package donnees;

import java.util.ArrayList;

import parser.Noeud;

public class Diagramme {

	private Noeud noeud;
	private String nomDiagramme, dateModification, typeDiagramme, id;
	private ArrayList<DiagrammeObjets> objets;
	
	public Diagramme() {
		init();
	}
	
	public Diagramme(Noeud n){
		init();
		this.noeud = n;
	}
	
	private void init(){
		nomDiagramme = "";
		dateModification = "";
		typeDiagramme = "";
		id = "";
		objets = new ArrayList<DiagrammeObjets>();
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
}
