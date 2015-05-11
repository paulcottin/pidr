package donnees;

import parser.Noeud;

public class Texte {

	private Noeud noeud;
	private String text;
	private int r, g, b;
	
	public Texte(Noeud n) {
		this.noeud = n;
		init();
	}
	
	private void init(){
		text = noeud.getChildByName("m_str").getStringValue().equals("\"\"") ? "" : noeud.getChildByName("m_str").getStringValue().split("\"")[1];
		r = -1;
		g = -1;
		b = -1;
	}
	
	public void write(){
		noeud.getChildByName("m_str").setStringValue("\""+text+"\"");
//		if (r != -1) {
//			String color = "\""+r+","+g+","+b+"\"";
//		}
	}
	
	public boolean equals(Object o){
		if (o instanceof Texte) {
			if (text.equals(text))
				return true;
			else
				return false;
		}else
			return false;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
	public void setRGB(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}

}
