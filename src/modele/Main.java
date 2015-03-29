package modele;

import parser.Noeud;
import parser.Parser;
import parser.Propriete;
import parser.Writer;

public class Main {
	
	public static void main(String[] args){
		Parser p = new Parser("test.rpy");
		Noeud n = p.parse();
		/**
		 * Affichage du parsing
		 */
//		for (Propriete prop : n.getProprietes()) {
//			System.out.println(prop.toString());
//		}
		/**
		 * Test de l'écriture du projet dans un fichier
		 */
//		Writer w = new Writer(n, "out.txt");
//		w.write();
	}

}
