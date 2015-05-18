package rhapsodyVisualisation;

import interfaces.LongTask;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import modele.Comparateur;

import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPProject;
import com.telelogic.rhapsody.core.RhapsodyAppServer;

public class Visualiser extends Observable implements Runnable, LongTask{

	private Comparateur c;
	private String projectPath, projectName;
	private IRPApplication app;
	private IRPProject project;
	private boolean running;
	private BufferedImage img;
	
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
		this.img = null;
	}
	
	public void run(){
//		try {
//			app = RhapsodyAppServer.createRhapsodyApplication();
//			System.out.println("app créée");
//		} catch (RhapsodyRuntimeException e) {
//			e.printStackTrace();
//		}
//		RhapsodyAppServer.setDefultClassFactory(new ClassesFactory());
//		app = RhapsodyAppServer.createRhapsodyApplication();
		app = RhapsodyAppServer.getActiveRhapsodyApplication();
		project = (IRPProject) app.openProject(projectPath);
		System.out.println(project.getDisplayName());
		System.out.println("panel widget : "+project.hasPanelWidget());
		System.out.println(project.findElementByGUID("GUID 158dc38e-b3c0-409f-b6b0-095b6bc1ef84").getDisplayName()); //block1
		System.out.println(project.findElementByGUID("GUID d935ccec-1611-40a6-bbd1-7056accc5c69").getDisplayName()); //Structure1 ?
		IRPDiagram diag = (IRPDiagram) project.findElementByGUID("GUID d935ccec-1611-40a6-bbd1-7056accc5c69");
//		diag.getPicture("C:\\Users\\paul\\Desktop\\image1.emf");
		diag.getPictureAs("C:\\Users\\paul\\Desktop\\image1.jpg", "JPG", 0, null);
		try {
			img = ImageIO.read(new File("C:\\Users\\paul\\Desktop\\ordres.jpg"));
			System.out.println("img read : ");
		} catch (IOException e) {
			e.printStackTrace();
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

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

}
