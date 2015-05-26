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
			//Si il n'y a pas le mm nombre d'attribut c'est qu'il y en a qui ont été créés ou supprimés
			if (attributs.size() != o.getAttributs().size()) {
				//Si il y en a plus dans la version de depart
				if (attributs.size() > o.getAttributs().size()) {
					//On sauvegarde ceux qui sont en commun
					ArrayList<Handle> commun = new ArrayList<Handle>();
					for (int i = 0; i < attributs.size(); i++) 
						if (o.getAttributs().contains(attributs.get(i))) 
							commun.add(attributs.get(i));
					//Les attributs qu'il y a dans la version de depart et pas celle d'arrivee
					for (int i = 0; i < attributs.size(); i++) 
						if (!commun.contains(attributs.get(i))) modif.add("Block \""+name+"\" - attribut \""+attributs.get(i).getNom()+"\" supprimé");
					//Les attributs qui sont dans la version d'arrivée et pas dans celle de départ
					for (int i = 0; i < o.getAttributs().size(); i++) {
						if (!commun.contains(o.getAttributs().get(i))) modif.add("Block \""+name+"\" - attribut \""+o.getAttributs().get(i).getNom()+"\" ajouté");
					}
				}
				//Si il y en a plus dans la version modifiée
				else {
					
				}
				b = false;
			}
			boolean test = false;
			for (Handle t : attributs) {
				test = false;
				for (Handle t1 : o.getAttributs()) {
					if (t.egal(t1)) {
						test = true;
					}
				}
				if (!test) {
					modif.add("attributs : "+t.toString());
//					this.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
					setEtat(MODIF);
					b = false; 
				}
			}
			for (Handle t : operations) {
				test = false;
				for (Handle t1 : o.getOperations()) {
					if (t.egal(t1)) {
						test = true;
					}
				}
				if (!test) {
					System.out.println("ope modif");
					modif.add("operations : "+t.toString());
//					this.setRGB(Comparateur.MODIF_R, Comparateur.MODIF_G, Comparateur.MODIF_B);
					setEtat(MODIF);
					b = false; 
				}
			}
			return b;
		}
		else
			return false;
	}
	
	@Override
	protected String getColorType(){
		return "Block";
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
