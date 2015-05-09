package modele;

import parser.Noeud;
import parser.Parser2;
import parser.Writer;

public class Main {
	
	public static void main(String[] args){
		/**
		 * Création du parser et parsage du fichier pour obtenir l'arbre de noeuds
		 */

		Parser2 p = new Parser2("test.rpy");
		Noeud n = p.parse();

		/**
		 * Ecriture du projet dans un fichier
		 */
		Writer w = new Writer(n, "out.txt");
		w.setInitLigne(p.getInitLigne());
		w.write();
	}

}
