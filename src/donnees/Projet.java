package donnees;

import java.util.ArrayList;
import parser.Noeud;

public class Projet {

	private String nomProjet, typeProjet;
	private Noeud noeud;
	private ArrayList<Diagramme> diagrammes;
	private String lastModifiedDate;
	private int[] correspondance;
	
	public Projet(Noeud n){
		this.noeud = n;
		init();
	}
	
	private void init(){
		diagrammes = new ArrayList<Diagramme>();
		nomProjet = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		lastModifiedDate = noeud.getChildByName("_modifiedTimeWeak").getStringValue();
		typeProjet = noeud.getChildByName("Stereotypes").getChildByName("value").getChilds().get(0).getChildByName("_name").getStringValue().split("\"")[1];
		diagrammes = new ArrayList<Diagramme>();
		int nbDiagrammes = noeud.getChildByName("Diagrams").getChildByName("size").getIntValue()-1;
//		System.out.println(noeud.getChildByName("Diagrams").getChildByName("value").getChilds().get(0));
		for (int i = 0; i < nbDiagrammes; i++) {
			diagrammes.add(new Diagramme(noeud.getChildByName("Diagrams").getChildByName("value").getChilds().get(1+i)));
		}
		
		
//		System.out.println("Projet "+nomProjet+" ("+typeProjet+"), modifié le "+lastModifiedDate+" : "+diagrammes.size()+" diagramme(s)");
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
}
