package donnees;

import parser.Noeud;

public class DiagrammeObjetPropriete {

	private Noeud noeud;
	private String nom;
	private Value value;
	
	public DiagrammeObjetPropriete() {
		init();
	}
	
	public DiagrammeObjetPropriete(Noeud noeud, String nom, Value v){
		init();
		this.noeud = noeud;
		this.nom = nom;
		this.value = v;
	}
	
	private void init(){
		nom = "";
		value = new Value();
		noeud = null;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}
}
