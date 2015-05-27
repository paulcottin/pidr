package vues;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Comparateur;

public class Legende extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel modif, suppr, add;
	JPanel mod, sup, ad;
	Comparateur c;
	
	public Legende(Comparateur c) {
		super();
		this.c = c;
		c.addObserver(this);
		init();
		construct();
		this.setVisible(false);
	}
	
	private void init(){
		modif = new JLabel("    Modification");
		suppr = new JLabel("    Suppression");
		add = new JLabel("Ajout");
		
		(mod = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(new Color(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B));
				g.fillRect(0, 0, 20, 20);
			}
		}).repaint();
		
		(ad = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(new Color(Comparateur.ADD_R, Comparateur.ADD_G, Comparateur.ADD_B));
				g.fillRect(0, 0, 20, 20);
			}
		}).repaint();
		
		(sup = new JPanel(){
			public void paintComponent(Graphics g){
				g.setColor(new Color(Comparateur.SUPPR_R, Comparateur.SUPPR_G, Comparateur.SUPPR_B));
				g.fillRect(0, 0, 20, 20);
			}
		}).repaint();
	}
	
	private void construct(){
		this.add(add);
		this.add(ad);
		this.add(modif);
		this.add(mod);
		this.add(suppr);
		this.add(sup);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (c.isComparaisonDone()) {
			this.setVisible(true);
		}
	}

}
