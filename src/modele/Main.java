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
