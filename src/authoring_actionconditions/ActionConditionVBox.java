package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class ActionConditionVBox extends VBox implements ActionConditionVBoxI{
	
	private static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";
	
	private ResourceBundle actionConditionVBoxResources;
	private String selectorLabel;
	private List<ToolBar> rows;
	
	public ActionConditionVBox(String selectorString) {
		super();
		selectorLabel = selectorString;
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
		rows = new LinkedList<ToolBar>();
	}

	@Override
	public void addActionCondition(String label) {
		Label type = new Label(label);
		ChoiceBox<String> implementationSelector = new ChoiceBox<String>(ActionConditionTabUtil.convertToObservableList(
				actionConditionVBoxResources.getString(label + actionConditionVBoxResources.getString("OptionsTag"))));
		VBox implementationSelectorVBox = ActionConditionTabUtil.addVBoxwithLabel(selectorLabel, implementationSelector);
		ToolBar toolBar = new ToolBar(type,implementationSelectorVBox);
		rows.add(toolBar);
		getChildren().add(toolBar);
	}

	@Override
	public void removeActionCondition(int row) {
		getChildren().remove(rows.get(row));
		rows.remove(row);
	}

}
