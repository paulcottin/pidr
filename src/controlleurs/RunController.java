package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import vues.ProgressBar;

import modele.Comparateur;

public class RunController implements ActionListener{

	private Comparateur c;
	private JMenuItem lancerItem;
	
	public RunController(Comparateur c, JMenuItem lancerItem) {
		this.c = c;
		this.lancerItem = lancerItem;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		lancerItem.setEnabled(false);
		Thread runThread = new Thread(c);
		runThread.start();
		
		if (c.isRunning()) {
			@SuppressWarnings("unused")
			ProgressBar bar = new ProgressBar(c);
		}
	}

}
