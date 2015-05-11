package donnees;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import parser.Noeud;

public class Projet {

	private String nomProjet, typeProjet;
	private Noeud noeud;
	private ArrayList<Diagramme> diagrammes;
	private GregorianCalendar lastModifiedDate;
	
	public Projet(Noeud n){
		this.noeud = n;
//		init();
	}
	
	private void init(){
		diagrammes = new ArrayList<Diagramme>();
		nomProjet = noeud.getChildByName("_name").getStringValue();
		String date = noeud.getChildByName("_modifiedTimeWeak").getStringValue();
		String[] tab = date.split("\\.");
		String[] hour = tab[tab.length-1].split(":");
		for (int i = 0; i < tab.length; i++) {
			System.out.println("date : "+tab[i]);
		}
		for (int i = 0; i < hour.length; i++) {
			System.out.println("hour : "+hour[i]);
		}
		lastModifiedDate = new GregorianCalendar(Integer.valueOf(tab[2].substring(0, 4)), Integer.valueOf(tab[1]), Integer.valueOf(tab[0]), Integer.valueOf(hour[2]), Integer.valueOf(hour[3]), Integer.valueOf(hour[4]));
		System.out.println("stereotype : "+noeud.getChildByName("Stereotypes").toString());
//		typeProjet = noeud.getChildByName("Stereotypes").getChildByName("value").getChildByName("_name").getStringValue();
//		System.out.println("new projet : "+nomProjet+"\n ("+lastModifiedDate.toString()+"), type : "+typeProjet);
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public ArrayList<Diagramme> getDiagrammes() {
		return diagrammes;
	}

	public void setDiagrammes(ArrayList<Diagramme> diagrammes) {
		this.diagrammes = diagrammes;
	}

	public GregorianCalendar getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(GregorianCalendar lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getTypeProjet() {
		return typeProjet;
	}

	public void setTypeProjet(String typeProjet) {
		this.typeProjet = typeProjet;
	}
}
