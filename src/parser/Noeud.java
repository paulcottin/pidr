package parser;

import java.util.ArrayList;

public class Noeud {

	private ArrayList<Noeud> childs;
	private ArrayList<Propriete> proprietes;
	private String classe;
	
	public Noeud() {
		init();
	}
	
	private void init(){
		childs = new ArrayList<Noeud>();
		proprietes = new ArrayList<Propriete>();
		classe = "";
	}
	
	public String toString(){
		String ligne = "***\n";
		ligne += classe+"\n";
		if (childs.size() > 0) {
			for (Noeud n : childs) {
				ligne += NoeudToString(n);
			}
		}else {
			for (Propriete p : proprietes) {
				ligne += p.toString();
			}
		}
		return ligne;
	}
	
	private String NoeudToString(Noeud n){
		String string = "";
		for (Propriete p : n.getProprietes()) {
			string += p.toString();
		}
		for (Noeud noeud : n.getChilds()) {
			NoeudToString(noeud);
		}
		string += "---\n";
		return string;
	}
	
	public Noeud getChildByName(String name){
		Noeud n = null;
		
		return n;
	}
	
	public Propriete getProprieteByName(String name){
		for (Propriete prop : this.getProprietes()) {
			if (prop.getNom().equals(name))
				return prop;
		}
		return null;
	}
	
	public Propriete getProprieteById(String id){
		for (Propriete p : proprietes) {
			if (p.getNom().equals("_id")) {
				if (p.getStringValue().equals(id)) {
					return p;
				}
			}
		}
		return null;
	}
	
	public int getChildCount(){
		return childs.size();
	}

	public ArrayList<Noeud> getChilds() {
		return childs;
	}

	public void setChilds(ArrayList<Noeud> childs) {
		this.childs = childs;
	}

	public ArrayList<Propriete> getProprietes() {
		return proprietes;
	}

	public void setProprietes(ArrayList<Propriete> proprietes) {
		this.proprietes = proprietes;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
}
