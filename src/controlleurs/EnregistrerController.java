package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import parser.Writer;

import modele.Comparateur;

/**
 * Listener pour enregistrer le resultat d'une comparaison (le plus long fichier des deux)
 * @author paul
 *
 */
public class EnregistrerController implements ActionListener{

	private Comparateur c;
	
	public EnregistrerController(Comparateur c) {
		this.c = c;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		FileFilter imagesFilter = new FileNameExtensionFilter("Rhapsody file", "rpy");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Enregistrer");
		fileChooser.setFileFilter(imagesFilter);
		fileChooser.setCurrentDirectory(new File("README.md"));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String path = checkExtension(f);
			Writer writer = new Writer();
			writer.setInitLigne(c.getInitLigne());
			if (c.getDiffs().length() == 0) {
				writer.setNoeud(c.getPremier().getProjet().getNoeud());
				writer.setPath(path);
			}
			else {
				writer.setNoeud(c.getDeuxieme().getProjet().getNoeud());
				writer.setPath(path);
			}
			writer.write();
		}
	}
	
	private String checkExtension(File f){
		String s = f.getPath();
		String nom = s.split("/")[s.split("/").length-1];
		String path = s.substring(0, s.length()-nom.length());
		if (!nom.contains(".")) nom += ".rpy";
		else nom = nom.substring(0, nom.indexOf("."))+".rpy";
		return path+nom;
	}

}
