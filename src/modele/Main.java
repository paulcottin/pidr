package modele;

import parser.Noeud;
import parser.Parser;
import parser.Propriete;
import parser.Writer;

public class Main {
	
	public static void main(String[] args){
		Parser p = new Parser("test.rpy");
		Noeud n = p.parse();
//		for (Propriete prop : n.getProprietes()) {
//			System.out.println(prop.toString());
//		}
		Writer w = new Writer(n, "out.txt");
		w.write();
	}

}
