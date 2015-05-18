package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import rhapsodyVisualisation.Visualiser;
import vues.DifferencesPanel;
import vues.ProgressBar;
import modele.Comparateur;

public class VoirDiagsController implements ActionListener{

	Comparateur c;
	DifferencesPanel panel;
	
	public VoirDiagsController(Comparateur c, DifferencesPanel panel) {
		this.c = c;
		this.panel = panel;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		c.getVis().setProjectPath(c.getPath1());
		Visualiser vis = new Visualiser(c);
		vis.setProjectPath(c.getPath1());
		vis.setRunning(true);
		Thread runThread = new Thread(vis);
		runThread.start();
		try {
			runThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panel.constructImage();
		if (vis.isRunning()) {
			@SuppressWarnings("unused")
			ProgressBar bar = new ProgressBar(vis);
		}
	}

}
