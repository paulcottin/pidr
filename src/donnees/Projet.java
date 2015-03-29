package donnees;

import parser.Noeud;

public class Projet {

	private String nomProjet, typeDiagramme;
	private Noeud noeud;
	
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
}
