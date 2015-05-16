package parser;

import java.util.ArrayList;

/**
 * Noeud de l'arbre construit lors du parsing d'un fichier .rpy
 * Contient des fils : childs ou une stringValue, une IntValue
 * Possede un nom et une classe (classe definie dans rhapsody)
 * @author paul
 *
 */
public class Noeud {

	private ArrayList<Noeud> childs;
	private String name, classe, stringValue;
	private int intValue;
	
	public Noeud() {
		init();
	}
	
	public Noeud(String name){
		init();
		this.name = name;
	}
	
	public Noeud(String name, int intValue, String stringValue, ArrayList<Noeud> childs){
		init();
		this.name = name;
		this.intValue = intValue;
		this.stringValue = stringValue;
		this.childs = childs;
	}
	
	public Noeud(int intValue, String stringValue, ArrayList<Noeud> childs){
		init();
		this.intValue = intValue;
		this.stringValue = stringValue;
		this.childs = childs;
	}
	
	private void init(){
		childs = new ArrayList<Noeud>();
		classe = "";
		name = "";
		stringValue = "";
		intValue = -8000;
	}
	
//	public String toString(){
//		String res = "";
//		res += this.getName()+" { "+this.getClasse()+"\n";
//		if (intValue != -8000) res+= intValue;
//		if (stringValue != null) res += stringValue;
//		if (childs != null) {
//			for (Noeud n : childs) {
//				res += n.toString();
//			}
//		}
//		res += "\n";
//		return res;
//	}
	
//	public String toString(){
//		String res = "";
//		res += "nom : "+name+"\n";
//		res += "childs ("+childs.size()+") : \n";
//		for (Noeud n : childs) {
//			res += "\t"+n.getName();
//		}
//		return res;
//	}
	
	public String toString(){
		String res = "";
		res += name;
		res += " ("+((childs == null) ? 0 : childs.size())+")";
		res += " [";
		if (childs != null) {
			for (Noeud no : childs) {
				res += no.getName()+", ";
			}
		}else
			res += "NULL";
		res += "]";
		return res;
	}
	
	public Noeud getChildByName(String name){
		for (Noeud no : childs) {
			if (no.getName().equals(name)) {
				//if (no.getChilds() != null && no.getChilds().get(0).getChilds() != null && !no.getName().equals("value")) return no.getChilds().get(0);
				if (no.getChilds() != null) return no.getChilds().get(0); 
				else return no;
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

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
