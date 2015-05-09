package donnees;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import parser.Noeud;

public class Creator {

	private Noeud noeud;
	private Projet projet;
	private ArrayList<Projet> list;
	private int nbProjet, nbDiagramm;
	
	public Creator(Noeud n) {
		this.noeud = n;
		this.projet = null;
		this.list = new ArrayList<Projet>();
		nbProjet = 0;
		nbDiagramm = 0;
	}
	
	public void createProjet(){
		this.projet = new Projet();
		createProjetHelper(noeud);
		
	}
	
	private void createProjetHelper(Noeud n){		
		if (n.getClasse().equals("IProject")) {
			System.out.println("IProject");
			list.add(new Projet(n));
			nbProjet++;
		}
		else if (n.getClasse().contains("IDiagram")) {
			System.out.println("trouvé");
			list.get(nbProjet).getDiagrammes().add(createDiagramme(n));
		}
//		System.out.println(n.getClasse());
		
		if (n.getChilds() != null) {
			for (int i = 0; i < n.getChilds().size(); i++) {
				System.out.println(n.getChilds().get(i).getClasse());
				createProjetHelper(n.getChilds().get(i));
			}
		}
		if(n.getProprietes() != null){
			for (int i = 0; i < n.getProprietes().size(); i++) {
				if (n.getProprietes().get(i).getNoeuds() != null) {
					for (int j = 0; j < n.getProprietes().get(i).getNoeuds().size(); j++) {
						createProjetHelper(n.getProprietes().get(i).getNoeuds().get(j));
					}
				}
			}
		}
	}
	
	private Diagramme createDiagramme(Noeud n){
		System.out.println("Création de diagramme");
		return new Diagramme();
	}
	
	private void createDiagrammeObject(){
		
	}
}
