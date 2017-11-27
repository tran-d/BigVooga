package authoring_actionconditions;

import java.util.List;
import java.util.ResourceBundle;

import ActionConditionClasses.ChoiceBoxVBox;
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
		List<String> actionConditionOptions = ActionConditionTabUtil.convertToList(actionConditionVBoxResources.getString(label 
				+ actionConditionVBoxResources.getString("OptionsTag"))); 
		VBox implementationSelectorVBox = new ChoiceBoxVBox<String>(selectorLabel, actionConditionOptions);
		getItems().addAll(IDlabel,separator,new Label(label),implementationSelectorVBox);
	}
	
	protected void decreaseLabelID() {
		labelInt --;
		IDlabel.setText(Integer.toString(labelInt));
	}
	
}
