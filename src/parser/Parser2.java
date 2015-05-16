package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Gere la lecture du fichier et la constitution de l'arbre de Noeud
 * @author paul
 *
 */
public class Parser2 {

	private String path, initLigne;
	private Noeud tree;
	private String ligne;
	private Stack<Integer> sizePile;
	static BufferedReader br;
	static BufferedWriter bw;

	public Parser2() {
		init();
	}

	public Parser2(String path){
		init();
		this.path = path;
	}

	private void init(){
		path = "";
		tree = new Noeud();
		sizePile = new Stack<Integer>();
		initLigne = "";
	}

	public Noeud parse(){
		try {
			br = new BufferedReader(new FileReader(new File(path)));
			bw = new BufferedWriter(new FileWriter(new File("remarques.txt")));
			ligne = "";
			initLigne = br.readLine(); //On lit la première ligne qui contient des informations sur le langage I-Logix
			while(ligne != null){
				if (ligne.contains("{")) {
					tree = creerNoeud();
				}
				if (ligne != null) {
					ligne = br.readLine();
				}
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tree.setName("IProject");
		return tree;
	}

	private Noeud creerNoeud() throws IOException{
		while(ligne.contains("}")){
			ligne = br.readLine();
		}
		Noeud n = new Noeud();
		if (ligne.contains("=")) {
			int beginNom = ligne.indexOf("- ")+"- ".length();
			int endNom = ligne.indexOf("=")-1;
			String nom = ligne.substring(beginNom, endNom).replaceAll("\\s", "");
			n.setName(nom);
		}
		int mark = ligne.indexOf("{");

		n.setClasse(ligne.substring(mark+1, ligne.length()-1).replaceAll("\\s", ""));
		bw.write("-->"+n.getClasse()+"\n");
		ligne = br.readLine();
		return parcourirNoeud(n);
	}

	private Noeud parcourirNoeud(Noeud n) throws IOException{
		while(!ligne.contains("}")){
			//Propriété
			if (ligne.contains("- ")) {
				int intValue = -8000;
				String stringValue = null;
				String nom = null;
				int beginNom = ligne.indexOf("- ")+"- ".length();
				int endNom = ligne.indexOf("=")-1;
				nom = ligne.substring(beginNom, endNom).replaceAll("\\s", "");
				//				n.setName(nom);
				bw.write("nom : "+nom+"\n");
				if (nom.equals("value") && ligne.contains(";")) {
					sizePile.pop();
				}
				//Sauvegarde de la valeur de size pour la gestion de la suite de noeuds
				if (nom.equals("size") || nom.equals("elementList")) {
					intValue = Integer.valueOf(ligne.substring(endNom+3, ligne.indexOf(";")));
					sizePile.push(intValue);
				}
				//Une propriété "finale"/simple
				if (ligne.contains(";") && !nom.equals("elementList")) {
					int beginValue = endNom + 2;
					int endValue = ligne.indexOf(";");
					stringValue = ligne.substring(beginValue+1, endValue);
					if (stringValue.matches("\\s*-?\\d+\\s*")) {
						intValue = Integer.parseInt(stringValue.replaceAll("\\s", ""));
						n.getChilds().add(new Noeud(nom, intValue, null, null));
					}else {
						if (nom.equals("elementList"))
							n.getChilds().add(new Noeud("", -8000, stringValue, null));
						else
							n.getChilds().add(new Noeud(nom, -8000, stringValue, null));
					}
				}
				else if (nom.equals("elementList")) {
					intValue = Integer.valueOf(ligne.substring(endNom+3, ligne.indexOf(";")));
					sizePile.push(intValue);
					n.setIntValue(intValue);
					n.getChilds().add(new Noeud(nom, intValue, null, null));

					ligne = br.readLine();
					ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
					int size = sizePile.pop();
					for (int i = 0; i < size; i++) {
						Noeud t = new Noeud();
						t = creerNoeud();
						noeuds.add(t);
					}
					n.getChilds().add(new Noeud("", -8000, null, noeuds));
				}
				else if (nom.equals("frameset")) {
					int beginValue = endNom + 2;
					int endValue = ligne.length();
					stringValue = ligne.substring(beginValue, endValue);
					while(!ligne.contains(";")){
						ligne = br.readLine();
						stringValue += ligne;
					}
					stringValue = stringValue.substring(0, stringValue.indexOf(";"));
					n.getChilds().add(new Noeud(nom, -8000, stringValue, null));
				}
				//Une propriété qui est un noeud
				else if (ligne.contains("=") && ligne.contains("{")) {
					//System.out.println("nvlle prop noeud "+nom+": _"+n.getName()+"_");
					Noeud t = creerNoeud();
					ArrayList<Noeud> noeud = new ArrayList<Noeud>();
					noeud.add(t);
					n.getChilds().add(new Noeud(nom, -8000, null, noeud));

				}
				// une propriété qui est une suite de noeuds
				else if (nom.equals("value") && !ligne.contains(";")) {
					ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
					ligne = br.readLine();

					int size = sizePile.pop();
					for (int i = 0; i < size; i++) {
						Noeud t = creerNoeud();
						noeuds.add(t);

					}
					n.getChilds().add(new Noeud(nom, -8000, null, noeuds));
				}
				else{
					System.out.println("ERROR !!");
				}
			}
			else {
				//Ligne vide
			}
			ligne = br.readLine();

		}
		bw.write("("+n.getClasse()+")}");
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

	public String getInitLigne() {
		return initLigne;
	}

	public void setInitLigne(String initLigne) {
		this.initLigne = initLigne;
	}
}
