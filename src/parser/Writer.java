package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
			bw.write(initLigne+"\n");
			writeNoeud(noeud);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeNoeud(Noeud n) throws IOException{
		if (suiteNoeud) {
			bw.write(writeDecalage()+"{ "+n.getClasse()+"\n");
			decalage++;
		}else {
			bw.write("{ "+n.getClasse()+"\n");
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
		bw.write(writeDecalage()+"}\n");
	}
	
	private void writeNoeudHelper(Noeud n) throws IOException{
		if (n.getName().equals("size")) {
			this.size = n.getIntValue();
		}
		//Si c'est une propriété simple
		if (!(n.getIntValue() == -8000 && n.getStringValue() == null)) {
			bw.write(writeDecalage()+"- "+n.getName()+" = "+((n.getIntValue() == -8000) ? n.getStringValue() : n.getIntValue())+";\n");
		}
		//Si c'est un noeud
		else {
			bw.write(writeDecalage()+""+((!n.getName().equals("") ? "- " : ""))+n.getName()+""+((!n.getName().equals("") ? " = " : "")));
			
			if (n.getName().equals("value")) {
				bw.write("\n");
				bw.write(writeDecalage());
			}
			for (Noeud no : n.getChilds()) {
				if (no.getName().equals("value") || no.getName().equals("elementList"))
					suiteNoeud = true;
				else
					suiteNoeud = false;
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
