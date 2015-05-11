package modele;

import java.util.ArrayList;

import donnees.Diagramme;
import donnees.DiagrammeObjets;
import donnees.Projet;

public class Comparateur {

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
	private int[] correspondance;

	public Comparateur(Projet p1, Projet p2){
		this.premier = p1;
		this.deuxieme = p2;
		init();
	}

	private void init(){
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
	}

	/**
	 * faire la correspondance entre les différents diagrammes afin de comparer les bons entre eux.
	 * A la fin de cette étape il faut demander confirmation à l'utilisateur
	 * 
	 * On commenceà essayer avec les noms, puis le nombre d'objets avant de demander 
	 * quel diagramme corresond auquel à l'utilisateur
	 * 
	 * @param list1 : la 1ère liste de diagrammes
	 * @param list2 : la 2ème liste de diagrammes
	 */
	private void doCorrespondance(ArrayList<Diagramme> list1, ArrayList<Diagramme> list2){
		boolean find = true;
		//Avec les noms 
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).getNomDiagramme().equals(list2.get(j).getNomDiagramme())) correspondance[i] = j;
			}
		}		
		find = correspondanceOK(list1, list2);

		//Si noms ont changés on passe aux types
		if (!find) {
			for (int i = 0; i < list1.size(); i++) {
				if (correspondance[i] != -1) {
					for (int j = 0; j < list2.size(); j++) {
						if (list1.get(i).getObjets().size() == list2.get(j).getObjets().size())
							correspondance[i] = j;

					}
				}
			}
		}
		
		find = correspondanceOK(list1, list2);
		if (!find)
			System.out.println("correspondance des diagrammes impossible");
		else
			System.out.println("correspondance diagrammes ok !");
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

	public Projet getPremier() {
		return premier;
	}

	public void setPremier(Projet premier) {
		this.premier = premier;
	}

	public Projet getDeuxieme() {
		return deuxieme;
	}

	public void setDeuxieme(Projet deuxieme) {
		this.deuxieme = deuxieme;
	}
}
