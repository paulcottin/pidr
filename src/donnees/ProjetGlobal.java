package donnees;

import parser.Noeud;
import parser.Parser2;

public class ProjetGlobal {
	
	private Projet projet;
	private ProjetBDD projetBDD;
	private String path;
	private Parser2 parser;
	private String initLigne;
	private String diffs;
	private int nbDiffs;
	
	public ProjetGlobal(String pathRPY) {
		this.path = pathRPY;
		init();
	}
	
	private void init(){
		//initialisations globales
		diffs = "";
		nbDiffs = 0;
		//Initialisation Projet
		parser = new Parser2(path);
		Noeud n = parser.parse();
		initLigne = parser.getInitLigne();
		projet = new Projet(n);
		//Initialisation ProjetBDD
		String sbsPath = path.substring(0, path.indexOf("."))+"_rpy\\"+"Default.sbs";
		parser = new Parser2(sbsPath);
		Noeud n1 = parser.parse();
		projetBDD = new ProjetBDD(n1);
	}
	
	public void compare(ProjetGlobal p){
		projet.compare(p.getProjet());
		
		diffs+= projet.getDiffs();
		nbDiffs += projet.getNbDiffs();
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public ProjetBDD getProjetBDD() {
		return projetBDD;
	}

	public void setProjetBDD(ProjetBDD projetBDD) {
		this.projetBDD = projetBDD;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Parser2 getParser() {
		return parser;
	}

	public void setParser(Parser2 parser) {
		this.parser = parser;
	}

	public String getInitLigne() {
		return initLigne;
	}

	public void setInitLigne(String initLigne) {
		this.initLigne = initLigne;
	}

	public String getDiffs() {
		return diffs;
	}

	public void setDiffs(String diffs) {
		this.diffs = diffs;
	}

	public int getNbDiffs() {
		return nbDiffs;
	}

	public void setNbDiffs(int nbDiffs) {
		this.nbDiffs = nbDiffs;
	}

}
