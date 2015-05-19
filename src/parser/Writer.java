package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * Ecrit dans un fichier le contenu d'un arbre de Noeud.
 * @author paul
 *
 */
public class Writer {
	
	private Noeud noeud;
	static BufferedWriter bw;
	private String path, initLigne;
	@SuppressWarnings("unused")
	private int size, decalage;
	private boolean suiteNoeud;
	
	public Writer() {
		init();
	}
	
	public Writer(Noeud n, String path){
		init();
		noeud = n;
		this.path = path;
	}
	
	private void init(){
		noeud = new Noeud();
		path = "";
		decalage = 0;
		suiteNoeud = false;
		initLigne = "";
	}
	
	public void write(){
		try {
			bw = new BufferedWriter(new FileWriter(new File(path)));
			bw.write(initLigne+"\r\n");
			writeNoeud(noeud);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeNoeud(Noeud n) throws IOException{
		if (suiteNoeud) {
			suiteNoeud = false;
//			System.out.println("nom : "+n.getName());
			bw.write(writeDecalage()+"{ "+n.getClasse()+" \r\n");
			decalage++;
		}else {
			bw.write("{ "+n.getClasse()+" \r\n");
			decalage++;
		}
		if (n.getChilds() != null) {
			if (n.getChildCount() > 0) {
				for (Noeud noeud : n.getChilds()) {
					writeNoeudHelper(noeud);
				}
			}
		}
		decalage--;
		bw.write(writeDecalage()+"}\r\n");
	}
	
	private void writeNoeudHelper(Noeud n) throws IOException{
		
		if (n.getName().equals("size")) {
			this.size = n.getIntValue();
		}
		//Si c'est une propriété simple
		if (!(n.getIntValue() == -8000 && n.getStringValue() == null)) {
			bw.write(writeDecalage()+"- "+n.getName()+" = "+((n.getIntValue() == -8000) ? n.getStringValue() : n.getIntValue())+";\r\n");
			if (n.getName().equals("value") || n.getName().equals("elementList")){
				suiteNoeud = true;
//				System.out.println(n.getName()+", suite noeud : "+suiteNoeud);
			}
		}
		//Si c'est un noeud
		else {
			
			bw.write(writeDecalage()+""+((!n.getName().equals("") ? "- " : ""))+n.getName()+""+((!n.getName().equals("") ? " = " : "")));
			
			if (n.getName().equals("value")) {
				bw.write("\r\n");
				bw.write(writeDecalage());
			}
			for (Noeud no : n.getChilds()) {
				writeNoeud(no);
			}
		}
	}
	
	private String writeDecalage(){
		String s = "";
		for (int i = 0; i < decalage; i++) {
			s += "\t";
		}
		return s;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getInitLigne() {
		return initLigne;
	}

	public void setInitLigne(String initLigne) {
		this.initLigne = initLigne;
	}

}
