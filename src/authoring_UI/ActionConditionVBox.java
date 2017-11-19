package authoring_UI;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class ActionConditionVBox extends VBox implements ActionConditionVBoxI{
	
	private static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";
	
	private ResourceBundle actionConditionVBoxResources;
	private List<ToolBar> rows;
	
	public ActionConditionVBox() {
		super();
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
	}

	@Override
	public void addActionCondition(String label) {
		Label type = new Label(label);
	}

}
