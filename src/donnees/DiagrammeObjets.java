package donnees;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jdk.internal.jfr.events.FileWriteEvent;
import modele.Comparateur;
import parser.Noeud;
import parser.Parser2;

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
	 * Methode abstraite dans laquelle chaque classe definie ses elements propres
	 */
	protected abstract void initClass();
	
	/**
	 * Methode abstraite de comparaison d'objets entre eux
	 * @param o : l'objet a comparer
	 * @return : true si les objets sont identiques, false sinon
	 * Cette methode enregistre les differences dans une liste modif
	 */
	protected boolean egal(DiagrammeObjets o){
		boolean b = true;
		if (!name.getText().equals(o.getNameText())) {
			modif.add("nom : "+o.getNameText());
			name.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
			b = false;
		}
		
		b = b ? egalObjet(o) : false;
		return b;
	}
	
	protected abstract boolean egalObjet(DiagrammeObjets objet);
	
	/**
	 * Methode abstraite pour que chaque objet puisse ecrire sur le noeud les eventuelles modifications
	 */
	protected abstract void write();
	
	public String constructRGB(int r, int g, int b){
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
	
	/**
	 * Défini si le composant a été modifié ou pas et le colore en conséquence
	 * @param etat : peut être égal à IDEM, MODIF, ADD, SUPPR
	 */
	public void setEtat(int etat) {
		if (etat == MODIF) setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
		else if (etat == SUPPR) setRGB(Comparateur.SUPPR_R, Comparateur.SUPPR_G, Comparateur.SUPPR_B);
		else if (etat == ADD) setRGB(Comparateur.ADD_R, Comparateur.ADD_G, Comparateur.ADD_B);
		else if (etat == IDEM) setRGB(-1, -1, -1);
		name.setRGB(r, g, b);
		this.etat = etat;
		colored(etat);
	}
	
	private void colored(int indicatifModification){
		int findLineColor = -1,findMetaclass = -1, findFormat = -1, findFontColor = -1;
		int nbPropSubject = 0, nbPropMetaClass = 0, nbProperties = 0;
		if (indicatifModification != IDEM) {
			System.out.println("etat : "+indicatifModification);
			//si il existe un fils "_propriete"
			if (noeud.getChildByName("_properties") != null) {
				System.out.println("properties not null");
				//Si il existe un attribut "format" : la où sont défini les couleurs
				nbPropSubject = noeud.getChildByName("_properties").getChildByName("Subjects").getChildByName("size").getIntValue();
				for (int i = 0; i < nbPropSubject; i++) {
					if (noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(1).getChilds().get(i).getChildByName("_Name").equals("\"Format\"")) {
						System.out.println("format existe");
						findFormat = i;
						Noeud format = noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(1).getChilds().get(i);
						nbPropMetaClass = format.getChildByName("Metaclasses").getChildByName("size").getIntValue();
						for (int j = 0; j < nbPropMetaClass; j++) {
							Noeud prop = format.getChildByName("Metaclasses").getChilds().get(1).getChilds().get(j).getChildByName("Properties");
							nbProperties = prop.getChildByName("size").getIntValue();
							for (int j2 = 0; j2 < nbProperties; j2++) {
								if (prop.getChilds().get(1).getChilds().get(j2).getChildByName("_Type").getStringValue().equals("Color") && 
										prop.getChilds().get(1).getChilds().get(j2).getChildByName("_Name").getStringValue().equals("\"Font.FontColor\"")) {
									findFontColor = j2;
									findMetaclass = j;
									System.out.println("color : "+noeud.getChildByName("_id").getStringValue());
									prop.getChilds().get(1).getChilds().get(j2).getChildByName("_Value").setStringValue(constructRGB(r, g, b));
								}
								else if (prop.getChilds().get(1).getChilds().get(j2).getChildByName("_Type").getStringValue().equals("Color") && 
										prop.getChilds().get(1).getChilds().get(j2).getChildByName("_Name").getStringValue().equals("\"Line.LineColor\"")) {
									findLineColor = j2;
									findMetaclass = j;
									prop.getChilds().get(1).getChilds().get(j2).getChildByName("_Value").setStringValue(constructRGB(r, g, b));
								}
							}
						}
					}
				}
				System.out.println("format existe pas");
				//Si il a trouvé "Format" mais qu'il n'y a pas Color
				if (findFormat > 0 && findFontColor < 0) {
					//Ajouter Property dans Properties
					noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(findFormat).getChildByName("Metaclasses").getChilds().get(findMetaclass).getChildByName("Properties").getChilds().add(constructAProperty(findFormat, findMetaclass));
				}
				//Si il n'a pas trouvé "Format"
				else if (findFormat < 0) {
					System.out.println("créer format");
					//créer Format
					noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(1).getChilds().add(0, constructFormat(nbPropSubject));
				}
			}
			//Si _properties est null
			else {
				//Créer _properties
				noeud.getChilds().add(1, constructProperties());
			}
		}
	}
	
	private Noeud constructProperties(){
		try {
			writeHelpFile("tmp\\properties.rpy", "properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parser2 p = new Parser2("tmp\\properties.rpy");
		Noeud n = p.parse();
		return n;
	}
	
	/**
	 * 
	 * @param nbPropSubjects
	 * @return
	 */
	private Noeud constructFormat(int nbPropSubjects){
		//On incrémente le nombre de subjects
		noeud.getChildByName("_properties").getChildByName("Subjects").getChildByName("size").setIntValue(noeud.getChildByName("_properties").getChildByName("Subjects").getChildByName("size").getIntValue()+1);
		//On construit le noeud
		try {
			writeHelpFile("tmp\\format.rpy", "format");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parser2 p = new Parser2("tmp\\format.rpy");
		Noeud n = p.parse();
		return n;
	}
	
	private Noeud constructAProperty(int findFormat, int findMetaclass){
		//On incrémente le nombre de property
		int size = noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(findFormat).getChildByName("Metaclasses").getChilds().get(findMetaclass).getChildByName("Properties").getChildByName("size").getIntValue();
		noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(findFormat).getChildByName("Metaclasses").getChilds().get(findMetaclass).getChildByName("Properties").getChildByName("size").setIntValue(size+1);
		//On crée la property pour la couleur
		try {
			writeHelpFile("tmp\\aProperty.rpy", "aProperty");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parser2 p = new Parser2("tmp\\aProperty.rpy");
		Noeud n = p.parse();
		return n;
	}
	
	private void writeHelpFile(String path, String type) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
		String s = "";
		
		if (type.equals("format")) {
			s += "{ IPropertySubject \n\t- _Name =3 \"Format\";\n- Metaclasses = { IRPYRawContainer \n\t- size = 1;\n- value = \n{ IPropertyMetaclass "+
						"\n\t- _Name = \"Object\";\n- Properties = { IRPYRawContainer\n\t- size = 1;\n- value =\n { IProperty\n\t- _Name = \"Line.LineColor\";"+
								"\n\t- _Value = "+constructRGB(r, g, b)+";\n- _Type = Color;\n}\n}\n}\n}\n}\n";
			bw.write(s);
		}
		else if (type.equals("aProperty")) {
			s += "{ IProperty\n\t- _Name = \"Line.LineColor\";"+
					"\n\t- _Value = "+constructRGB(r, g, b)+";\n- _Type = Color;\n}\n";
			bw.write(s);
		}
		else if (type.equals("properties")) {
			s += "- _properties = { IPropertyContainer \n\t- Subjects = { IRPYRawContainer\n\t - size = 1;\n"
					+ "- value = { IPropertySubject \n\t- _Name =3 \"Format\";\n- Metaclasses = { IRPYRawContainer \n\t- size = 1;\n- value = \n{ IPropertyMetaclass "+
					"\n\t- _Name = \"Object\";\n- Properties = { IRPYRawContainer\n\t- size = 1;\n- value =\n { IProperty\n\t- _Name = \"Line.LineColor\";"+
							"\n\t- _Value = "+constructRGB(r, g, b)+";\n- _Type = Color;\n}\n}\n}\n}\n}\n}\n}\n";
			bw.write(s);
		}
		bw.close();
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
