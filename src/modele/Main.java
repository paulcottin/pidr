package modele;

<<<<<<< HEAD
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

=======
import donnees.Creator;
import donnees.Projet;
>>>>>>> 38a94ac50dcca2119e847ca9046ab168d91416b1
import donnees.Value;
import parser.Noeud;
import parser.Parser2;
import parser.Writer;

public class Main {
	
	public static void main(String[] args){
		/**
		 * Création du parser et parsage du fichier pour obtenir l'arbre de noeuds
		 */
<<<<<<< HEAD
		Parser2 p = new Parser2("test.rpy");
		Noeud n = p.parse();
//		Noeud test = n;
//		System.out.println("nb noeuds ("+test.getName()+") : "+test.getChildCount());
//		System.out.println("tree : "+n.getClasse()+" ("+n.getChildCount()+")");
//		System.out.println(n.toString());
//		for (Noeud no : n.getChilds()) {
//			if (!no.getName().equals("")) System.out.print(no.getName()+" : ");
//			if (no.getIntValue() != -8000) System.out.println(no.getIntValue());
//			if (no.getStringValue() != null) System.out.println(no.getStringValue());
//			if (no.getChilds() != null) System.out.println("Childs : "+no.getChildCount());
//		}
//		int i = 0;
//		for (Noeud no : test.getChilds()) {
//			System.out.print("child "+i+++" : ");
//			if (!no.getName().equals("")) System.out.print(no.getName()+" : ");
//			if (no.getIntValue() != -8000) System.out.println(no.getIntValue());
//			if (no.getStringValue() != null) System.out.println(no.getStringValue());
//			if (no.getChilds() != null) System.out.println("Nombre de childs : "+no.getChildCount());
//		}
		
		/**
		 * Affichage du parsing
		 */
//		try {
//			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("out")));
//			for (Propriete prop : n.getProprietes()) {
//				bw.write(prop.toString());
//			}
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		for (Propriete prop : n.getProprietes()) {
=======
		Parser p = new Parser("test.rpy");
		Noeud n = p.parse();
		/**
		 * Affichage du parsing
		 */
//		for (Propriete prop : p.getTree().getProprietes()) {
>>>>>>> 38a94ac50dcca2119e847ca9046ab168d91416b1
//			System.out.println(prop.toString());
//		}
		/**
		 * Test de l'écriture du projet dans un fichier
		 */
<<<<<<< HEAD
//		System.out.println(n.getClasse());
		Writer w = new Writer(n, "out.txt");
		w.setInitLigne(p.getInitLigne());
		w.write();
=======
//		Writer w = new Writer(n, "out.txt");
//		w.write();
		/**
		 * Créer un arbre de classe
		 */
		Creator c = new Creator(p.getTree());
		c.createProjet();
		
>>>>>>> 38a94ac50dcca2119e847ca9046ab168d91416b1
	}

}
