package rhapsodyVisualisation;

import interfaces.LongTask;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

import parser.Writer;
import modele.Comparateur;

import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPProject;
import com.telelogic.rhapsody.core.RhapsodyAppServer;

import donnees.Diagramme;

public class Visualiser extends Observable implements Runnable, LongTask, WindowListener{

	private Comparateur c;
	private String projectPath, projectName;
	private IRPApplication app;
	private IRPProject project;
	private boolean running, imagesConstructs;
	private ArrayList<BufferedImage> images;
	
	public Visualiser(){
		this.c = null;
		init();
	}
	
	public Visualiser(Comparateur c) {
		this.c = c;
		init();
	}
	
	private void init(){
		this.projectPath = null;
		this.projectName = null;
		this.app = null;
		this.running = false;
		this.images = new ArrayList<BufferedImage>();
		this.imagesConstructs = false;
	}
	
	public void initApplication(){
		new Thread(){
			public void run(){
				app = RhapsodyAppServer.createRhapsodyApplication();
			}
		}.start();
	}
	
	public void run(){		
		app = RhapsodyAppServer.getActiveRhapsodyApplication();
		writeProjectWithDiff();
		project = (IRPProject) app.openProject(projectPath);
		System.out.println(project.getDisplayName());
		ArrayList<IRPDiagram> diagrammes = new ArrayList<IRPDiagram>();
		String tmpPath = new File("tmp").getAbsolutePath().replace("\\", "\\\\")+"\\";
		for (Diagramme diag : c.getPremier().getDiagrammes()) {
			System.out.println("id : "+diag.getId());
			diagrammes.add((IRPDiagram) project.findElementByGUID(diag.getId()));
		}
		for (int j = 0; j < diagrammes.size(); j++) {
			System.out.println("diag de j="+j+" : "+diagrammes.get(j).toString());
			diagrammes.get(j).getPictureAs(tmpPath+"image"+j+".jpg", "JPG", 0, null);
		}
		for (int i = 0; i < diagrammes.size(); i++) {
			try {
				images.add(ImageIO.read(new File(tmpPath+"image"+i+".jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		running = false;
		update();
	}
	
	public void onDispose(){
		this.imagesConstructs = true;
		update();
	}
	
	private void writeProjectWithDiff(){
		String[] pathTab = projectPath.split("\\\\");
		String nomFichier = pathTab[pathTab.length-1];
		String[] tab = nomFichier.split("\\.");
		nomFichier = tab[0]+"_diff"+"."+tab[1];
		String s = "";
		for (int i = 0; i < pathTab.length-1; i++) {
			s += pathTab[i]+"\\";
		}
		nomFichier = s+nomFichier;
		Writer writer = new Writer(c.getPremier().getNoeud(), nomFichier);
		writer.setInitLigne(c.getInitLigne());
		writer.write();
		projectPath = nomFichier;
	}
	
	private void update(){
		setChanged();
		notifyObservers();
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isImagesConstructs() {
		return imagesConstructs;
	}

	public void setImagesConstructs(boolean imagesConstructs) {
		this.imagesConstructs = imagesConstructs;
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<BufferedImage> images) {
		this.images = images;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		RhapsodyAppServer.CloseSession();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
