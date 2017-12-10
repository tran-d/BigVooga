package authoring_actionconditions;

import java.util.List;

import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ActionCheckBoxVBoxI;
import engine.Condition;
import engine.operations.OperationFactory;
import engine.operations.booleanops.BooleanOperation;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

/**
 * Class representing a condition row for sprites.
 * 
 * @author DavidTran
 *
 */
public class ConditionRow extends ActionConditionRow implements ActionCheckBoxVBoxI {

	private static final String PRIORITY_NUMBER_PROMPT = "EnterPriority";
	private static final String INTEGER_INPUT_MESSAGE = "EnterInteger";
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;

	private OperationNameTreeItem operationNameTreeItem;
	private TreeView<HBox> operationTreeView;
	private TextField integerTF;

	public ConditionRow(int ID, String label, String selectorLabel, String selectedCondition,
			ObservableList<Integer> newActionOptions, ConditionVBox<ConditionRow> ACVBox) {
		super(ID, ACVBox);
		addActionCheckBox(newActionOptions);

		operationNameTreeItem = new OperationNameTreeItem("Boolean");
		operationTreeView = new TreeView<>(operationNameTreeItem);
		integerTF = createIntegerTextField();
		this.getItems().addAll(makeIntegerInputPrompt(integerTF), operationTreeView);
	}

	public ConditionRow(int ID, String label, String selectorLabel, String selectedCondition,
			ObservableList<Integer> newActionOptions, List<Integer> selectedActionOptions,
			ConditionVBox<ConditionRow> ACVBox) {
		this(ID, label, selectorLabel, selectedCondition, newActionOptions, ACVBox);
		getItems().remove(actionCheckBoxVBox);
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(newActionOptions, selectedActionOptions);
		getItems().add(2, actionCheckBoxVBox);

	}

	/********************** PUBLIC METHODS ***********************/

	public TreeItem<HBox> getRootTreeItem() {
		return operationNameTreeItem;
	}

	public TreeView<HBox> getTreeView() {
		return operationTreeView;
	}

	public void changeRowTVSize() {
		if (operationNameTreeItem.isExpanded()) {
			this.setPrefHeight(EXPANDED_HEIGHT);
			operationTreeView.setPrefHeight(EXPANDED_HEIGHT);
		} else {
			this.setPrefHeight(COLLAPSED_HEIGHT);
			operationTreeView.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}

	public Condition getCondition() {

		if (integerTF.getText().equals("")) {
			showError(INVALID_INPUT_MESSAGE, INTEGER_INPUT_MESSAGE);
			return null;
		} else
			return new Condition(Integer.parseInt(integerTF.getText()),
					(BooleanOperation) operationNameTreeItem.makeOperation());

	}

	@Override
	public Object getSelectedActions() {
		return actionCheckBoxVBox.getCurrentValue();
	}

	@Override
	public void addAction() {
		actionCheckBoxVBox.addAction();
	}

	@Override
	public void removeAction(Integer action) {
		actionCheckBoxVBox.removeAction(action);
	}

	protected void setNewActionCheckBoxVBoxOptions(ObservableList<Integer> newOptions) {
		actionCheckBoxVBox.setNewOptions(newOptions);
	}

	private void addActionCheckBox(ObservableList<Integer> newActionOptions) {
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(newActionOptions);
		getItems().add(actionCheckBoxVBox);
	}

	private HBox makeIntegerInputPrompt(TextField tf) {
		Label lb = new Label();
		lb.textProperty().bind(DisplayLanguage.createStringBinding(PRIORITY_NUMBER_PROMPT));
		HBox hb = new HBox(lb, tf);
		return hb;
	}

	private TextField createIntegerTextField() {
		TextField tf = new TextField();
		tf.setOnKeyReleased(e -> {
			checkIntegerInput(tf);
		});
		return tf;
	}

	private void checkIntegerInput(TextField tf) {
		try {
			if (!tf.getText().equals(""))
				Integer.parseInt(tf.getText());
		} catch (NumberFormatException e) {
			showError(INVALID_INPUT_MESSAGE, INTEGER_INPUT_MESSAGE);
			tf.clear();
		}
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

}
