package donnees;

import parser.Noeud;

public abstract class Liaison extends DiagrammeObjets{

	private Texte lineName;
	private String sourceType, targetType;
	
	public Liaison(Noeud n) {
		super(n);
	}
	
	@Override
	protected void initClass(){
		lineName = new Texte(noeud.getChildByName("m_rpn"));
		sourceType = noeud.getChildByName("m_sourceType").getStringValue();
		targetType = noeud.getChildByName("m_targetType").getStringValue();
		initLiaisonClass();
	}
	
	protected abstract void initLiaisonClass();
	
	@Override
	protected boolean egalObjet(DiagrammeObjets objet) {
		boolean b = true;
		if (objet instanceof Liaison){
			Liaison o = (Liaison) objet;
			if (!lineName.equals(o.getLineName())){
				modif.add("nom : "+lineName.getText());
				b =  lineName.equals(objet) == true ? b : false;
			}
			if (!sourceType.equals(o.getSourceType())){
				modif.add("Liaison - sourceType : "+sourceType);
				b = false;
			}
			if (!targetType.equals(o.getTargetType())) {
				modif.add("Liaison - targetType : "+targetType);
				b = false;
			}
			
			b =  egalObjetLiaison(o) == true ? b : false;
			return b;
		}
		else
			return false;
	}
	
	protected abstract boolean egalObjetLiaison(Liaison l);

	@Override
	protected void write() {
		lineName.write();
		noeud.getChildByName("m_sourceType").setStringValue(sourceType);
		noeud.getChildByName("m_targetType").setStringValue(targetType);
		writeLiaison();
	}
	
	protected abstract void writeLiaison();

	public Texte getLineName() {
		return lineName;
	}

	public void setLineName(Texte lineName) {
		this.lineName = lineName;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

}
