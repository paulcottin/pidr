package rhapsodyVisualisation;

import com.telelogic.rhapsody.core.RPExtendedRPClassesFactory;

//extended class factory
public class ClassesFactory extends RPExtendedRPClassesFactory {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getExtendedClass(String RPClassName) {
		if(RPClassName.compareTo("RPProject") == 0){
			return RPProjectEX.class;
		}
		return null; //just extending RPProjet
	}

}