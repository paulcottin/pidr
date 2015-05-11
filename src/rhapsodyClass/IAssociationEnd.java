package rhapsodyClass;

import parser.Noeud;
import donnees.DiagrammeObjets;
import donnees.Texte;

public class IAssociationEnd extends DiagrammeObjets{
	
	private String debut, fin, debFleche, finFleche;
	private Texte debMultiplicity, finMultiplicity;
	private int typeFleche;

	public IAssociationEnd(Noeud n) {
		super(n);
	}
	
	public void initClass(){
		Noeud typeProperties =  noeud.getChildByName("_properties").getChildByName("value");
		int totalSize = noeud.getChildByName("_properties").getChildByName("value").getChilds().get(0).getChildByName("Metaclasses").getChildByName("size").getIntValue();
		String colors = "";
		for (int i = 0; i < totalSize; i++) {
			if (typeProperties.getChilds().get(i).getChildByName("_Name").getStringValue().equals("\"Format\"")) {
				Noeud properties = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChilds().get(0).getChildByName("Properties").getChildByName("value");
				int size = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChilds().get(0).getChildByName("Properties").getChildByName("size").getIntValue();
				for (int j = 0; j < size; j++) {
					String type = properties.getChilds().get(j).getChildByName("_Type").getStringValue();
					if (type.equals("Color")) {
						colors = getOutQuotes(properties.getChilds().get(j).getChildByName("_Value").getStringValue());
					}
				}
			}
		}

		if (!colors.equals("")) {
			String[] rgb = colors.split(",");
			super.r = Integer.parseInt(rgb[0]);
			super.g = Integer.parseInt(rgb[1]);
			super.b = Integer.parseInt(rgb[2]);
		}
		
		debut = getOutQuotes(noeud.getChildByName("m_pModelObject").getChildByName("_class").getStringValue());
		fin = getOutQuotes(noeud.getChildByName("m_pInverseModelObject").getChildByName("_class").getStringValue());
		debMultiplicity = new Texte(noeud.getChildByName("m_sourceMultiplicity"));
		finMultiplicity = new Texte(noeud.getChildByName("m_targetMultiplicity"));
		debFleche = getOutQuotes(noeud.getChildByName("m_sourceType").getStringValue());
		finFleche = getOutQuotes(noeud.getChildByName("m_targetType").getStringValue());
		typeFleche = noeud.getChildByName("m_line_style").getIntValue();
	}
	
	@Override
	protected void write() {
//		writeGeneral();
		Noeud typeProperties =  noeud.getChildByName("_properties").getChildByName("value");
		int totalSize = noeud.getChildByName("_properties").getChildByName("value").getChilds().get(0).getChildByName("Metaclasses").getChildByName("size").getIntValue();
		String colors = addQuotes(r+","+g+","+b);
		for (int i = 0; i < totalSize; i++) {
			if (typeProperties.getChilds().get(i).getChildByName("_Name").getStringValue().equals("\"Format\"")) {
				Noeud properties = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChilds().get(0).getChildByName("Properties").getChildByName("value");
				int size = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChilds().get(0).getChildByName("Properties").getChildByName("size").getIntValue();
				for (int j = 0; j < size; j++) {
					String type = properties.getChilds().get(j).getChildByName("_Type").getStringValue();
					if (type.equals("Color")) {
						properties.getChilds().get(j).getChildByName("_Value").setStringValue(colors);
					}
				}
			}
		}
		noeud.getChildByName("m_pModelObject").getChildByName("_class").setStringValue(addQuotes(debut));
		noeud.getChildByName("m_pInverseModelObject").getChildByName("_class").setStringValue(addQuotes(fin));
		debMultiplicity.write();
		finMultiplicity.write();
		noeud.getChildByName("m_sourceType").setStringValue(addQuotes(debFleche));
		noeud.getChildByName("m_targetType").setStringValue(addQuotes(finFleche));
		noeud.getChildByName("m_line_style").setIntValue(typeFleche);
	}
	
	@Override
	protected boolean egal(DiagrammeObjets o) {
		if (o instanceof IAssociationEnd) {
			IAssociationEnd ob = (IAssociationEnd) o;
			if (debut.equals(ob.getDebut()) && fin.equals(ob.getFin()) && debMultiplicity.equals(ob.getDebMultiplicity()) && finMultiplicity.equals(ob.getFinMultiplicity()) && debFleche.equals(ob.getDebFleche()) && finFleche.equals(ob.getFinFleche()) && typeFleche==ob.getTypeFleche())
				return true;
			else
				return false;
		}
		else 
			return false;
	}

	public String getDebut() {
		return debut;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public Texte getDebMultiplicity() {
		return debMultiplicity;
	}

	public void setDebMultiplicity(Texte debMultiplicity) {
		this.debMultiplicity = debMultiplicity;
	}

	public Texte getFinMultiplicity() {
		return finMultiplicity;
	}

	public void setFinMultiplicity(Texte finMultiplicity) {
		this.finMultiplicity = finMultiplicity;
	}

	public String getDebFleche() {
		return debFleche;
	}

	public void setDebFleche(String debFleche) {
		this.debFleche = debFleche;
	}

	public String getFinFleche() {
		return finFleche;
	}

	public void setFinFleche(String finFleche) {
		this.finFleche = finFleche;
	}

	public int getTypeFleche() {
		return typeFleche;
	}

	public void setTypeFleche(int typeFleche) {
		this.typeFleche = typeFleche;
	}
}
