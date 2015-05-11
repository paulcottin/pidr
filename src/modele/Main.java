package modele;

import donnees.Projet;
import parser.Noeud;
import parser.Parser2;
import parser.Writer;

public class Main {
	
	public static void main(String[] args){
		/**
		 * Création du parser et parsage du fichier pour obtenir l'arbre de noeuds
		 */

//		Parser2 p = new Parser2("liaisonCouleur.rpy");
		Parser2 p = new Parser2("test.rpy");
		Noeud n = p.parse();
		
		p.setPath("blocksCouleurs.rpy");
		Noeud n1 = p.parse();
		
		
//		System.out.println("name : "+n.getName());
//		System.out.println(n.getChilds().size());
//		System.out.println(n.getChilds().get(0).getName()+"("+n.getChilds().get(0).getChilds()+")");
//		System.out.println(n.getChilds().get(1).getName()+"("+n.getChilds().get(1).getChilds()+")");
//		System.out.println(n.getChilds().get(2).getName()+"("+n.getChilds().get(2).getChilds()+")");
//		System.out.println(n.getChilds().get(2).getChilds().get(0).getName()+"("+n.getChilds().get(2).getChilds()+")");
//		System.out.println(n.getChilds().get(2).getChilds().get(0).getChilds().get(1).getChilds().get(0));
//		System.out.println("--");


		/**
		 * Création de l'arbre plus abstrait
		 */
		Projet proj = new Projet(n);
		Projet proj2 = new Projet(n1);
		
		
		/**
		 * Comparaison des deux projets
		 */
		
		Comparateur c = new Comparateur(proj, proj2);
		
		
		/**
		 * Ecriture du projet dans un fichier
		 */
		Writer w = new Writer(n, "out.txt");
		w.setInitLigne(p.getInitLigne());
		w.write();
	}

}
