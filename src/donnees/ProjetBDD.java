package donnees;

import java.util.ArrayList;

import exceptions.BadProjectCorrespondance;
import parser.Noeud;

public class ProjetBDD {
	
	private String nomProjet, id;
	private Noeud noeud;
	private ArrayList<Diagramme> diagrammes;
	private int[] correspondance;
	private String diffs;
	private int nbDiffs;
	private boolean data;
	
	public ProjetBDD(Noeud n) {
		this.noeud = n;
		init();
	}
	
	private void init(){
		id = noeud.getChildByName("_id").getStringValue();
		nomProjet = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		diagrammes = new ArrayList<Diagramme>();
		data = noeud.getChildByName("Declaratives") != null ? true : false;
		if (data) {
			int nbDiagrammes = noeud.getChildByName("Declaratives").getChildByName("size").getIntValue();
			for (int i = 0; i < nbDiagrammes; i++) {
				if (noeud.getChildByName("Declaratives").getChild(1).getChild(i).getChildByName("_name").getStringValue().equals("\"BDD\"")) {
					diagrammes.add(new Diagramme(noeud.getChildByName("Declaratives").getChild(1).getChild(i)));
				}
			}
			nbDiffs = 0;
			initCorrespondance(diagrammes.size());
		}
	}
	
	public void compare(ProjetBDD p){
		//On etablit la correspondance entre les diagrammes
		doCorrespondance(p);
		//on compare les diagrammes en commun
		for (int i = 0; i < p.getDiagrammes().size(); i++) {
			diagrammes.get(i).compareTo(p.getDiagrammes().get(correspondance[i]));
		}
		//Si la list1 est la liste de départ et plus longue alors on met les restants dans un état supprimé
		if (diagrammes.size() > p.getDiagrammes().size()) {
			for (int i = p.getDiagrammes().size(); i < diagrammes.size(); i++)
				diagrammes.get(i).setEtat(DiagrammeObjets.SUPPR);
		}
		//Si la list1 est la liste de départ et plus courte alors on met les restants dans un état ajouté
		else if (diagrammes.size() > p.getDiagrammes().size()) {
			for (int i = p.getDiagrammes().size(); i < diagrammes.size(); i++)
				diagrammes.get(i).setEtat(DiagrammeObjets.ADD);
		}
		else if (diagrammes.size() == p.getDiagrammes().size()){}
		//Ne rien faire
		else
			System.out.println("ERROR (Comparateur - compare())");
		writeDiff();	
	}
	
	private void doCorrespondance(ProjetBDD p){
		//Correspondance des projets
		if (!id.equals(p.getId())) new BadProjectCorrespondance();
		else{
			//Avec les IDs
			for (int i = 0; i < diagrammes.size(); i++) 
				for (int j = 0; j < p.getDiagrammes().size(); j++)
					if (diagrammes.get(i).getId().equals(p.getDiagrammes().get(j).getId())) correspondance[i] = j;
		}

	}
	
	private void writeDiff(){
		for (Diagramme d : this.diagrammes) {
			for (String s : d.getDiffString()) {
				diffs += s+"\n";
				nbDiffs++;
			}
		}
	}
	
	private void initCorrespondance(int size){
		correspondance = new int[size];
		for (int i = 0; i < correspondance.length; i++)
			correspondance[i] = -1;
	}
	
	public String getNomProjet() {
		return nomProjet;
	}
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int[] getCorrespondance() {
		return correspondance;
	}
	public void setCorrespondance(int[] correspondance) {
		this.correspondance = correspondance;
	}
	public String getDiffs() {
		return diffs;
	}
	public void setDiffs(String diffs) {
		this.diffs = diffs;
	}
	public int getNbDiffs() {
		return nbDiffs;
	}
	public void setNbDiffs(int nbDiffs) {
		this.nbDiffs = nbDiffs;
	}

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

}
