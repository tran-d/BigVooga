package authoring_actionconditions;

import java.util.ResourceBundle;

import ActionConditionClasses.ChoiceBoxVBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import tools.DisplayLanguage;

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

public class ActionConditionRow extends ToolBar implements ActionConditionRowI {

	protected static final double ROW_WIDTH = 600;
	protected static final double TREE_VIEW_WIDTH = 400;
	protected static final double EXPANDED_HEIGHT = 300;
	protected static final double COLLAPSED_HEIGHT = 25;

	protected static final String ENTER_VALID_INPUT = "EnterValid";
	protected static String EMPTY_CHOICEBOX = "EmptyChoiceBox";
	protected static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	protected static final String DOUBLE_INPUT_MESSAGE = "EnterDouble";
	protected static final String BOOLEAN_INPUT_MESSAGE = "EnterBoolean";
	protected static final String ACTIONCONDITION_RESOURCE_PATH = "TextResources/ActionConditionVBoxResources";

	protected ResourceBundle actionConditionVBoxResources;
	private int labelInt;
	private Label label;
	private Label IDlabel;
	private ChoiceBoxVBox<String> implementationSelectorVBox;

	public ActionConditionRow(int ID, ActionConditionVBox<?> ACVBox) {
		super();
		actionConditionVBoxResources = ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
		labelInt = ID;
		IDlabel = new Label(Integer.toString(ID));
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		getItems().addAll(IDlabel, separator);
	}

	// public ActionConditionRow(int ID, String label, String selectorLabel, String
	// selectedConditionAction,
	// ActionConditionVBox<?> ACVBox) {
	// super();
	// actionConditionVBoxResources =
	// ResourceBundle.getBundle(ACTIONCONDITION_RESOURCE_PATH);
	// this.label = new Label(label);
	// labelInt = ID;
	// IDlabel = new Label(Integer.toString(ID));
	// Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
	// ObservableList<String> actionConditionOptions =
	// ActionConditionTabUtil.convertToObservableList(
	// actionConditionVBoxResources.getString(label +
	// actionConditionVBoxResources.getString("OptionsTag")));
	// implementationSelectorVBox = new ChoiceBoxVBox<String>(selectorLabel,
	// actionConditionOptions);
	// implementationSelectorVBox.setValue(selectedConditionAction);
	// getItems().addAll(IDlabel, separator, this.label);
	// }

	@Override
	public void decreaseLabelID() {
		labelInt--;
		IDlabel.setText(Integer.toString(labelInt));
	}

	@Override
	public String getImplementationSelectorVBoxValue() {
		return (String) implementationSelectorVBox.getCurrentValue();
	}

	@Override
	public Label getLabel() {
		return label;
	}

	@Override
	public Label getImplementationSelectorLabel() {
		return implementationSelectorVBox.getLabel();
	}

	@Override
	public int getRowID() {
		return labelInt;
	}
	
	protected void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

}
