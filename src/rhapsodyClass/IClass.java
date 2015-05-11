package rhapsodyClass;

import java.util.ArrayList;

import parser.Noeud;
import donnees.DiagrammeObjets;

public class IClass extends DiagrammeObjets {

	private ArrayList<String> attributs, operations;
	
	public IClass(Noeud n) {
		super(n);
	}
	
	@Override
	public void initClass(){
		attributs = new ArrayList<String>();
		operations = new ArrayList<String>();
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
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean egal(DiagrammeObjets objet) {
		// TODO Auto-generated method stub
		return false;
	}

}
