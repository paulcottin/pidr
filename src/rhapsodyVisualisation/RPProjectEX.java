package rhapsodyVisualisation;

import java.util.Iterator;

import com.telelogic.rhapsody.core.IRPPackage;
import com.telelogic.rhapsody.core.RPProject;

//extention of RPProject
public class RPProjectEX extends RPProject {

	public RPProjectEX(long nCOMInterfacesAddress) {
		super(nCOMInterfacesAddress);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("rawtypes")
	public void printMyPackages(){
		Iterator iter = getPackages().toList().iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if (obj instanceof IRPPackage) {
				IRPPackage pkg = (IRPPackage) obj;
				System.out.println(pkg.getName());				
			}
			
		}
		
	}
	

}
