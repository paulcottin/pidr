package donnees;

import java.util.ArrayList;
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
	
	public Projet(Noeud n){
		this.noeud = n;
		init();
	}
	
	private void init(){
		id = noeud.getChildByName("_id").getStringValue();
		diagrammes = new ArrayList<Diagramme>();
		nomProjet = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		lastModifiedDate = noeud.getChildByName("_modifiedTimeWeak").getStringValue();
		typeProjet = noeud.getChildByName("Stereotypes").getChildByName("value").getChildByName("_name").getStringValue().split("\"")[1];
		diagrammes = new ArrayList<Diagramme>();
		int nbDiagrammes = noeud.getChildByName("Diagrams").getChildByName("size").getIntValue()-1;
		
		for (int i = 0; i < nbDiagrammes; i++) {
			diagrammes.add(new Diagramme(noeud.getChildByName("Diagrams").getChilds().get(1).getChilds().get(1+i)));
		}
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
}
