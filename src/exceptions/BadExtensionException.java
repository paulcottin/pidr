package exceptions;

import javax.swing.JOptionPane;

public class BadExtensionException {
	
	private String good, bad;

	public BadExtensionException(String goodExtension, String badExtension) {
		this.good = goodExtension;
		this.bad = badExtension;
		execute();
	}
	
	private void execute(){
		JOptionPane.showMessageDialog(null, "You must give a \"."+good+"\" file, not a \""+bad+"\" file ", "Bad file extension!", JOptionPane.ERROR_MESSAGE);
	}

}
