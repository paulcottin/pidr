package rhapsodyClass;

import parser.Noeud;
import donnees.Liaison;
import donnees.Texte;

public class IInformationFlow extends Liaison {

	private Texte keyword, conveyed;
	private String direction;
	
	public IInformationFlow(Noeud n) {
		super(n);
	}

	@Override
	protected void initLiaisonClass() {
		keyword = new Texte(noeud.getChildByName("m_keyword"));
		conveyed = new Texte(noeud.getChildByName("m_conveyed"));
		direction = noeud.getChildByName("m_direction").getStringValue();
	}

	@Override
	protected boolean egalObjetLiaison(Liaison l) {
		boolean b = true;
		if (l instanceof IInformationFlow) {
			IInformationFlow o = (IInformationFlow) l;
			if (!keyword.equals(o.getKeyword())){
				modif.add("Flow - keyword : "+keyword.getText());
				b = false;
			}
			if (!conveyed.equals(o.getConveyed())){
				modif.add("Flow - conveyed : "+conveyed.getText());
				b = false;
			}
			if (!direction.equals(o.getDirection())) {
				modif.add("Flow - direction : "+o.getDirection());
				b = false;
			}
			return b;
		}else
			return false;
	}

	@Override
	protected void writeLiaison() {
		keyword.write();
		conveyed.write();
		noeud.getChildByName("m_direction").setStringValue(direction);
	}

	public Texte getKeyword() {
		return keyword;
	}

	public void setKeyword(Texte keyword) {
		this.keyword = keyword;
	}

	public Texte getConveyed() {
		return conveyed;
	}

	public void setConveyed(Texte conveyed) {
		this.conveyed = conveyed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
