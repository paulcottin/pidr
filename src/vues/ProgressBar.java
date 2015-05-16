package vues;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import modele.Comparateur;

/**
 * Barre de progression lors des parsing et comparaison un peu long
 * @author paul
 *
 */
public class ProgressBar extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JProgressBar bar;
	private Comparateur c;
	
	public ProgressBar(Comparateur c) {
		super("Processing...");
		this.c = c;
		this.c.addObserver(this);
		this.setSize(300, 50);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		bar = new JProgressBar();
		this.add(bar);
		bar.setIndeterminate(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("coucou : "+c.isRunning());
		if (!c.isRunning()) {
			this.dispose();
		}
	}
}
