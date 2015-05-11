package donnees;

import java.util.ArrayList;

import parser.Noeud;
import rhapsodyClass.IAssociationEnd;
import rhapsodyClass.IClass;

public class Diagramme {

	private Noeud noeud;
	private String nomDiagramme, dateModification, typeDiagramme, id;
	private ArrayList<DiagrammeObjets> objets;
	private int etat;
	private int[] correspondance;

	public Diagramme(Noeud n){
		this.noeud = n;
		init();
	}

	private void init(){
		nomDiagramme = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		dateModification = noeud.getChildByName("_lastModifiedTime").getStringValue().split("\"")[1];
		typeDiagramme = noeud.getChildByName("Stereotypes").getChildByName("value").getChilds().get(0).getChildByName("_name").getStringValue().split("\"")[1];
		id = noeud.getChildByName("_id").getStringValue();
		Noeud listDiag = noeud.getChildByName("_graphicChart").getChilds().get(noeud.getChildByName("_graphicChart").getChilds().indexOf(noeud.getChildByName("_graphicChart").getChildByName("elementList"))+1);
		objets = new ArrayList<DiagrammeObjets>();
		for (Noeud n : listDiag.getChilds()) {
			int type = n.getChildByName("m_type").getIntValue();
			if (type != 78) {
				switch (getClass(n)) {
				case "IClass":
					objets.add(new IClass(n));
					break;
				case "IAssociationEnd":
					objets.add(new IAssociationEnd(n));
					break;
				default:
					break;
				}
			}
		}
		etat = DiagrammeObjets.IDEM;
		//		System.out.println("Diagramme "+nomDiagramme+" ("+typeDiagramme+") constitué de "+objets.size()+" objets");
	}

	/**
	 * Compare this et le diagramme d
	 * @param d Diagramme à comparer
	 */
	public void compareTo(Diagramme d){
		int maxDiagrammeSize = this.getObjets().size() >= d.getObjets().size() ? this.getObjets().size() : d.getObjets().size();
		initCorrespondance(maxDiagrammeSize);
		ArrayList<DiagrammeObjets> list1 = (maxDiagrammeSize == this.getObjets().size() ? this.getObjets() : d.getObjets());
		ArrayList<DiagrammeObjets> list2 = (maxDiagrammeSize != this.getObjets().size() ? this.getObjets() : d.getObjets());
		//list1 est donc la liste la plus longue

		doCorrespondance(list1, list2);

		compare(list1, list2, d);

	}

	/**
	 * Lance la comparaison en appellant la méthode egal du type DiagrammeObjet
	 * @param list1 liste à comparer
	 * @param list2 avec elle
	 */
	private void compare(ArrayList<DiagrammeObjets> list1, ArrayList<DiagrammeObjets> list2, Diagramme d){
		ArrayList<DiagrammeObjets> diff = new ArrayList<DiagrammeObjets>();
		//on compare les diagrammes en commun
		for (int i = 0; i < list2.size(); i++) {
			if (!list1.get(i).egal(list2.get(correspondance[i]))){
				diff.add(list1.get(i));
				list1.get(i).setEtat(DiagrammeObjets.MODIF);
			}
		}
		//Si la list1 est la liste de départ et plus longue alors on met les restants dans un état supprimé
		if (list1.size() > list2.size() && this.getObjets().equals(list1)) {
			for (int i = list2.size(); i < list1.size(); i++)
				list1.get(i).setEtat(DiagrammeObjets.SUPPR);
		}
		//Si la list1 est la liste de départ et plus courte alors on met les restants dans un état ajouté
		else if (list1.size() > list2.size() && d.getObjets().equals(list1)) {
			for (int i = list2.size(); i < list1.size(); i++)
				list1.get(i).setEtat(DiagrammeObjets.ADD);
		}
		else if (list1.size() == list2.size()){}
		//Ne rien faire
		else
			System.out.println("ERROR (Diagramme - compare())");

		if (diff.size() == 0)
			System.out.println("Aucune différence majeure");
		else {
			System.out.println("Différences : ");
			for (DiagrammeObjets diagrammeObjets : diff)
				System.out.println(diagrammeObjets);
		}
	}

	/**
	 * faire la correspondance entre les différents objets afin de comparer les bons entre eux.
	 * 
	 * On commence à essayer avec les id des objets
	 * 
	 * @param list1 : la 1ère liste de diagrammes
	 * @param list2 : la 2ème liste de diagrammes
	 */
	private void doCorrespondance(ArrayList<DiagrammeObjets> list1, ArrayList<DiagrammeObjets> list2){
		boolean find = true;
		//Avec les id 
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).getId().equals(list2.get(j).getId())) correspondance[i] = j;
			}
		}		

		find = correspondanceOK(list1, list2);
		if (!find)
			System.out.println("correspondance des objets impossible");
		else
			System.out.println("correspondance objets ok !");
	}

	/**
	 * Check si la correspondance est finie ou pas
	 * @param list1 les deux listes 
	 * @param list2 à comparer
	 * @return
	 */
	private boolean correspondanceOK(ArrayList<DiagrammeObjets> list1, ArrayList<DiagrammeObjets> list2){
		boolean find = true;
		int cpt = 0;
		for (Integer i : correspondance) {
			if (list1.size() == list2.size())
				if (i == -1) find = false; else;
			else 
				if (i == -1) cpt++;			
		}
		if (list1.size() < list2.size()) 
			if (list1.size() != cpt) find = false; else find = true;
		else if (list2.size() < list1.size())
			if (list2.size() != cpt) find = false; else find = true;
		return find;
	}

	/**
	 * Initialisation du tableau de correspondance
	 * @param size : taille du tableau
	 */
	private void initCorrespondance(int size){
		correspondance = new int[size];
		for (int i = 0; i < correspondance.length; i++)
			correspondance[i] = -1;
	}

	private String getClass(Noeud n){
		return n.getChildByName("m_pModelObject").getChildByName("_m2Class").getStringValue().equals("\"\"") ? "" : n.getChildByName("m_pModelObject").getChildByName("_m2Class").getStringValue().split("\"")[1];
	}

	public String getNomDiagramme() {
		return nomDiagramme;
	}

	public void setNomDiagramme(String nomDiagramme) {
		this.nomDiagramme = nomDiagramme;
	}

	public String getDateModification() {
		return dateModification;
	}

	public void setDateModification(String dateModification) {
		this.dateModification = dateModification;
	}

	public String getTypeDiagramme() {
		return typeDiagramme;
	}

	public void setTypeDiagramme(String typeDiagramme) {
		this.typeDiagramme = typeDiagramme;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<DiagrammeObjets> getObjets() {
		return objets;
	}

	public void setObjets(ArrayList<DiagrammeObjets> objets) {
		this.objets = objets;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		for (DiagrammeObjets o : objets)
			o.setEtat(etat);
		this.etat = etat;
	}
}
