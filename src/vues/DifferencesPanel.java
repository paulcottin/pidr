package vues;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
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
public class DifferencesPanel extends JPanel implements Observer{

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
		this.container = new JPanel();
		init();
	}
	
	private void init(){
		this.diffs = new JLabel("");
		this.voirDiags = new JButton("Voir diagrammes");
		this.voirDiags.addActionListener(new VoirDiagsController(c, this));
		container.add(diffs);
		container.add(voirDiags);
//		this.setViewportView(container);
		this.add(container);
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
	
	public void constructImage(){
		ImagePanel p;
		try {
			p = new ImagePanel();
			p.repaint();
			container.add(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		container.repaint();
		container.revalidate();
		System.out.println("components : ");
		for (Component c : container.getComponents()) {
			System.out.println(c.toString());
		}
//		this.setViewportView(null);
//		this.setViewportView(container);
		this.revalidate();
		this.repaint();
	}

}
