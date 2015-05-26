package rhapsodyClass;

import parser.Noeud;
import donnees.Liaison;

public class IDependency extends Liaison{

	public IDependency(Noeud n) {
		super(n);
	}

	@Override
	protected void initLiaisonClass() {
		
	}

	@Override
	protected boolean egalObjetLiaison(Liaison l) {
		return true;
	}

	@Override
	protected void writeLiaison() {
		
	}

	@Override
	protected String getColorType(){
		return lineName.getText().equals("") ? "Depends" : lineName.getText();
	}
	
}
