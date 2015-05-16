package vues;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Comparateur;

/**
 * Panel qui affiche les noms des fichiers compares
 * @author paul
 *
 */
public class FilesInformations extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Comparateur c;
	private JLabel file1Path, file2Path, fileSeparator;
	
	public FilesInformations(Comparateur c) {
		this.c = c;
		this.c.addObserver(this);
		init();
	}
	
	private void init(){
		file1Path = new JLabel("Fichier 1 : [Vide]");
		fileSeparator = new JLabel(" / ");
		file2Path = new JLabel("Fichier 2 : [Vide]");
		
		this.add(file1Path);
		this.add(fileSeparator);
		this.add(file2Path);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (c.getPath1() != null) {
			file1Path.setText("Fichier 1 : "+c.getPath1());
		}
		else file1Path.setText("Fichier 1 : [Vide]");
		if (c.getPath2() != null) {
			file2Path.setText("Fichier 2 : "+c.getPath2());
		}
		else file2Path.setText("Fichier 2 : [Vide]");
	}

}
