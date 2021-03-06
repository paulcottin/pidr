package rhapsodyVisualisation;

import interfaces.LongTask;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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

public class Visualiser extends Observable implements Runnable, LongTask{

	private Comparateur c;
	private String projectPath, projectName, realProjectPath;
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
		System.out.println("visualiser - Run");
		app = RhapsodyAppServer.getActiveRhapsodyApplication();
		System.out.println("app ok");
		writeProjectWithDiff();
		System.out.println("project path : "+projectPath);
		project = (IRPProject) app.openProject(projectPath);
		System.out.println(project.getDisplayName());
		ArrayList<IRPDiagram> diagrammes = new ArrayList<IRPDiagram>();
		String tmpPath = new File("tmp").getAbsolutePath().replace("\\", "\\\\")+"\\";
		System.out.println("size diags : "+c.getDeuxieme().getDiagrammes().size());
		for (Diagramme diag : c.getDeuxieme().getDiagrammes()) {
			diagrammes.add((IRPDiagram) project.findElementByGUID(diag.getId()));
		}
		for (int j = 0; j < diagrammes.size(); j++) {
			diagrammes.get(j).getPictureAs(tmpPath+"image"+j+".jpg", "JPG", 0, null);
		}
		for (int i = 0; i < diagrammes.size(); i++) {
			try {
				images.add(ImageIO.read(new File(tmpPath+"image"+i+".jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		replaceFiles();
		running = false;
		update();
	}
	
	public void onDispose(){
		this.imagesConstructs = true;
		update();
	}
	
	/**
	 * But de la fonction : remplacer le fichier .rpy initial par celui généré par le programme
	 * 						pour que s'affiche en couleur les modifications lorsque l'on appelera 
	 * 						la fonction openProject();
	 * Pour cela : 
	 * 1. on deplace le fichier de projet.rpy dans le repertoire tmp/
	 * 2. on ecrit le fichier généré par nous dans le repertoire du projet initial
	 * 3. on génère les graphs avec la fonction habituelle
	 * 4. on supprime le fichier généré par nous
	 * 5. on redéplace le fichier.rpy de tmp/ dans son répertoire
	 * 
	 */
	private void writeProjectWithDiff(){
		//1. sauvegarde de l'ancien path
		realProjectPath = projectPath;
		//1. on deplace les fichiers dans tmp/
		String[] pathTab = projectPath.split("\\\\");
		String nomFichier = pathTab[pathTab.length-1];
		Path pathProject = FileSystems.getDefault().getPath(realProjectPath);
		Path pathTmp = FileSystems.getDefault().getPath("tmp", nomFichier);
		Path pathDefault = FileSystems.getDefault().getPath(realProjectPath.substring(0, realProjectPath.indexOf("."))+"_rpy\\Default.sbs");
		Path pathTmpDefault = FileSystems.getDefault().getPath("tmp", "Default.sbs");
		try {
			Files.move(pathProject, pathTmp, StandardCopyOption.REPLACE_EXISTING);
			Files.move(pathDefault, pathTmpDefault, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Ecriture des fichiers à partir de l'arbre
		Writer writer = new Writer(c.getDeuxieme().getProjet().getNoeud(), realProjectPath);
		writer.setInitLigne(c.getInitLigne());
		writer.write();
		Writer writerSBS = new Writer(c.getDeuxieme().getProjetBDD().getNoeud(), realProjectPath.substring(0, realProjectPath.indexOf("."))+"_rpy\\Default.sbs");
		writerSBS.setInitLigne(c.getInitLigne());
		writerSBS.write();
		//
		Writer writer2 = new Writer(c.getDeuxieme().getProjet().getNoeud(), "retour.rpy");
		writer2.setInitLigne(c.getInitLigne());
		writer2.write();
		Writer writer3 = new Writer(c.getDeuxieme().getProjetBDD().getNoeud(), "retourBDD.rpy");
		writer3.setInitLigne(c.getInitLigne());
		writer3.write();
		projectPath = realProjectPath;
	}
	
	private void replaceFiles(){
		(new File(realProjectPath)).delete();
		String[] pathTab = projectPath.split("\\\\");
		String nomFichier = pathTab[pathTab.length-1];
		Path pathProject = FileSystems.getDefault().getPath(realProjectPath);
		Path pathTmp = FileSystems.getDefault().getPath("tmp", nomFichier);
		Path pathDefault = FileSystems.getDefault().getPath(realProjectPath.substring(0, realProjectPath.indexOf("."))+"_rpy\\Default.sbs");
		Path pathTmpDefault = FileSystems.getDefault().getPath("tmp", "Default.sbs");
		try {
			Files.move(pathTmp, pathProject, StandardCopyOption.REPLACE_EXISTING);
			Files.move(pathTmpDefault, pathDefault, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public String getRealProjectPath() {
		return realProjectPath;
	}

	public void setRealProjectPath(String realProjectPath) {
		this.realProjectPath = realProjectPath;
	}

}
