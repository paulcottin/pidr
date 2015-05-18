package modele;

import interfaces.LongTask;

import java.util.ArrayList;
import java.util.Observable;

import rhapsodyVisualisation.Visualiser;
import donnees.Diagramme;
import donnees.DiagrammeObjets;
import donnees.Projet;
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
	public static int ADD_R = 51;
	public static int ADD_G = 255;
	public static int ADD_B = 0;
	public static int MODIF_R = 255;
	public static int MODIF_G = 153;
	public static int MODIF_B = 0;

	private Projet premier, deuxieme;
	private String diffs;
	private int[] correspondance;
	private boolean running, comparaisonDone;
	private String path1, path2;
	private String initLigne;
	private Visualiser vis;

	public Comparateur(){
		init();
	}

	public Comparateur(Projet p1, Projet p2){
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
	}

	public void run(){
		this.running = true;
		diffs = "";
		int maxDiagrammeSize = premier.getDiagrammes().size() >= deuxieme.getDiagrammes().size() ? premier.getDiagrammes().size() : deuxieme.getDiagrammes().size();
		initCorrespondance(maxDiagrammeSize);
		ArrayList<Diagramme> list1 = (maxDiagrammeSize == premier.getDiagrammes().size() ? premier.getDiagrammes() : deuxieme.getDiagrammes());
		ArrayList<Diagramme> list2 = (maxDiagrammeSize != premier.getDiagrammes().size() ? premier.getDiagrammes() : deuxieme.getDiagrammes());
		//list1 est donc la liste la plus longue
		doCorrespondance(list1, list2);

		compare(list1, list2);
	}

	/**
	 * Lance la comparaison en appellant la méthode compareTo du type Diagramme
	 * @param list1 liste à comparer
	 * @param list2 avec elle
	 */
	private void compare(ArrayList<Diagramme> list1, ArrayList<Diagramme> list2){
		//on compare les diagrammes en commun
		for (int i = 0; i < list2.size(); i++) {
			list1.get(i).compareTo(list2.get(correspondance[i]));
		}
		//Si la list1 est la liste de départ et plus longue alors on met les restants dans un état supprimé
		if (list1.size() > list2.size() && premier.getDiagrammes().equals(list1)) {
			for (int i = list2.size(); i < list1.size(); i++)
				list1.get(i).setEtat(DiagrammeObjets.SUPPR);
		}
		//Si la list1 est la liste de départ et plus courte alors on met les restants dans un état ajouté
		else if (list1.size() > list2.size() && deuxieme.getDiagrammes().equals(list1)) {
			for (int i = list2.size(); i < list1.size(); i++)
				list1.get(i).setEtat(DiagrammeObjets.ADD);
		}
		else if (list1.size() == list2.size()){}
		//Ne rien faire
		else
			System.out.println("ERROR (Comparateur - compare())");
		writeDiff();
		this.running = false;
		this.comparaisonDone = true;
		update();
	}

	/**
	 * faire la correspondance entre les différents diagrammes afin de comparer les bons entre eux.
	 * 
	 * On prodece avec les ids des diagrammes
	 * 
	 * @param list1 : la 1ère liste de diagrammes
	 * @param list2 : la 2ème liste de diagrammes
	 */
	private void doCorrespondance(ArrayList<Diagramme> list1, ArrayList<Diagramme> list2){
		//Correspondance des projets
		if (!premier.getId().equals(deuxieme.getId())) new BadProjectCorrespondance();
		else{
			boolean find = true;
			//Avec les IDs
			for (int i = 0; i < list1.size(); i++) 
				for (int j = 0; j < list2.size(); j++)
					if (list1.get(i).getId().equals(list2.get(j).getId())) correspondance[i] = j;

			find = correspondanceOK(list1, list2);
			if (!find)
				new BadDiagramCorrespondance();
			else
				System.out.println("correspondance diagrammes ok !");
		}
	}

	/**
	 * Initialisation du tableau de correspondance
	 * @param size : taille du tableau
	 */
	private void initCorrespondance(int size){
		correspondance = new int[size];
		for (int i = 0; i < correspondance.length; i++)
			correspondance[i] = -1;
	}

	/**
	 * Check si la correspondance est finie ou pas
	 * @param list1 les deux listes 
	 * @param list2 à comparer
	 * @return
	 */
	private boolean correspondanceOK(ArrayList<Diagramme> list1, ArrayList<Diagramme> list2){
		boolean find = true;
		int cpt = 0;
		for (Integer i : correspondance) {
			if (list1.size() == list2.size())
				if (i == -1) find = false; else;
			else 
				if (i == -1) cpt++;			
		}
		if (list1.size() < list2.size()) 
			if (list1.size() != cpt) find = false; else find = true;
		else if (list2.size() < list1.size())
			if (list2.size() != cpt) find = false; else find = true;
		return find;
	}

	private void writeDiff(){
		for (Diagramme d : this.premier.getDiagrammes()) {
			for (String s : d.getDiffString()) {
				diffs += s+"\n";
			}
		}
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

	public Projet getPremier() {
		return premier;
	}

	public void setPremier(Projet premier) {
		this.premier = premier;
		update();
	}

	public Projet getDeuxieme() {
		return deuxieme;
	}

	public void setDeuxieme(Projet deuxieme) {
		this.deuxieme = deuxieme;
		update();
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
}
