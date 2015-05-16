package vues;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import modele.Comparateur;

import controlleurs.ChoixFichierController;
import controlleurs.EnregistrerController;
import controlleurs.QuitterController;
import controlleurs.RunController;

/**
 * Barre de menu
 * @author paul
 *
 */
public class MenuBar extends JMenuBar implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Comparateur c;

	private JMenu fichier, execution;
	private JMenuItem quitter, choixFichier1, choixfichier2, enregistrer;
	private JMenuItem compare;
	
	public MenuBar(Comparateur c) {
		super();
		this.c = c;
		this.c.addObserver(this);
		init();
		construct();
	}
	
	private void init(){
		fichier = new JMenu("Fichier");
		quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new QuitterController(c));
		choixFichier1 = new JMenuItem("Choix fichier 1");
		choixFichier1.addActionListener(new ChoixFichierController(c, 1));
		choixfichier2 = new JMenuItem("Choix fichier 2");
		choixfichier2.addActionListener(new ChoixFichierController(c, 2));
		enregistrer = new JMenuItem("Enregistrer");
		enregistrer.addActionListener(new EnregistrerController(c));
		enregistrer.setEnabled(false);
		
		execution = new JMenu("Lancer");
		compare = new JMenuItem("Comparer");
		compare.addActionListener(new RunController(c, compare));
		compare.setEnabled(false);
	}
	
	private void construct(){
		fichier.add(choixFichier1);
		fichier.add(choixfichier2);
		fichier.add(enregistrer);
		fichier.addSeparator();
		fichier.add(quitter);
		
		execution.add(compare);
		
		this.add(fichier);
		this.add(execution);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (c.getPremier() != null && c.getDeuxieme() != null) {
			compare.setEnabled(true);
			enregistrer.setEnabled(true);
		}
		
	}

}

