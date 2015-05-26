package rhapsodyClass;

import parser.Noeud;
import donnees.DiagrammeObjets;

public class IPort extends DiagrammeObjets{
	
	public IPort(Noeud n) {
		super(n);
	}
	
	@Override
	protected void initClass() {
		
	}

	@Override
	protected boolean egalObjet(DiagrammeObjets o) {
		// Il n'y a que le nom qui est important, si il est modifié la classe 
		//abstraite le notifiera
		return true;
	}
	
	@Override
	protected void write() {
		
	}
	
	@Override
	protected String getColorType(){
		return "Object";
	}
}
