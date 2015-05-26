package donnees;

import java.util.ArrayList;

import parser.Noeud;

public class ProjetBDD {
	
	private String nomProjet, id;
	private Noeud noeud;
	private ArrayList<Diagramme> diagrammes;
	private String lastModifiedDate;
	private int[] correspondance;
	private String diffs;
	private int nbDiffs;
	
	public ProjetBDD(Noeud n) {
		this.noeud = n;
		init();
	}
	
	private void init(){
		id = noeud.getChildByName("_id").getStringValue();
		nomProjet = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		lastModifiedDate = noeud.getChildByName("_modifiedTimeWeak").getStringValue();
		diagrammes = new ArrayList<Diagramme>();
		int nbDiagrammes = noeud.getChildByName("Declaratives").getChildByName("size").getIntValue();
		for (int i = 0; i < nbDiagrammes; i++) {
			diagrammes.add(new Diagramme(noeud.getChildByName("Declaratives").getChild(1).getChild(i)));
		}
		nbDiffs = 0;
		initCorrespondance(diagrammes.size());
	}
	
	private void initCorrespondance(int size){
		correspondance = new int[size];
		for (int i = 0; i < correspondance.length; i++)
			correspondance[i] = -1;
	}
	
	public String getNomProjet() {
		return nomProjet;
	}
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
