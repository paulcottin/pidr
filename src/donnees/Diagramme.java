package donnees;

import java.util.ArrayList;

import parser.Noeud;
import rhapsodyClass.IAssociationEnd;
import rhapsodyClass.IClass;

public class Diagramme {

	private Noeud noeud;
	private String nomDiagramme, dateModification, typeDiagramme, id;
	private ArrayList<DiagrammeObjets> objets;

	public Diagramme(Noeud n){
		this.noeud = n;
		init();
	}

	private void init(){
		nomDiagramme = noeud.getChildByName("_name").getStringValue().split("\"")[1];
		dateModification = noeud.getChildByName("_lastModifiedTime").getStringValue().split("\"")[1];
		typeDiagramme = noeud.getChildByName("Stereotypes").getChildByName("value").getChilds().get(0).getChildByName("_name").getStringValue().split("\"")[1];
		id = noeud.getChildByName("_id").getStringValue();
		Noeud listDiag = noeud.getChildByName("_graphicChart").getChilds().get(noeud.getChildByName("_graphicChart").getChilds().indexOf(noeud.getChildByName("_graphicChart").getChildByName("elementList"))+1);
		objets = new ArrayList<DiagrammeObjets>();
		for (Noeud n : listDiag.getChilds()) {
			int type = n.getChildByName("m_type").getIntValue();
			if (type != 78) {
				switch (getClass(n)) {
				case "IClass":
					objets.add(new IClass(n));
					break;
				case "IAssociationEnd":
					objets.add(new IAssociationEnd(n));
					break;
				default:
					break;
				}
			}
		}
		System.out.println("Diagramme "+nomDiagramme+" ("+typeDiagramme+") constitué de "+objets.size()+" objets");
	}

	private String getClass(Noeud n){
		return n.getChildByName("m_pModelObject").getChildByName("_m2Class").getStringValue().equals("\"\"") ? "" : n.getChildByName("m_pModelObject").getChildByName("_m2Class").getStringValue().split("\"")[1];
	}

	public String getNomDiagramme() {
		return nomDiagramme;
	}

	public void setNomDiagramme(String nomDiagramme) {
		this.nomDiagramme = nomDiagramme;
	}

	public String getDateModification() {
		return dateModification;
	}

	public void setDateModification(String dateModification) {
		this.dateModification = dateModification;
	}

	public String getTypeDiagramme() {
		return typeDiagramme;
	}

	public void setTypeDiagramme(String typeDiagramme) {
		this.typeDiagramme = typeDiagramme;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<DiagrammeObjets> getObjets() {
		return objets;
	}

	public void setObjets(ArrayList<DiagrammeObjets> objets) {
		this.objets = objets;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}
}
