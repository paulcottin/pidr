package donnees;

import parser.Noeud;

public class Value {
	
	private String stringValue;
	private Noeud noeudValue;
	
	public Value() {
		init();
	}
	
	public Value(Object o){
		init();
		if (o instanceof Noeud) {
			noeudValue = (Noeud) o;
		}else if (o instanceof String) {
			stringValue = (String) o;
		}else if (o instanceof Integer) {
			stringValue = String.valueOf((Integer) o);
		}
	}
	
	public void init(){
		this.stringValue = "";
		this.noeudValue = null;
	}
	
	public String get(){
		if (noeudValue != null) {
			return noeudValue.getClasse();
		}else {
			return stringValue;
		}
	}
	
	public void set(Object o){
		if (o instanceof Noeud) {
			noeudValue = (Noeud) o;
		}else if (o instanceof String) {
			stringValue = (String) o;
		}else if (o instanceof Integer) {
			stringValue = String.valueOf((Integer) o);
		}
	}

}
