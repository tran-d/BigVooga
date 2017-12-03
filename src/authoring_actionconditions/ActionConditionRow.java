package authoring_actionconditions;

import java.util.ResourceBundle;

import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ActionCheckBoxVBoxI;
import ActionConditionClasses.ChoiceBoxVBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

/**
 * ActionConditionRow purpose--in each action/condition tab, there is a list of
 * actions or conditions that a user can add. These rows contain all of the
 * information that the user can add for each action or condition.
 * assumptions--an actioncheckboxvbox is added to an action tab, which is
 * shouldn't be. It should only be added to condition tab since conditions
 * select which actions they cause dependencies--depends on the choiceboxvbox
 * class to instantiate itself and present choices to the user an example of how
 * to use it--pass in ID (row #), label (for the label of specific action or
 * condition), selector Label (to further select the action or condition that
 * the user wants), if it is a conditionRow, and the new potential list of
 * actionOptions any other details users should know--condition and action rows
 * only differ between in that condition rows have an actioncheckboxvbox
 * 
 * @author Owen Smith
 *
 */

public class ActionConditionRow extends ToolBar implements ActionCheckBoxVBoxI {

	private static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";

	private ResourceBundle actionConditionVBoxResources;
	private int labelInt;
	private Label IDlabel;
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;
	private ObservableList<Integer> newActionOptions;
	private BuildActionView view;
	private ChoiceBoxVBox<String> actionOptions;
	private ActionConditionVBox ACVBox;

	public ActionConditionRow(int ID, String label, String selectorLabel, boolean isConditionRow,
			ObservableList<Integer> newActionOptions, ActionConditionVBox ACVBox) {
		super();
		this.newActionOptions = newActionOptions;
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
		labelInt = ID;
		IDlabel = new Label(Integer.toString(ID));
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		ObservableList<String> actionConditionOptions = ActionConditionTabUtil.convertToObservableList(
				actionConditionVBoxResources.getString(label + actionConditionVBoxResources.getString("OptionsTag")));
		actionOptions = new ChoiceBoxVBox<String>(selectorLabel,
				actionConditionOptions);
		getItems().addAll(IDlabel, separator, new Label(label), actionOptions);
		if (isConditionRow) {
			addActionCheckBox();
		} 
//		else
//			addBuildActionButton(e -> openBuildWindow());
	}

	protected void setNewActionCheckBoxVBoxOptions(ObservableList<Integer> newOptions) {
		actionCheckBoxVBox.setNewOptions(newOptions);
	}

	protected void decreaseLabelID() {
		labelInt--;
		IDlabel.setText(Integer.toString(labelInt));
	}

	private void addActionCheckBox() {
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(
				actionConditionVBoxResources.getString("ActionCheckBoxLabel"), newActionOptions);
		getItems().add(actionCheckBoxVBox);
	}

	private void addBuildActionButton(EventHandler<ActionEvent> handler) {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		buildActionButton.setOnAction(handler);
		getItems().add(buildActionButton);
	}

	private void openBuildWindow() {
//		if (view == null && actionOptions.getSelected() != null)
//			view = new BuildActionView(ACVBox, (ActionConditionRow) ACVBox.getChildren().get(labelInt - 1));
	}

	@Override
	public void addAction() {
		actionCheckBoxVBox.addAction();
	}

	@Override
	public void removeAction(Integer action) {
		actionCheckBoxVBox.removeAction(action);
	}
	
	public int getRowID() {
		return labelInt;
	}

}
