package exceptions;

import javax.swing.JOptionPane;

/**
 * Erreur de correspondance entre les projets a comparer 
 * (difference des IDs)
 * @author paul
 *
 */
public class BadProjectCorrespondance {

	public BadProjectCorrespondance() {
		execute();
	}
	
	private void execute(){
		JOptionPane.showMessageDialog(null, "Erreur de correspondance entre les projets", "Erreur de correspondance", JOptionPane.ERROR_MESSAGE);
		Thread.currentThread().interrupt();
	}

}