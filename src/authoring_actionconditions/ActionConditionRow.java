package authoring_actionconditions;

import java.util.List;
import java.util.ResourceBundle;

import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ActionCheckBoxVBoxI;
import ActionConditionClasses.ChoiceBoxVBox;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

/**
 * ActionConditionRow
 * purpose--in each action/condition tab, there is a list of actions or conditions that a user can add. These rows contain all of the information that 
 * the user can add for each action or condition.
assumptions--an actioncheckboxvbox is added to an action tab, which is shouldn't be. It should only be added to condition tab since conditions select 
which actions they cause
dependencies--depends on the choiceboxvbox class to instantiate itself and present choices to the user
an example of how to use it--pass in ID (row #), label (for the label of specific action or condition), selector Label (to further select the action or 
condition that the user wants), if it is a conditionRow, and the new potential list of actionOptions
any other details users should know--condition and action rows only differ between in that condition rows have an actioncheckboxvbox
 * @author Owen Smith
 *
 */

public class ActionConditionRow extends ToolBar implements ActionCheckBoxVBoxI,ActionConditionRowI {
	
	private static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";
	
	private ResourceBundle actionConditionVBoxResources;
	private int labelInt;
	private Label label;
	private Label IDlabel;
	private ChoiceBoxVBox<String> implementationSelectorVBox;
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;
	private ObservableList<Integer> newActionOptions;
	private boolean isConditionRow;
	
	public ActionConditionRow(int ID,String label,String selectorLabel,boolean isConditionRow, ObservableList<Integer> newActionOptions) {
		super();
		this.newActionOptions= newActionOptions;
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
		this.label = new Label(label);
		labelInt = ID;
		IDlabel = new Label(Integer.toString(ID));
		this.isConditionRow = isConditionRow;
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		ObservableList<String> actionConditionOptions = ActionConditionTabUtil.convertToObservableList(actionConditionVBoxResources.getString(label 
				+ actionConditionVBoxResources.getString("OptionsTag"))); 
		implementationSelectorVBox = new ChoiceBoxVBox<String>(selectorLabel, actionConditionOptions);
		getItems().addAll(IDlabel,separator,this.label,implementationSelectorVBox);
		if(isConditionRow) addActionCheckBox();
		else addBuildActionButton();
	}
	
	public ActionConditionRow(int ID,String label,String selectorLabel,boolean isConditionRow, String selectedConditionAction, 
			ObservableList<Integer> newActionOptions) {
		this(ID,label,selectorLabel,isConditionRow,newActionOptions);
		implementationSelectorVBox.setValue(selectedConditionAction);
	}
	
	public ActionConditionRow(int ID,String label,String selectorLabel,boolean isConditionRow, String selectedConditionAction, 
			ObservableList<Integer> newActionOptions,List<Integer> selectedActionOptions) {
		this(ID,label,selectorLabel,isConditionRow,selectedConditionAction,newActionOptions);
		actionCheckBoxVBox = new ActionCheckBoxVBox(newActionOptions,selectedActionOptions);
	}
	
	protected void setNewActionCheckBoxVBoxOptions(ObservableList<Integer> newOptions) {
		actionCheckBoxVBox.setNewOptions(newOptions);
	}
	
	protected void decreaseLabelID() {
		labelInt --;
		IDlabel.setText(Integer.toString(labelInt));
	}
	
	protected boolean isEqualTo(ActionConditionRow other) {
		if(!(isConditionRow == other.isConditionRow)) return false;
		else if(!getImplementationSelectorVBoxValue().equals(other.getImplementationSelectorVBoxValue())) return false;
		else if(!getSelectedActions().equals(other.getSelectedActions())) return false;
		else return true;
	}
	
	@Override
	public Object getSelectedActions() {
		return actionCheckBoxVBox.getCurrentValue();
	}
	
	protected boolean getIsConditionRow() {
		return isConditionRow;
	}
	
	@Override
	public String getImplementationSelectorVBoxValue() {
		return (String) implementationSelectorVBox.getCurrentValue();
	}
	
	private void addActionCheckBox() {
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(newActionOptions);
		getItems().add(actionCheckBoxVBox);
	}
	
	private void addBuildActionButton() {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		getItems().add(buildActionButton);
	}

	@Override
	public void addAction() {
		actionCheckBoxVBox.addAction();
	}

	@Override
	public void removeAction(Integer action) {
		actionCheckBoxVBox.removeAction(action);
	}

	@Override
	public Label getLabel() {
		return label;
	}

	@Override
	public Label getImplementationSelectorLabel() {
		return implementationSelectorVBox.getLabel();
	}
	
}
