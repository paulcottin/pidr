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
		String ligne = "";
		for (Noeud n : childs) {
			ligne += NoeudToString(n);
		}
		return ligne;
	}
	
	private String NoeudToString(Noeud n){
		String string = "***\n";
		string += n.getClasse()+"\n";
		for (Propriete p : n.getProprietes()) {
			string += "-->"+p.getNom()+" : "+((p.getIntValue() == -1)?p.getStringValue():p.getIntValue())+"\n";
		}
		for (Noeud noeud : n.getChilds()) {
			NoeudToString(noeud);
		}
		string += "---\n";
		return string;
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
