package donnees;

import java.util.ArrayList;

import exceptions.BadDiagramCorrespondance;
import exceptions.BadProjectCorrespondance;
import parser.Noeud;

public class Projet {

	/**
	 * Classe qui implemente un projet rhapsody
	 * Initialise la correspondance entre les diffenrents diagramme et lance la comparaison.
	 */
	private String nomProjet, typeProjet, id;
	private Noeud noeud;
	private ArrayList<Diagramme> diagrammes;
	private String lastModifiedDate;
	private int[] correspondance;
	private String diffs;
	private int nbDiffs;
	
	public Projet(Noeud n){
		this.noeud = n;
		init();
	}
	
	private void init(){
		id = noeud.getChildByName("_id").getStringValue();
		diagrammes = new ArrayList<Diagramme>();
		nomProjet = noeud.getChildByName("_name").getStringValue().split("\"")[1];
//		lastModifiedDate = noeud.getChildByName("_modifiedTimeWeak").getStringValue();
		typeProjet = noeud.getChildByName("Stereotypes").getChildByName("value").getChildByName("_name").getStringValue().split("\"")[1];
		diagrammes = new ArrayList<Diagramme>();
		int nbDiagrammes = noeud.getChildByName("Diagrams").getChildByName("size").getIntValue();
		for (int i = 0; i < nbDiagrammes; i++) {
			diagrammes.add(new Diagramme(noeud.getChildByName("Diagrams").getChilds().get(1).getChilds().get(i)));
		}
		nbDiffs = 0;
		initCorrespondance(diagrammes.size());
	}
	
	/**
	 * Lance la comparaison en appellant la méthode compareTo du type Diagramme
	 * @param list1 liste à comparer
	 * @param list2 avec elle
	 */
	public void compare(Projet p){
		//On etablit la correspondance entre les diagrammes
		doCorrespondance(p);
		//on compare les diagrammes en commun
		for (int i = 0; i < p.getDiagrammes().size(); i++) {
			diagrammes.get(i).compareTo(p.getDiagrammes().get(correspondance[i]));
		}
		//Si la list1 est la liste de départ et plus longue alors on met les restants dans un état supprimé
		if (diagrammes.size() > p.getDiagrammes().size()) {
			for (int i = p.getDiagrammes().size(); i < diagrammes.size(); i++)
				diagrammes.get(i).setEtat(DiagrammeObjets.SUPPR);
		}
		//Si la list1 est la liste de départ et plus courte alors on met les restants dans un état ajouté
		else if (diagrammes.size() > p.getDiagrammes().size()) {
			for (int i = p.getDiagrammes().size(); i < diagrammes.size(); i++)
				diagrammes.get(i).setEtat(DiagrammeObjets.ADD);
		}
		else if (diagrammes.size() == p.getDiagrammes().size()){}
		//Ne rien faire
		else
			System.out.println("ERROR (Comparateur - compare())");
		writeDiff();	
	}
	
	/**
	 * faire la correspondance entre les différents diagrammes afin de comparer les bons entre eux.
	 * 
	 * On prodece avec les ids des diagrammes
	 * 
	 * @param list1 : la 1ère liste de diagrammes
	 * @param list2 : la 2ème liste de diagrammes
	 */
	private void doCorrespondance(Projet p){
		//Correspondance des projets
		if (!id.equals(p.getId())) new BadProjectCorrespondance();
		else{
			boolean find = true;
			//Avec les IDs
			for (int i = 0; i < diagrammes.size(); i++) 
				for (int j = 0; j < p.getDiagrammes().size(); j++)
					if (diagrammes.get(i).getId().equals(p.getDiagrammes().get(j).getId())) correspondance[i] = j;
		}

	}
	
	private void writeDiff(){
		for (Diagramme d : this.diagrammes) {
			for (String s : d.getDiffString()) {
				diffs += s+"\n";
				nbDiffs++;
			}
		}
	}
	
	private void initCorrespondance(int size){
		correspondance = new int[size];
		for (int i = 0; i < correspondance.length; i++)
			correspondance[i] = -1;
	}
	
	public void write(){
		for (Diagramme d : diagrammes) {
			d.write();
		}
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public ArrayList<Diagramme> getDiagrammes() {
		return diagrammes;
	}

	public void setDiagrammes(ArrayList<Diagramme> diagrammes) {
		this.diagrammes = diagrammes;
	}

	public String getTypeProjet() {
		return typeProjet;
	}

	public void setTypeProjet(String typeProjet) {
		this.typeProjet = typeProjet;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int[] getCorrespondance() {
		return correspondance;
	}

	public void setCorrespondance(int[] correspondance) {
		this.correspondance = correspondance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiffs() {
		return diffs;
	}

	public void setDiffs(String diffs) {
		this.diffs = diffs;
	}

	public int getNbDiffs() {
		return nbDiffs;
	}

	public void setNbDiffs(int nbDiffs) {
		this.nbDiffs = nbDiffs;
	}
}
