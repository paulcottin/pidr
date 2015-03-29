package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

	private String path;
	private Noeud tree;
	private int size;
	static BufferedReader br;
	
	public Parser() {
		init();
	}
	
	public Parser(String path){
		init();
		this.path = path;
	}
	
	private void init(){
		path = "";
		tree = new Noeud();
		size = -8000;
	}
	
	public Noeud parse(){
		
		try {
			br = new BufferedReader(new FileReader(new File(path)));
			String ligne = "";
			br.readLine(); //On lit la première ligne qui contient des informations sur le langage I-Logix
			while(ligne != null){
				if (ligne.contains("{")) {
					tree = creerNoeud(ligne);
				}
				if (ligne != null) {
					ligne = br.readLine();
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tree;
	}
	
	private Noeud creerNoeud(String ligne) throws IOException{
		Noeud n = new Noeud();
		int mark = ligne.indexOf("{");
		n.setClasse(ligne.substring(mark+1, ligne.length()-1).replaceAll("\\s", ""));
//		System.out.println("-->"+n.getClasse());
		ligne = br.readLine();
		n = parcourirNoeud(ligne, n);
		return n;
	}
	
	private Noeud parcourirNoeud(String ligne, Noeud n) throws IOException{
		while(!ligne.contains("}")){
			//Propriété
			if (ligne.contains("- ")) {
				ArrayList<Noeud> noeuds = null;
				int intValue = -8000;
				String stringValue = null;
				String nom = null;
				int beginNom = ligne.indexOf("- ")+"- ".length();
				int endNom = ligne.indexOf("=")-1;
				nom = ligne.substring(beginNom, endNom).replaceAll("\\s", "");
//				System.out.println("nom : "+nom);
				//Une propriété "finale"/simple
				if (ligne.contains(";")) {
					int beginValue = endNom + 2;
					int endValue = ligne.indexOf(";");
					stringValue = ligne.substring(beginValue, endValue);
					if (stringValue.matches("\\s*-?\\d+\\s*")) {
						intValue = Integer.parseInt(stringValue.replaceAll("\\s", ""));
						stringValue = null;
					}
					//Sauvegarde de la valeur de size pour la gestion de la suite de noeuds
					if (nom.equals("size")) {
						size = intValue;
					}
				}
				else if (nom.equals("frameset")) {
					int beginValue = endNom + 2;
					int endValue = ligne.length();
					stringValue = ligne.substring(beginValue, endValue);
//					br.readLine();
					while(!ligne.contains(";")){
						ligne = br.readLine();
						stringValue += ligne;
					}
					stringValue = stringValue.substring(0, stringValue.indexOf(";"));
				}
				//Une propriété qui est un noeud
				else if (ligne.contains("=") && ligne.contains("{")) {
					noeuds = new ArrayList<Noeud>();
					noeuds.add(creerNoeud(ligne));
				}
				// une propriété qui est une suite de noeuds
				else if (nom.equals("value") && !ligne.contains(";")) {
//					System.out.println("suite de noeuds, size = "+size);
					ligne = br.readLine();
					noeuds = new ArrayList<Noeud>();
					for (int i = 0; i < size; i++) {
						noeuds.add(creerNoeud(ligne));
					}
				}
//				System.out.println("add prop");
				n.getProprietes().add(new Propriete(nom, stringValue, intValue, noeuds));
			}
			ligne = br.readLine();
			
		}
		return n;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Noeud getTree() {
		return tree;
	}

	public void setTree(Noeud tree) {
		this.tree = tree;
	}
}
