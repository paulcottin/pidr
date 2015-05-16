package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Comparateur;

/**
 * Listener pour quitter le programme
 * @author paul
 *
 */
public class QuitterController implements ActionListener{

	Comparateur c;
	
	public QuitterController(Comparateur c) {
		this.c = c;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}

}
