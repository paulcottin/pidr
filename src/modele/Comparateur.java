package modele;

import parser.Noeud;

public class Comparateur {
	
	private Noeud premier, deuxieme;
	
	public Comparateur() {
		init();
	}
	
	public Comparateur(Noeud n1, Noeud n2){
		init();
		this.premier = n1;
		this.deuxieme = n2;
	}
	
	public void init(){
		premier = new Noeud();
		deuxieme = new Noeud();
	}

	public Noeud getPremier() {
		return premier;
	}

	public void setPremier(Noeud premier) {
		this.premier = premier;
	}

	public Noeud getDeuxieme() {
		return deuxieme;
	}

	public void setDeuxieme(Noeud deuxieme) {
		this.deuxieme = deuxieme;
	}

}
