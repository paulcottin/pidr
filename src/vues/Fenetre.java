package vues;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modele.Comparateur;

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
		this.container.add(new DifferencesPanel(c));
		
		this.setContentPane(container);
		
		this.setJMenuBar(new MenuBar(c));
		this.setVisible(true);
	}

}
