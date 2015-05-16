package vues;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.Comparateur;

/**
 * Panel central qui liste les differences recensee lors de la comparaison
 * @author paul
 *
 */
public class DifferencesPanel extends JScrollPane implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Comparateur c;
	private JLabel diffs;
	
	public DifferencesPanel(Comparateur c) {
		super();
		this.c = c;
		this.c.addObserver(this);
		init();
	}
	
	private void init(){
		diffs = new JLabel("");
		JPanel view = new JPanel();
		view.add(diffs);
		this.setViewportView(view);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (c.isComparaisonDone() && c.getDiffs().length() == 0) 
			diffs.setText("Il n'y a pas de differences notables");
		else if (!c.isComparaisonDone()) 
			diffs.setText("");
		else 
			diffs.setText("<html>Differences : <br/>"+c.getDiffs().replaceAll("\n", "<br/>")+"</html>");
	}

}
