package vues;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	
	public ImagePanel() throws IOException {
		this.img = ImageIO.read(new File("C:\\Users\\paul\\Desktop\\image1.jpg"));
		this.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
	}
	
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
	    g.drawImage(img, 0, 0, this);
	  }

}
