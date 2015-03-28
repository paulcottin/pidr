package parser;

import java.util.ArrayList;

public class Propriete {
	
	private String nom, stringValue;
	private int intValue;
	private ArrayList<Noeud> noeuds;
	
	public Propriete() {
		init();
	}
	
	public Propriete(String nom, String stringValue, int intValue, ArrayList<Noeud> noeudList){
		this.nom = nom;
		this.stringValue = stringValue;
		this.intValue = intValue;
		this.noeuds = noeudList;
	}
	
	private void init(){
		nom = null;
		stringValue = null;
		noeuds = null;
		intValue = -1;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public ArrayList<Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(ArrayList<Noeud> noeuds) {
		this.noeuds = noeuds;
	}

}
