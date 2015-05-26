package modele;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import parser.Noeud;
import parser.Parser2;
import parser.Writer;
import vues.Fenetre;

/**
 * Lance le comparateur de fichiers rhapsody
 * @author paul
 *
 */
public class Main {

	public static void main(String[] args){
		/**
		 * Lancement de rhapsody dans un thread à part car long à charger
		 */


		/**
		 * Création du parser et parsage du fichier pour obtenir l'arbre de noeuds
		 */

		//		Parser2 p = new Parser2("liaisonCouleur.rpy");
		//		Parser2 p = new Parser2("C:\\Program Files\\IBM\\Rational\\Rhapsody\\8.1.1\\Test\\Test.rpy");
		//		Noeud n = p.parse();

		//		p.setPath("C:\\Program Files\\IBM\\Rational\\Rhapsody\\8.1.1\\Test\\Test.rpy");
		//		Noeud n1 = p.parse();


		//		System.out.println("name : "+n.getName());
		//		System.out.println(n.getChilds().size());
		//		System.out.println(n.getChilds().get(0).getName()+"("+n.getChilds().get(0).getChilds()+")");
		//		System.out.println(n.getChilds().get(1).getName()+"("+n.getChilds().get(1).getChilds()+")");
		//		System.out.println(n.getChilds().get(2).getName()+"("+n.getChilds().get(2).getChilds()+"), classe : "+n.getChilds().get(2).getClasse());
		//		System.out.println(n.getChilds().get(2).getChilds().get(0).getName()+"("+n.getChilds().get(2).getChilds().get(0).getChilds()+")");
		//		System.out.println(n.getChilds().get(2).getChilds().get(0).getChilds().get(1).getChilds().get(0));
		//		System.out.println("--");


		/**
		 * Création de l'arbre plus abstrait
		 */
		//		Projet proj = new Projet(n);
		//		Projet proj2 = new Projet(n1);


		/**
		 * Comparaison des deux projets
		 */

		//		Comparateur c = new Comparateur(proj, proj2);

		/**
		 * Ecriture du projet dans un fichier
		 */
		//		Writer w = new Writer(n, "out.txt");
		//		w.setInitLigne(p.getInitLigne());
		//		w.write();

		/**
		 * Avec GUI
		 */
		//		System.getSecurityManager();
		//		Path pathDLL = FileSystems.getDefault().getPath("C:\\Program Files\\IBM\\Rational\\Rhapsody\\8.1.1\\Share\\JavaAPI\\rhapsody.dll");
		//		Path pathLib = FileSystems.getDefault().getPath("C:\\Program Files\\Java\\jre7\\bin", "rhapsody.dll");
		//		
		//		try {
		//			Files.copy(pathDLL, pathLib, StandardCopyOption.REPLACE_EXISTING);
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}

		//		String libpath = System.getProperty("java.library.path");
		//		System.out.println(libpath);
		//    	libpath = libpath + ";C:\\Program Files\\IBM\\Rational\\Rhapsody\\8.1.1\\Share\\JavaAPI\\rhapsody";
		//    	System.setProperty("java.library.path",libpath);
		//    	System.out.println(System.getProperty("java.library.path"));

		/**
		 * Ajoute un nouveau répertoire dans le java.library.path.
		 * @param dir Le nouveau répertoire à ajouter.
		 */
		File dir = new File("C:\\Program Files\\IBM\\Rational\\Rhapsody\\8.1.1\\Share\\JavaAPI");
		final String LIBRARY_PATH = "java.library.path";
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(dir + " is not a directory.");
		}
		String javaLibraryPath = System.getProperty(LIBRARY_PATH);
		System.setProperty(LIBRARY_PATH, javaLibraryPath + File.pathSeparatorChar + dir.getAbsolutePath());

		resetJavaLibraryPath();


		/**
		 * Supprime le cache du "java.library.path".
		 * Cela forcera le classloader à revérifier sa valeur lors du prochaine chargement de librairie.
		 * 
		 * Attention : ceci est spécifique à la JVM de Sun et pourrait ne pas fonctionner
		 * sur une autre JVM...
		 */



		Comparateur c = new Comparateur();
		@SuppressWarnings("unused")
		Fenetre f = new Fenetre(c);


	}

	public static void resetJavaLibraryPath() {
		synchronized(Runtime.getRuntime()) {
			try {
				java.lang.reflect.Field field = ClassLoader.class.getDeclaredField("usr_paths");
				field.setAccessible(true);
				field.set(null, null);

				field = ClassLoader.class.getDeclaredField("sys_paths");
				field.setAccessible(true);
				field.set(null, null);
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
