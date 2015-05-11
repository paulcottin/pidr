package rhapsodyClass;

import java.util.ArrayList;

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
			if (getOutQuotes(compNoeud.getChildByName("value").getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Attribute")){
				Noeud attNoeud = compNoeud.getChildByName("value").getChilds().get(i).getChildByName("Items");
				int sizeAtt = attNoeud.getChildByName("size").getIntValue();
				for (int j = 0; j < sizeAtt; j++) {
					System.out.println("ajout attribut "+i);
				}
			}
			else if (getOutQuotes(compNoeud.getChildByName("value").getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Operation")){
				Noeud attNoeud = compNoeud.getChildByName("value").getChilds().get(i).getChildByName("Items");
				int sizeAtt = attNoeud.getChildByName("size").getIntValue();
				for (int j = 0; j < sizeAtt; j++) {
					System.out.println("ajout operation "+i);
				}
			}
		}
		
		//TODO Comment récupérer la couleur du block ?
	}

	@Override
	protected void write() {
		writeGeneral();
		Noeud compNoeud = noeud.getChildByName("Compartments");
		int sizeComp = compNoeud.getChildByName("size").getIntValue();
		for (int i = 0; i < sizeComp; i++) {
			if (getOutQuotes(compNoeud.getChildByName("value").getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Attribute")){
				Noeud attNoeud = compNoeud.getChildByName("value").getChilds().get(i).getChildByName("Items");
				attNoeud.getChildByName("size").setIntValue(attributs.size());
				for (int j = 0; j < attributs.size(); j++) {
					System.out.println("écriture attribut "+i);
				}
			}
			else if (getOutQuotes(compNoeud.getChildByName("value").getChilds().get(i).getChildByName("m_name").getStringValue()).equals("Operation")){
				Noeud attNoeud = compNoeud.getChildByName("value").getChilds().get(i).getChildByName("Items");
				attNoeud.getChildByName("size").setIntValue(operations.size());
				for (int j = 0; j < operations.size(); j++) {
					System.out.println("écriture operation "+i);
				}
			}
		}
	}

	@Override
	protected boolean egal(DiagrammeObjets objet) {
		if (objet instanceof IClass) {
			IClass o = (IClass) objet;
			if (!name.getText().equals(o.getNameText())) return false;
			if (attributs.size() != o.getAttributs().size() || operations.size() != o.getOperations().size()) return false;
			boolean test = false;
			for (Texte t : attributs) {
				test = false;
				for (Texte t1 : o.getAttributs()) {
					if (t.equals(t1)) {
						test = false;
					}
				}
				if (!test) return false; 
			}
			for (Texte t : operations) {
				test = false;
				for (Texte t1 : o.getOperations()) {
					if (t.equals(t1)) {
						test = false;
					}
				}
				if (!test) return false; 
			}
			return true;
		}
		else
			return false;
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
