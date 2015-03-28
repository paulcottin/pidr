package modele;

import parser.Noeud;
import parser.Parser;

public class Main {
	
	public static void main(String[] args){
		Parser p = new Parser("test.rpy");
		Noeud n = p.parse();
		System.out.println(n.getChildCount());
	}

}
