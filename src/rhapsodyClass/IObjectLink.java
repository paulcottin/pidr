package rhapsodyClass;

import parser.Noeud;
import donnees.Liaison;
import donnees.Texte;

public class IObjectLink extends Liaison {
	
	private Texte sourceMultiplicity, targetMultiplicity;

	public IObjectLink(Noeud n) {
		super(n);
	}

	@Override
	protected void initLiaisonClass() {
		sourceMultiplicity = new Texte(noeud.getChildByName("m_sourceMultiplicity"));
		targetMultiplicity = new Texte(noeud.getChildByName("m_targetMultiplicity"));
	}

	@Override
	protected boolean egalObjetLiaison(Liaison l) {
		boolean b = true;
		if (l instanceof IObjectLink) {
			IObjectLink o = (IObjectLink) l;
			if (!sourceMultiplicity.equals(o.getSourceMultiplicity())) {
				modif.add("Connector : Multiplicité de la source");
				b = false;
			}
			if (!targetMultiplicity.equals(o.getTargetMultiplicity())) {
				modif.add("Connector : Multiplicité de la cible");
				b = false;
			}
			return b;
		}else
			return false;
	}

	@Override
	protected void writeLiaison() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getColorType(){
		return "connector";
	}

	public Texte getSourceMultiplicity() {
		return sourceMultiplicity;
	}

	public void setSourceMultiplicity(Texte sourceMultiplicity) {
		this.sourceMultiplicity = sourceMultiplicity;
	}

	public Texte getTargetMultiplicity() {
		return targetMultiplicity;
	}

	public void setTargetMultiplicity(Texte targetMultiplicity) {
		this.targetMultiplicity = targetMultiplicity;
	}
}
