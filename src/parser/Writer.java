package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	
	private Noeud noeud;
	static BufferedWriter bw;
	private String path;
	private int size;
	
	public Writer() {
		init();
	}
	
	public Writer(Noeud n, String path){
		init();
		noeud = n;
		this.path = path;
	}
	
	public void write(){
		try {
			bw = new BufferedWriter(new FileWriter(new File(path)));
			bw.write("I-Logix-RPY-Archive version 8.10.0 C++ 8169320\n");
			System.out.println("I-Logix-RPY-Archive version 8.10.0 C++ 8169320");
			writeNoeud(noeud);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeNoeud(Noeud n) throws IOException{
		bw.write("{ "+n.getClasse()+"\n");
		System.out.println("{ "+n.getClasse()+"\n");
		for (Propriete p : n.getProprietes()) {
			writePropriete(p);
		}
		if (n.getChildCount() > 0) {
			for (Noeud noeud : n.getChilds()) {
				writeNoeud(noeud);
			}
		}
		bw.write("}\n");
		System.out.println("}");
	}
	
	private void writePropriete(Propriete p) throws IOException{
		//Si c'est une propritété de type "size" on sauvegarde pour écrire le bon nombre de child ensuite
		if (p.getNom().equals("size")) {
			this.size = p.getIntValue();
		}
		//Si c'est une propriété simple
		if (!(p.getIntValue() == -8000 && p.getStringValue() == null)) {
			bw.write("- "+p.getNom()+" = "+((p.getIntValue() == -8000) ? p.getStringValue() : p.getIntValue())+";\n");
			System.out.println("- "+p.getNom()+" = "+((p.getIntValue() == -8000) ? p.getStringValue() : p.getIntValue())+";\n");
		}
		//Si c'est un noeud
		else{
			bw.write("- "+p.getNom()+" = ");
			System.out.println("- "+p.getNom()+" = ");
			for (Noeud n : p.getNoeuds()) {
				writeNoeud(n);
			}
		}
	}
	
	private void init(){
		noeud = new Noeud();
		path = "";
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

}
