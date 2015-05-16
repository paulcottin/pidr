package exceptions;

import javax.swing.JOptionPane;

/**
 * Il existe des diagrammes qui ne correspondent pas dans un projet.
 * @author paul
 *
 */
public class BadDiagramCorrespondance {

	public BadDiagramCorrespondance() {
		execute();
	}
	
	private void execute(){
		JOptionPane.showMessageDialog(null, "Erreur de correspondance entre les diagrammes <br/>Est-ce les memes projets ?", "Erreur de correspondance", JOptionPane.ERROR_MESSAGE);
		Thread.currentThread().interrupt();
	}

}