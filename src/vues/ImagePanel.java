package vues;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import modele.Comparateur;

public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	
	public ImagePanel(BufferedImage img) throws IOException {
		this.img = img;
		this.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
	}
	
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
	    g.drawImage(img, 0, 0, this);
	  }

}
