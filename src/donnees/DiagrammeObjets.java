package donnees;

import parser.Noeud;

public class DiagrammeObjets {

	private Noeud noeud;
	private String id, classe, name;
	private Value value;
	
	public DiagrammeObjets() {
		init();
	}
	
	public DiagrammeObjets(Noeud n){
		init();
		this.noeud = n;
	}
	
	private void init(){
		noeud = new Noeud();
		name = "";
		id  ="";
		classe = "";
		value = new Value();
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
