package vues;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Comparateur;

public class DifferencesPanel extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Comparateur c;
	private JLabel diffs;
	
	public DifferencesPanel(Comparateur c) {
		this.c = c;
		this.c.addObserver(this);
		init();
	}
	
	private void init(){
		diffs = new JLabel("Il n'y a pas de differences notables");
		this.add(diffs);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (c.getDiffs().length() == 0) {
			diffs.setText("Il n'y a pas de differences notables");
		}
		else
			diffs.setText("<html>Differences : <br/>"+c.getDiffs().replaceAll("\n", "<br/>")+"</html>");
	}

}
