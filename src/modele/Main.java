package modele;

import donnees.Creator;
import donnees.Projet;
import donnees.Value;
import parser.Noeud;
import parser.Parser;
import parser.Propriete;
import parser.Writer;

public class Main {
	
	public static void main(String[] args){
		/**
		 * Création du parser et parsage du fichier pour obtenir l'arbre de noeuds
		 */
		Parser p = new Parser("test.rpy");
		Noeud n = p.parse();
		/**
		 * Affichage du parsing
		 */
//		for (Propriete prop : p.getTree().getProprietes()) {
//			System.out.println(prop.toString());
//		}
		/**
		 * Test de l'écriture du projet dans un fichier
		 */
//		Writer w = new Writer(n, "out.txt");
//		w.write();
		/**
		 * Créer un arbre de classe
		 */
		Creator c = new Creator(p.getTree());
		c.createProjet();
		
	}

}
