package rhapsodyClass;

import java.util.ArrayList;

import modele.Comparateur;
import parser.Noeud;
import donnees.DiagrammeObjets;
import donnees.Handle;

public class IClass extends DiagrammeObjets {

	private ArrayList<Handle> attributs, operations;

	public IClass(Noeud n) {
		super(n);
	}

	@Override
	public void initClass(){
		attributs = new ArrayList<Handle>();
		operations = new ArrayList<Handle>();
		Noeud compNoeud = noeud.getChildByName("Compartments");
		int sizeComp = compNoeud.getChildByName("size").getIntValue();
		for (int i = 0; i < sizeComp; i++) {
			//Récupération des attributs
			if (getOutQuotes(compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Attribute")){
				Noeud attNoeud = compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("Items");
				int sizeAtt = attNoeud.getChildByName("size").getIntValue();
				for (int j = 0; j < sizeAtt; j++) {
					attributs.add(new Handle(attNoeud.getChilds().get(1).getChilds().get(j)));
				}
			}
			//Récupération des opérations
			else if (getOutQuotes(compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Operation")){
				Noeud attNoeud = compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("Items");
				int sizeAtt = attNoeud.getChildByName("size").getIntValue();
				for (int j = 0; j < sizeAtt; j++) {
					operations.add(new Handle(attNoeud.getChilds().get(1).getChilds().get(j)));
				}
			}
		}

		if (noeud.getChildByName("_properties") != null) {
			Noeud typeProperties =  noeud.getChildByName("_properties").getChildByName("Subjects").getChilds().get(1);
			int totalSize = noeud.getChildByName("_properties").getChildByName("Subjects").getChildByName("size").getIntValue();
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
					attributs.get(j).write();
				}
			}
			else if (getOutQuotes(compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Operation")){
				Noeud attNoeud = compNoeud.getChilds().get(1).getChilds().get(i).getChildByName("Items");
				attNoeud.getChildByName("size").setIntValue(operations.size());
				for (int j = 0; j < operations.size(); j++) {
					operations.get(j).write();
				}
			}
		}
		//La couleur est traitée dans la fonction setEtat(int e) de la classe abstraite
//		//Si la couleur a été modifiée
//		if (r != -1) {
//			Noeud typeProperties;
//			if (noeud.getChildByName("_properties") != null) 
//				typeProperties =  noeud.getChildByName("_properties").getChildByName("Subjects").getChildByName("value");
//			else{
//				typeProperties = constructProperties();
//				noeud.getChilds().add(1, typeProperties);
//			}
//
//			typeProperties = noeud.getChildByName("_properties").getChilds().get(1);
//			int totalSize = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("size").getIntValue();
//			//Ecriture de la couleur
//			String colors = constructRGB(r, g, b);
//			for (int i = 0; i < totalSize; i++) {
//				if (typeProperties.getChilds().get(i).getChildByName("_Name").getStringValue().equals("\"Format\"")) {
//					Noeud properties = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChilds().get(0).getChildByName("Properties").getChildByName("value");
//					int size = typeProperties.getChilds().get(0).getChildByName("Metaclasses").getChildByName("value").getChildByName("Properties").getChildByName("size").getIntValue();
//					for (int j = 0; j < size; j++) {
//						String type = properties.getChilds().get(j).getChildByName("_Type").getStringValue();
//						if (type.equals("Color")) {
//							properties.getChilds().get(j).getChildByName("_Value").setStringValue(colors);
//						}
//					}
//				}
//			}
//		}
	}

	@Override
	protected boolean egalObjet(DiagrammeObjets objet) {
		boolean b = true;
		modif.clear();
		if (objet instanceof IClass) {
			IClass o = (IClass) objet;
			if (!name.getText().equals(o.getNameText())) {
				modif.add("nom : "+o.getNameText());
				name.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
				b = false;
			}
//			if (attributs.size() != o.getAttributs().size() || operations.size() != o.getOperations().size()) return false;
			boolean test = false;
			for (Handle t : attributs) {
				test = false;
				for (Handle t1 : o.getAttributs()) {
					if (t.equals(t1)) {
						test = false;
					}
				}
				if (!test) {
					modif.add("attributs : "+o.getAttributs().toString());
					this.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
					b = false; 
				}
			}
			for (Handle t : operations) {
				test = false;
				for (Handle t1 : o.getOperations()) {
					if (t.equals(t1)) {
						test = false;
					}
				}
				if (!test) {
					modif.add("operations : "+o.getOperations().toString());
					this.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
					b = false; 
				}
			}
			return b;
		}
		else
			return false;
	}

	public ArrayList<Handle> getAttributs() {
		return attributs;
	}

	public void setAttributs(ArrayList<Handle> attributs) {
		this.attributs = attributs;
	}

	public ArrayList<Handle> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<Handle> operations) {
		this.operations = operations;
	}

}
