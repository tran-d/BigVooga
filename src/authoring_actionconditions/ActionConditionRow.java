package authoring_actionconditions;

import java.util.ResourceBundle;

import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ChoiceBoxVBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

public class ActionConditionRow extends ToolBar {
	
	private static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";
	
	private ResourceBundle actionConditionVBoxResources;
	private int labelInt;
	private Label IDlabel;
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;
	
	public ActionConditionRow(int ID,String label,String selectorLabel,boolean isConditionRow) {
		super();
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
		labelInt = ID;
		IDlabel = new Label(Integer.toString(ID));
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		ObservableList<String> actionConditionOptions = ActionConditionTabUtil.convertToObservableList(actionConditionVBoxResources.getString(label 
				+ actionConditionVBoxResources.getString("OptionsTag"))); 
		ChoiceBoxVBox<String> implementationSelectorVBox = new ChoiceBoxVBox<String>(selectorLabel, actionConditionOptions);
		if(isConditionRow) addActionCheckBox();
		getItems().addAll(IDlabel,separator,new Label(label),implementationSelectorVBox);
	}
	
	protected void setNewActionCheckBoxVBoxOptions(ObservableList<Integer> newOptions) {
		if(!(actionCheckBoxVBox == null)) actionCheckBoxVBox.setNewOptions(newOptions);
	}
	
	protected void decreaseLabelID() {
		labelInt --;
		IDlabel.setText(Integer.toString(labelInt));
	}
	
	private void addActionCheckBox() {
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(actionConditionVBoxResources.getString("ActionCheckBoxLabel"),FXCollections.observableArrayList());
		getItems().add(actionCheckBoxVBox);
	}
	
}
