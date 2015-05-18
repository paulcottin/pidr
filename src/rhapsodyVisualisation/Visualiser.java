package rhapsodyVisualisation;

import interfaces.LongTask;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;

import vues.DifferencesPanel;
import modele.Comparateur;

import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPProject;
import com.telelogic.rhapsody.core.RhapsodyAppServer;

import donnees.Diagramme;

public class Visualiser extends Observable implements Runnable, LongTask{

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
//		try {
//			app = RhapsodyAppServer.createRhapsodyApplication();
//			System.out.println("app créée");
//		} catch (RhapsodyRuntimeException e) {
//			e.printStackTrace();
//		}
//		RhapsodyAppServer.setDefultClassFactory(new ClassesFactory());
		
		app = RhapsodyAppServer.getActiveRhapsodyApplication();
		project = (IRPProject) app.openProject(projectPath);
		System.out.println(project.getDisplayName());
		ArrayList<IRPDiagram> diagrammes = new ArrayList<IRPDiagram>();
		for (Diagramme diag : c.getPremier().getDiagrammes()) {
			diagrammes.add((IRPDiagram) project.findElementByGUID(diag.getId()));
		}
		for (int j = 0; j < diagrammes.size(); j++) {
			diagrammes.get(j).getPictureAs("tmp\\image"+j+".jpg", "JPG", 0, null);
		}
		for (int i = 0; i < diagrammes.size(); i++) {
			try {
				images.add(ImageIO.read(new File("tmp\\image"+i+".jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		running = false;
		update();
//		diag.getPictureEx("C:\\Users\\paul\\Desktop\\testJPG.jpg", "JPG", 0);
		
//		if(app != null)	{
//			project = (IRPProject) app.openProject("C:\\Program Files\\IBM\\Rational\\Rhapsody\\8.1.1\\Test\\Test.rpy");
//			System.out.println(project);
//			System.out.println(project.getDisplayName());
//			System.out.println("nb components diagrams : "+project.getComponentDiagrams().getCount());
//			System.out.println("nb components : "+project.getComponents().getCount());
//			for (int i = 1; i < project.getComponents().getCount()+1; i++) {
//				System.out.println("component name : "+project.getComponents().getItem(i));
//			}
//			IRPDiagram diag = (IRPDiagram) project.getComponentDiagrams().getItem(0);
//			System.out.println(diag);
//			IRPCollection diagrammes = diag.getGraphicalElements();
//			IRPDiagram mainElement = diag.getMainDiagram();
//			Test d'enregistrement en une image (format EMF)
//			diag.getPicture("image1");
//			Test d'enregistrement en une image (format JPG)
//			diag.getPictureAs("testJPG", "JPG", 0, diagrammes);
//			RhapsodyAppServer.CloseSession();
//		}
	}
	
	public void onDispose(){
		this.imagesConstructs = true;
		update();
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

}
