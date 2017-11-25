package authoring_actionconditions;

import java.util.ResourceBundle;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class ActionConditionRow extends ToolBar {
	
	private static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";
	
	private ResourceBundle actionConditionVBoxResources;
	private int labelInt;
	private Label IDlabel;
	
	public ActionConditionRow(int ID,String label,String selectorLabel) {
		super();
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
		labelInt = ID;
		IDlabel = new Label(Integer.toString(ID));
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		Label type = new Label(label);
		ChoiceBox<String> implementationSelector = new ChoiceBox<String>(ActionConditionTabUtil.convertToObservableList(
				actionConditionVBoxResources.getString(label + actionConditionVBoxResources.getString("OptionsTag"))));
		VBox implementationSelectorVBox = ActionConditionTabUtil.addVBoxwithLabel(selectorLabel, implementationSelector);
		getItems().addAll(IDlabel,separator,type,implementationSelectorVBox);
	}
	
	protected void decreaseLabelID() {
		labelInt --;
		IDlabel.setText(Integer.toString(labelInt));
	}
	
}
