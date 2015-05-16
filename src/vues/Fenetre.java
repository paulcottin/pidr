package vues;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modele.Comparateur;

/**
 * Fenetre principale de l'application
 * @author paul
 *
 */
public class Fenetre extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Comparateur c;
	private JPanel container;
	
	public Fenetre(Comparateur c) {
		super("SysML comparaison");
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.c = c;
		
		this.container = new JPanel();
		this.container.setLayout(new BorderLayout());
		this.container.add(new FilesInformations(c), BorderLayout.NORTH);
		this.container.add(new DifferencesPanel(c), BorderLayout.CENTER);
		
		this.setContentPane(container);
		
		this.setJMenuBar(new MenuBar(c));
		this.setVisible(true);
	}

}
