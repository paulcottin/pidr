package controlleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		Thread t = new Thread(c.getVis());
		c.getVis().setRunning(true);
		t.start();
		
		if (c.getVis().isRunning())
			new ProgressBar(c.getVis());
	}

}
