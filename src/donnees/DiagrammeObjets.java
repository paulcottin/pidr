package donnees;

import java.util.ArrayList;

import modele.Comparateur;
import parser.Noeud;

/**
 * Classe abstraite qui gère tous les elements d'un diagramme.
 * Cette classe initialise les donnes communes a chaque objet donc l'id.
 * @author paul
 *
 */
public abstract class DiagrammeObjets {

	public static int SUPPR = 0;
	public static int ADD = 1;
	public static int MODIF = 2;
	public static int IDEM = 3;
	
	protected Noeud noeud;
	protected String id, classe;
	protected Texte name;
	protected int r, g, b;
	protected int etat;
	protected ArrayList<String> modif;//, suppr, add;
	
	public DiagrammeObjets(Noeud n){
		this.noeud = n;
		init();
		initClass();
	}
	
	/**
	 * Initialisation de la classe
	 */
	private void init(){
		name = new Texte(noeud.getChildByName("m_name"));
		id  = noeud.getChildByName("_id").getStringValue();
		classe = getOutQuotes(noeud.getChildByName("m_pModelObject").getChildByName("_m2Class").getStringValue());
		etat = IDEM;
		modif = new ArrayList<String>();
//		suppr = new ArrayList<String>();
//		add = new ArrayList<String>();
//		System.out.println("Objet "+name.getText()+" ("+classe+")");
//		r = -1; 
//		g = -1;
//		b = -1;
	}
	
	/**
	 * Ecrit sur le noeud les donnees representant l'id, le nom et le type d'objet (la classe Rhapsody)
	 */
	protected void writeGeneral(){
		name.write();
		noeud.getChildByName("m_pModelObject").getChildByName("_name").setStringValue(addQuotes(name.getText()));
		noeud.getChildByName("_id").setStringValue(id);
		noeud.getChildByName("m_pModelObject").getChildByName("_m2Class").setStringValue(addQuotes(classe));
	}
	
	/**
	 * Enlève les " d'une chaine de caracteres
	 * @param string : la chaine avec "
	 * @return : la chaine sans "
	 */
	protected String getOutQuotes(String string){
		if (string.contains("\""))
			return string.split("\"")[1];
		else
			return string;
	}
	
	/**
	 * Ajoute des " a une chaine de carateres
	 * @param string : chaine sans "
	 * @return : chaine avec "
	 */
	protected String addQuotes(String string){
		return "\""+string+"\"";
	}
	
	/**
	 * Methode abstraite pour que chaque objet puisse ecrire sur le noeud les eventuelles modifications
	 */
	protected abstract void write();
	
	/**
	 * Methode abstraite dans laquelle chaque classe definie ses elements propres
	 */
	protected abstract void initClass();
	
	/**
	 * Methode abstraite de comparaison d'objets entre eux
	 * @param o : l'objet a comparer
	 * @return : true si les objets sont identiques, false sinon
	 * Cette methode enregistre les differences dans une liste modif
	 */
	protected abstract boolean egal(DiagrammeObjets o);
	
	protected String constructRGB(int r, int g, int b){
		return "\""+r+","+g+","+b+"\"";
	}
	
	public String toString(){
		String etat = "";
		if (this.etat == ADD) etat = "ADD";
		if (this.etat == SUPPR) etat = "SUPPR";
		if (this.etat == MODIF) etat = "MODIF";
		if (this.etat == IDEM) etat = "IDEM";
		return name.getText()+" ("+classe+") etat : "+etat;
	}
	
	public void setEtat(int etat) {
		if (etat == MODIF) setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
		else if (etat == SUPPR) setRGB(Comparateur.SUPPR_R, Comparateur.SUPPR_G, Comparateur.SUPPR_B);
		else if (etat == ADD) setRGB(Comparateur.ADD_R, Comparateur.ADD_G, Comparateur.ADD_B);
		else if (etat == IDEM) setRGB(-1, -1, -1);
		name.setRGB(r, g, b);
		this.etat = etat;
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
	
	public void setRGB(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
		name.setRGB(r, g, b);
	}

	public int getEtat() {
		return etat;
	}

	public ArrayList<String> getModif() {
		return modif;
	}

	public void setModif(ArrayList<String> modif) {
		this.modif = modif;
	}
}
