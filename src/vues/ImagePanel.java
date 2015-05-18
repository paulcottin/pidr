package vues;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import magick.ImageInfo;
import magick.ImageMagick;
import magick.MagickException;
import magick.MagickImage;
import magick.MagickLoader;

public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	
	public ImagePanel() throws IOException {
		try {
			ImageInfo origInfo = new ImageInfo("C:\\Users\\paul\\Desktop\\image1.emf"); //load image info
			MagickImage image = new MagickImage(origInfo); //load image
			image.setFileName("C:\\Users\\paul\\Desktop\\image1.jpg");
			image.writeImage(origInfo); //save
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //give new location
		
		this.img = ImageIO.read(new File("C:\\Users\\paul\\Desktop\\image1.jpg"));
		this.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
	}
	
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
	    g.drawImage(img, 0, 0, this);
	  }

}
