package donnees;

import java.util.ArrayList;

import modele.Comparateur;
import exceptions.BadDiagramCorrespondance;
import parser.Noeud;
import rhapsodyClass.IAssociationEnd;
import rhapsodyClass.IClass;
import rhapsodyClass.IDependency;
import rhapsodyClass.IInformationFlow;
import rhapsodyClass.IObjectLink;
import rhapsodyClass.IPart;
import rhapsodyClass.IPort;
import rhapsodyClass.ISysMLPort;

/**
 * Plusieurs diagrammes composent un projet. Ils sont caracterises par un type, un nom et un id.
 * Exemple de diagramme : Diagramme de block sous Rhapsody.
 * @author paul
 *
 */
public class Diagramme {

	private Noeud noeud;
	private String nomDiagramme, dateModification, typeDiagramme, id;
	private ArrayList<DiagrammeObjets> objets;
	private ArrayList<String> diffString;
	private int etat;
	private int[] correspondance;

	public Diagramme(Noeud n){
		this.noeud = n;
		init();
	}

	private void init(){
		diffString = new ArrayList<String>();
		nomDiagramme = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		dateModification = noeud.getChildByName("_lastModifiedTime").getStringValue().split("\"")[1];
		typeDiagramme = noeud.getChildByName("Stereotypes").getChildByName("value").getChildByName("_name").getStringValue().split("\"")[1];
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
				case "IPort":
					objets.add(new IPort(n));
					break;
				case "IDependency":
					objets.add(new IDependency(n));
					break;
				case "IInformationFlow":
					objets.add(new IInformationFlow(n));
					break;
				case "ISysMLPort":
					objets.add(new ISysMLPort(n));
					break;
				case "IPart":
					objets.add(new IPart(n));
					break;
				case "IObjectLink":
					objets.add(new IObjectLink(n));
					break;
				default:
					break;
				}
			}
		}
		etat = DiagrammeObjets.IDEM;
	}

	/**
	 * Compare this et le diagramme d
	 * @param d Diagramme à comparer
	 */
	public void compareTo(Diagramme d){
		correspondance = new int[objets.size()];
		for (int i = 0; i < correspondance.length; i++)
			correspondance[i] = -1;

		doCorrespondance(d);
		compare(d);
	}

	/**
	 * faire la correspondance entre les différents objets afin de comparer les bons entre eux.
	 * 
	 * On procede avec les id des objets
	 * 
	 * @param list1 : la 1ère liste de diagrammes
	 * @param list2 : la 2ème liste de diagrammes
	 */

	private void doCorrespondance(Diagramme d){
		diffString.clear();
		//On parcourt this, les objets qui sont dans les deux se mettent en correspondance
		//Ceux qui ne sont pas dans d se mettent en etat SUPPR
		boolean find = false;
		for (int i = 0; i < objets.size(); i++) {
			find = false;
			for (int j = 0; j < d.getObjets().size(); j++) {
				if (objets.get(i).getId().equals(d.getObjets().get(j).getId())) {
					correspondance[i] = j;
					find = true;
				}
			}
			if (!find) {
				objets.get(i).setEtat(DiagrammeObjets.ADD);
				d.getObjets().add(objets.get(i));
				diffString.add("Ajout : "+objets.get(i).getNameText()+" ("+objets.get(i).getClasse()+")");
			}
		}
		//On parcourt d, ceux qui ne sont pas dans this se mettent en etat ADD
		find = false;
		for (int i = 0; i < d.getObjets().size(); i++) {
			find = false;
			for (int j = 0; j < objets.size(); j++) {
				if (d.getObjets().get(i).getId().equals(this.objets.get(j).getId())) {
					find = true;
				}
			}
			if (!find) {
				d.getObjets().get(i).setEtat(DiagrammeObjets.SUPPR);
				diffString.add("Suppression : "+d.getObjets().get(i).getNameText()+" ("+d.getObjets().get(i).getClasse()+")");
			}
		}
	}

	/**
	 * Lance la comparaison en appellant la méthode egal du type DiagrammeObjet
	 * @param list1 liste à comparer
	 * @param list2 avec elle
	 */
	private void compare(Diagramme d){
		//on compare les diagrammes en commun
		for (int i = 0; i < objets.size(); i++) {
			if (correspondance[i] != -1) {
				if (!objets.get(i).egal(d.getObjets().get(correspondance[i]))) {
					diffString.addAll(objets.get(i).getModif());
					d.getObjets().get(correspondance[i]).setEtat(DiagrammeObjets.MODIF);
				}
			}
		}
	}

	/**
	 * Ecris dans le Noeud les données de la classe (pour l'écriture ensuite dans le fichier via le Writer)
	 */
	public void write(){
		noeud.getChildByName("_name").setStringValue(nomDiagramme);
		noeud.getChildByName("_lastModifiedTime").setStringValue(dateModification);
		noeud.getChildByName("Stereotypes").getChildByName("value").getChildByName("_name").setStringValue(typeDiagramme);
		noeud.getChildByName("_id").setStringValue(id);
		for (DiagrammeObjets d : objets) {
			d.writeGeneral();
			d.write();
		}
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

	public ArrayList<String> getDiffString() {
		return diffString;
	}

	public void setDiffString(ArrayList<String> diffString) {
		this.diffString = diffString;
	}
}
