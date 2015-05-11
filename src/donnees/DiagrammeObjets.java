package donnees;

import parser.Noeud;

public abstract class DiagrammeObjets {

	protected Noeud noeud;
	protected String id, classe;
	protected Texte name;
	protected int r, g, b;
	
	public DiagrammeObjets(Noeud n){
		this.noeud = n;
		init();
		initClass();
	}
	
	private void init(){
		name = new Texte(noeud.getChildByName("m_name"));
		id  = noeud.getChildByName("_id").getStringValue();
		classe = getOutQuotes(noeud.getChildByName("m_pModelObject").getChildByName("_m2Class").getStringValue());
		System.out.println("Objet "+name.getText()+" ("+classe+")");
//		r = -1; 
//		g = -1;
//		b = -1;
	}
	
	protected void writeGeneral(){
		name.write();
		noeud.getChildByName("m_pModelObject").getChildByName("_name").setStringValue(addQuotes(name.getText()));
		noeud.getChildByName("_id").setStringValue(id);
		noeud.getChildByName("m_pModelObject").getChildByName("_m2Class").setStringValue(addQuotes(classe));
	}
	
	protected String getOutQuotes(String string){
		if (string.contains("\""))
			return string.split("\"")[1];
		else
			return string;
	}
	
	protected String addQuotes(String string){
		return "\""+string+"\"";
	}
	
	protected abstract void write();
	protected abstract void initClass();
	protected abstract boolean egal(DiagrammeObjets o);
	
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

	public String getNameText() {
		return name.getText();
	}

	public void setNameText(String name) {
		this.name.setText(name);
	}

	public Texte getName() {
		return name;
	}

	public void setName(Texte name) {
		this.name = name;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
}
