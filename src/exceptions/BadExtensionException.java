package exceptions;

import javax.swing.JOptionPane;

/**
 * Erreur d'extension de fichier lors de l'enregistrement.
 * @author paul
 *
 */
public class BadExtensionException {
	
	private String good, bad;

	public BadExtensionException(String goodExtension, String badExtension) {
		this.good = goodExtension;
		this.bad = badExtension;
		execute();
	}
	
	private void execute(){
		JOptionPane.showMessageDialog(null, "Vous devez donner un fichier \"."+good+"\" et non un \"."+bad+"\" file ", "Erreur d'extension !", JOptionPane.ERROR_MESSAGE);
	}

}
