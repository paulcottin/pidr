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

public class ChoixFichierController implements ActionListener{

	private Comparateur c;
	private int choixFichier;
	
	public ChoixFichierController(Comparateur c, int choixFichier) {
		this.c = c;
		this.choixFichier = choixFichier;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileFilter imagesFilter = new FileNameExtensionFilter("Rhapsody file", "rpy");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(imagesFilter);
		fileChooser.setCurrentDirectory(new File("README.md"));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			if (f.getName().contains(".")) {
				String extension = f.getName().substring(f.getName().indexOf(".")+1);
				if (extension.equals("rpy")) {
					Parser2 p = new Parser2(f.getPath());
					Noeud n = p.parse();
					if (choixFichier == 1){
						c.setPremier(new Projet(n));
					}
					else if (choixFichier == 2) {
						c.setDeuxieme(new Projet(n));
					}
				}else
					new BadExtensionException("rpy", extension);
			}else
				new BadExtensionException("rpy", "[vide]");
		}	
	}
}