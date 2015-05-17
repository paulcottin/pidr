package rhapsodyVisualisation;

import modele.Comparateur;

import com.telelogic.rhapsody.core.IRPApplication;
import com.telelogic.rhapsody.core.IRPCollection;
import com.telelogic.rhapsody.core.IRPDiagram;
import com.telelogic.rhapsody.core.IRPGraphElement;
import com.telelogic.rhapsody.core.IRPProject;
import com.telelogic.rhapsody.core.RhapsodyAppServer;
import com.telelogic.rhapsody.core.RhapsodyRuntimeException;

public class Test {

	private Comparateur c;
	private String projectPath, projectName;
	private IRPApplication app;
	
	public Test(Comparateur c) {
		this.c = c;
		this.projectPath = "DishwasherDishwasher.rpy";
		this.projectName = "Dishwasher";
//		RhapsodyAppServer.setDefultClassFactory(new ClassesFactory());
		IRPApplication app = null;
		try {
			app = RhapsodyAppServer.getActiveRhapsodyApplication();
		} catch (RhapsodyRuntimeException e) {
			e.printStackTrace();
		}
//		if(app != null)	{
//			RPProjectEX prj = (RPProjectEX) app.activeProject();
//			prj.printMyPackages();
//		}
//		this.app.openProject(projectPath);
	}
	
	public void createProject(){
		app.createAndInsertProject(projectPath, projectName);
		IRPProject project = app.activeProject();
		IRPDiagram diag = (IRPDiagram) project.getComponentDiagrams().getItem(0);
		IRPCollection diagrammes = diag.getGraphicalElements();
		IRPDiagram mainElement = diag.getMainDiagram();
		//Test d'enregistrement en une image (format EMF)
		diag.getPicture("image1");
		//Test d'enregistrement en une image (format JPG)
		diag.getPictureAs("testJPG", "JPG", 0, diagrammes);
		
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
