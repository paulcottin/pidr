package donnees;

import parser.Noeud;

public class Handle {

	private Noeud noeud;
	private String classe, nom, id;
	
	public Handle(Noeud noeud) {
		this.noeud = noeud;
		init();
	}
	
	private void init(){
		classe = noeud.getChildByName("_class").getStringValue().split("\"")[1];
		nom = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		id = noeud.getChildByName("_id").getStringValue();
	}
	
	public boolean equals(Object object){
		if (object instanceof Handle) {
			Handle o = (Handle) object;
			if (!classe.equals(o.getClasse()) || !nom.equals(o.getNom())) return false;
			else return true;
		}
		else
			return false;
	}
	
	public void write(){
		noeud.getChildByName("_class").setStringValue("\""+classe+"\"");
		noeud.getChildByName("_name").setStringValue("\""+nom+"\"");
		noeud.getChildByName("_id").setStringValue(id);
	}
	
	public String toString(){
		return nom+" ("+classe+")";
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
