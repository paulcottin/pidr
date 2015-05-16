package rhapsodyClass;

import java.util.ArrayList;

import modele.Comparateur;

import parser.Noeud;
import donnees.DiagrammeObjets;
import donnees.Texte;

public class IClass extends DiagrammeObjets {

	private ArrayList<Texte> attributs, operations;

	public IClass(Noeud n) {
		super(n);
	}

	@Override
	public void initClass(){
		attributs = new ArrayList<Texte>();
		operations = new ArrayList<Texte>();
		Noeud compNoeud = noeud.getChildByName("Compartments");
		int sizeComp = compNoeud.getChildByName("size").getIntValue();
		for (int i = 0; i < sizeComp; i++) {
			//Récupération des attributs
			if (getOutQuotes(compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Attribute")){
				Noeud attNoeud = compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("Items");
				int sizeAtt = attNoeud.getChildByName("size").getIntValue();
				for (int j = 0; j < sizeAtt; j++) {
					System.out.println("ajout attribut "+i);
				}
			}
			//Récupération des opérations
			else if (getOutQuotes(compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Operation")){
				Noeud attNoeud = compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("Items");
				int sizeAtt = attNoeud.getChildByName("size").getIntValue();
				for (int j = 0; j < sizeAtt; j++) {
					System.out.println("ajout operation "+i);
				}
			}
		}

		if (noeud.getChildByName("_properties") != null) {
			Noeud typeProperties =  noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(1);
			int totalSize = noeud.getChildByName("_properties").getChildByName("Subjects").getChildByName("value").getChildByName("Metaclasses").getChildByName("size").getIntValue();
			//Récupération de la couleur du block
			String colors = "";
			for (int i = 0; i < totalSize; i++) {
				if (typeProperties.getChilds().get(i).getChildByName("_Name").getStringValue().equals("\"Format\"")) {
					Noeud properties = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChildByName("Properties").getChilds().get(1);
					int size = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChildByName("Properties").getChildByName("size").getIntValue();
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
		}
	}

	@Override
	protected void write() {
		//		writeGeneral();
		Noeud compNoeud = noeud.getChildByName("Compartments");
		int sizeComp = compNoeud.getChildByName("size").getIntValue();
		for (int i = 0; i < sizeComp; i++) {
			if (getOutQuotes(compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Attribute")){
				Noeud attNoeud = compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("Items");
				attNoeud.getChildByName("size").setIntValue(attributs.size());
				for (int j = 0; j < attributs.size(); j++) {
					System.out.println("écriture attribut "+i);
				}
			}
			else if (getOutQuotes(compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Operation")){
				Noeud attNoeud = compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("Items");
				attNoeud.getChildByName("size").setIntValue(operations.size());
				for (int j = 0; j < operations.size(); j++) {
					System.out.println("écriture operation "+i);
				}
			}
		}
		//Si la couleur a été modifiée
		if (r != -1) {
			Noeud typeProperties;
			if (noeud.getChildByName("_properties") != null) 
				typeProperties =  noeud.getChildByName("_properties").getChildByName("Subjects").getChildByName("value");
			else{
				typeProperties = constructProperties();
				noeud.getChilds().add(1, typeProperties);
			}

			typeProperties = noeud.getChildByName("_properties").getChilds().get(1);
			int totalSize = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("size").getIntValue();
			//Ecriture de la couleur
			String colors = constructRGB(r, g, b);
			for (int i = 0; i < totalSize; i++) {
				if (typeProperties.getChilds().get(i).getChildByName("_Name").getStringValue().equals("\"Format\"")) {
					Noeud properties = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChilds().get(0).getChildByName("Properties").getChildByName("value");
					int size = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChildByName("Properties").getChildByName("size").getIntValue();
					for (int j = 0; j < size; j++) {
						String type = properties.getChilds().get(j).getChildByName("_Type").getStringValue();
						if (type.equals("Color")) {
							properties.getChilds().get(j).getChildByName("_Value").setStringValue(colors);
						}
					}
				}
			}
		}
	}

	@Override
	protected boolean egal(DiagrammeObjets objet) {
		modif.clear();
		if (objet instanceof IClass) {
			IClass o = (IClass) objet;
			if (!name.getText().equals(o.getNameText())) {
				modif.add("nom : "+o.getNameText());
				name.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
				return false;
			}
//			if (attributs.size() != o.getAttributs().size() || operations.size() != o.getOperations().size()) return false;
			boolean test = false;
			for (Texte t : attributs) {
				test = false;
				for (Texte t1 : o.getAttributs()) {
					if (t.equals(t1)) {
						test = false;
					}
				}
				if (!test) {
					modif.add("attributs : "+o.getAttributs().toString());
					this.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
					return false; 
				}
			}
			for (Texte t : operations) {
				test = false;
				for (Texte t1 : o.getOperations()) {
					if (t.equals(t1)) {
						test = false;
					}
				}
				if (!test) {
					modif.add("operations : "+o.getOperations().toString());
					this.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
					return false; 
				}
			}
			return true;
		}
		else
			return false;
	}

	private Noeud constructProperties(){
		Noeud n = new Noeud("_properties");
		n.setClasse("IPropertyContainer ");
		Noeud subject = new Noeud("Subjects");
		subject.setClasse("IRPYRawContainer");
		subject.getChilds().add(new Noeud("size", 1, null, null));
		Noeud value = new Noeud("value", -8000, null, new ArrayList<Noeud>());
		Noeud propertySubject = new Noeud();
		propertySubject.setClasse("IPropertySubject");
		propertySubject.getChilds().add(new Noeud("_Name", -8000, "\"Format\"", null));
		Noeud metaclass = new Noeud("Metaclasses");
		metaclass.setClasse("IRPYRawContainer");
		metaclass.getChilds().add(new Noeud("size", 1, null, null));
		Noeud metaclassValue = new Noeud("value", -8000, null, new ArrayList<Noeud>());
		Noeud propertyMetaClass = new Noeud();
		propertyMetaClass.getChilds().add(new Noeud("_Name", -8000, "Block", null));
		Noeud rawContainer = new Noeud("Properties");
		rawContainer.setClasse("IRPYRawContainer");
		rawContainer.getChilds().add(new Noeud("size", 1, null, null));
		Noeud rawContainerValue = new Noeud("value", -8000, null, new ArrayList<Noeud>());
		Noeud iProperty = new Noeud();
		iProperty.setClasse("IProperty");
		iProperty.getChilds().add(new Noeud("_Name", -8000, "\"Line.LineColor\"", null));
		iProperty.getChilds().add(new Noeud("_Value", -8000, constructRGB(r, g, b), null));
		iProperty.getChilds().add(new Noeud("_Type", -8000, "Color", null));

		rawContainerValue.getChilds().add(iProperty);
		rawContainer.getChilds().add(rawContainerValue);
		propertyMetaClass.getChilds().add(rawContainer);
		metaclassValue.getChilds().add(propertyMetaClass);
		metaclass.getChilds().add(metaclassValue);
		propertySubject.getChilds().add(metaclass);
		value.getChilds().add(propertySubject);
		subject.getChilds().add(value);
		n.getChilds().add(subject);
		return n;
	}

	public ArrayList<Texte> getAttributs() {
		return attributs;
	}

	public void setAttributs(ArrayList<Texte> attributs) {
		this.attributs = attributs;
	}

	public ArrayList<Texte> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<Texte> operations) {
		this.operations = operations;
	}

}
