package modele;

import interfaces.LongTask;

import java.util.ArrayList;
import java.util.Observable;

import rhapsodyVisualisation.Visualiser;
import donnees.Diagramme;
import donnees.DiagrammeObjets;
import donnees.Projet;
import donnees.ProjetGlobal;
import exceptions.BadDiagramCorrespondance;
import exceptions.BadProjectCorrespondance;

/**
 * Gere la comparaison de deux projets
 * Recupere les differences dans diffs
 * Conserve les paths des fichiers compares, la premiere ligne du fichier d'origine
 * @author paul
 *
 */
public class Comparateur extends Observable implements Runnable, LongTask{

	public static int SUPPR_R = 255;
	public static int SUPPR_G = 0;
	public static int SUPPR_B = 0;
	public static int ADD_R = 0;
	public static int ADD_G = 255;
	public static int ADD_B = 0;
	public static int MODIF_R = 0;
	public static int MODIF_G = 0;
	public static int MODIF_B = 255;

	private ProjetGlobal premier, deuxieme;
	private String diffs;
	private boolean running, comparaisonDone;
	private String path1, path2;
	private String initLigne;
	private Visualiser vis;
	private int nbDiff;

	public Comparateur(){
		init();
	}

	public Comparateur(ProjetGlobal p1, ProjetGlobal p2){
		init();
		this.premier = p1;
		this.deuxieme = p2;
		run();
	}

	private void init(){
		this.premier = null;
		this.deuxieme = null;
		this.running = false;
		this.path1 = null;
		this.path2 = null;
		this.diffs = "";
		this.comparaisonDone = false;
		this.initLigne = "";
		this.vis = new Visualiser(this);
		this.vis.initApplication();
		this.nbDiff = 0;
	}

	public void run(){
		this.running = true;
		diffs = "";
		deuxieme.compare(premier);
		diffs = deuxieme.getDiffs();
		nbDiff = deuxieme.getNbDiffs();
		this.running = false;
		this.comparaisonDone = true;
		update();
	}
	
	public void constructImage(){
		Thread t = new Thread(vis);
		t.start();
	}
	
	public void onDispose(){
		//Ne rien faire
	}

	private void update(){
		setChanged();
		notifyObservers();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getDiffs() {
		return diffs;
	}

	public void setDiffs(String diffs) {
		this.diffs = diffs;
	}

	public String getPath1() {
		return path1;
	}
	
	public String getName1(){
		if (path1.contains("\\\\")) 
			return path1.split("\\\\")[path1.split("\\\\").length-1];
		else if (path1.contains("/"))
			return path1.split("/")[path1.split("/").length-1];
		else
			return null;
	}

	public void setPath1(String path1) {
		this.path1 = path1.replace("\\", "\\\\");
		this.comparaisonDone = false;
		this.premier = new ProjetGlobal(this.path1);
		this.initLigne = premier.getInitLigne();
		System.out.println("init ligne : "+initLigne);
		update();
	}

	public String getPath2() {
		return path2;
	}
	
	public String getName2(){
		if (path2.contains("\\")) 
			return path2.split("\\\\")[path2.split("\\\\").length-1];
		else if (path2.contains("/"))
			return path2.split("/")[path2.split("/").length-1];
		else
			return null;
	}

	public void setPath2(String path2) {
		this.path2 = path2.replace("\\", "\\\\");
		this.comparaisonDone = false;
		this.deuxieme = new ProjetGlobal(this.path2);
		update();
	}

	public boolean isComparaisonDone() {
		return comparaisonDone;
	}

	public void setComparaisonDone(boolean comparaisonDone) {
		this.comparaisonDone = comparaisonDone;
	}

	public String getInitLigne() {
		return initLigne;
	}

	public void setInitLigne(String initLigne) {
		this.initLigne = initLigne;
	}

	public Visualiser getVis() {
		return vis;
	}

	public void setVis(Visualiser vis) {
		this.vis = vis;
	}

	public int getNbDiff() {
		return nbDiff;
	}

	public void setNbDiff(int nbDiff) {
		this.nbDiff = nbDiff;
	}

	public ProjetGlobal getPremier() {
		return premier;
	}

	public void setPremier(ProjetGlobal premier) {
		this.premier = premier;
		update();
	}

	public ProjetGlobal getDeuxieme() {
		return deuxieme;
	}

	public void setDeuxieme(ProjetGlobal deuxieme) {
		this.deuxieme = deuxieme;
		update();
	}
}
