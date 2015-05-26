package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import donnees.Projet;
import exceptions.BadExtensionException;

import parser.Noeud;
import parser.Parser2;

import modele.Comparateur;

/**
 * Listener pour le choix des deux fichier .rpy a comparer
 * @author paul
 *
 */
public class ChoixFichierController implements ActionListener{

	private Comparateur c;
	private int choixFichier;
	
	public ChoixFichierController(Comparateur c, int choixFichier) {
		this.c = c;
		this.choixFichier = choixFichier;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileFilter imagesFilter = new FileNameExtensionFilter("Rhapsody file", "rpy", "sbs");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(imagesFilter);
		fileChooser.setCurrentDirectory(new File(c.getPath1() != null ? c.getPath1() : "C:\\Program Files\\IBM\\Rational\\Rhapsody\\8.1.1\\Test\\Test.rpy"));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			if (f.getName().contains(".")) {
				String extension = f.getName().substring(f.getName().indexOf(".")+1);
				if (extension.equals("rpy") || extension.equals("sbs")) {
					if (choixFichier == 1){
						c.setPath1(f.getAbsolutePath());
					}
					else if (choixFichier == 2) {
						c.setPath2(f.getAbsolutePath());
					}
				}else
					new BadExtensionException("rpy", extension);
			}else
				new BadExtensionException("rpy", "[vide]");
		}	
	}
}