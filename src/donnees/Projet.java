package donnees;

import java.util.ArrayList;

import parser.Noeud;

public class Projet {

	private String nomProjet, typeDiagramme;
	private Noeud noeud;
	private ArrayList<Diagramme> diagrammes;
	
	public Projet() {
		init();
	}
	
	public Projet(Noeud n){
		init();
		this.noeud = n;
	}
	
	private void init(){
		nomProjet = "";
		noeud = new Noeud();
		diagrammes = new ArrayList<Diagramme>();
	}
	
	private void typeDiagramme(){
		typeDiagramme = noeud.getProprieteByName("").getStringValue();
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public String getTypeDiagramme() {
		return typeDiagramme;
	}

	public void setTypeDiagramme(String typeDiagramme) {
		this.typeDiagramme = typeDiagramme;
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
}
