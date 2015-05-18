package vues;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlleurs.VoirDiagsController;
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
	private JButton voirDiags;
	private JPanel container;
	
	public DifferencesPanel(Comparateur c) {
		super();
		this.c = c;
		this.c.addObserver(this);
		this.c.getVis().addObserver(this);
		this.container = new JPanel();
		this.container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		init();
	}
	
	private void init(){
		this.diffs = new JLabel("");
		this.voirDiags = new JButton("Voir diagrammes");
		this.voirDiags.addActionListener(new VoirDiagsController(c, this));
		this.voirDiags.setVisible(false);
		container.add(Box.createRigidArea(new Dimension(getWidth(), 20)));
		container.add(diffs);
		container.add(Box.createRigidArea(new Dimension(getWidth(), 20)));
		container.add(voirDiags);
		container.add(Box.createRigidArea(new Dimension(getWidth(), 20)));
		this.setViewportView(container);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (c.isComparaisonDone())
			voirDiags.setVisible(true);
		
		if (c.isComparaisonDone() && c.getDiffs().length() == 0)
			diffs.setText("Il n'y a pas de differences notables");
		else if (!c.isComparaisonDone()) 
			diffs.setText("");
		else 
			diffs.setText("<html>Differences : <br/>"+c.getDiffs().replaceAll("\n", "<br/>")+"</html>");
		
		if (c.getVis().isImagesConstructs()) {
			constructImage();
			c.getVis().setImagesConstructs(false);
		}
	}
	
	public void constructImage(){
		for (int i = 0; i < c.getVis().getImages().size(); i++) {
			ImagePanel p;
			try {
				p = new ImagePanel(c.getVis().getImages().get(i));
				p.repaint();
				container.add(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.revalidate();
		this.repaint();
	}

}
