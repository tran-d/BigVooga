package authoring_UI;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class ActionConditionVBox extends VBox implements ActionConditionVBoxI{
	
	private static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";
	
	private ResourceBundle actionConditionVBoxResources;
	private List<ToolBar> rows;
	private Boolean myIsConditionVBox;
	
	public ActionConditionVBox(Boolean isConditionVBox) {
		super();
		myIsConditionVBox = isConditionVBox;
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
	}

	@Override
	public void addActionCondition(String label) {
		Label type = new Label(label);
		ChoiceBox<String> implementationSelector = new ChoiceBox<String>(ActionTabUtil.convertToObservableList(
				actionConditionVBoxResources.getString(label + actionConditionVBoxResources.getString("OptionsTag"))));
		VBox implementationSelectorVBox = ActionTabUtil.addVBoxwithLabel(implementationSelectorHeader(), implementationSelector);
		ToolBar toolBar = new ToolBar(type,implementationSelectorVBox);
		rows.add(toolBar);
		getChildren().add(toolBar);
	}

	@Override
	public void removeActionCondition(int row) {
		getChildren().remove(rows.get(row));
		rows.remove(row);
	}
	
	private String implementationSelectorHeader() {
		if(myIsConditionVBox) return actionConditionVBoxResources.getString("ConditionSelectorLabel");
		else return actionConditionVBoxResources.getString("ActionSelectorLabel");
	}

}
