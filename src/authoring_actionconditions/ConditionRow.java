package authoring_actionconditions;

import java.util.List;
import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ActionCheckBoxVBoxI;
import engine.Condition;
import engine.operations.booleanops.BooleanOperation;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class representing a condition row for sprites.
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ConditionRow extends ActionConditionRow implements ActionCheckBoxVBoxI {

	private static final double VBOX_SPACING = 10;
	private static final String PRIORITY_NUMBER_PROMPT = "EnterPriority";
	private static final String INTEGER_INPUT_MESSAGE = "EnterInteger";
	private static final double INTEGER_TEXTFIELD_WIDTH = 100;
	private static final String INVALID_SELECTED_ACTIONS_MESSAGE = "Please select valid actions";
	
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;
	private OperationNameTreeItem operationNameTreeItem;
	private TreeView<HBox> operationTreeView;
	private TextField integerTF;
	private VBox booleanOperationTreeView;

	public ConditionRow(int ID,ObservableList<Integer> newActionOptions, ConditionVBox<ConditionRow> ACVBox) {
		super(ID, ACVBox);
		addActionCheckBox(newActionOptions);

		this.setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);

		operationNameTreeItem = new OperationNameTreeItem("Boolean", () -> changeRowTVSize());
		operationTreeView = new TreeView<>(operationNameTreeItem);
		operationTreeView.setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
		integerTF = createIntegerTextField();	
		booleanOperationTreeView = buildBooleanOperationTreeView(operationTreeView);
		this.getItems().addAll(booleanOperationTreeView);
	}

	public ConditionRow(int ID, ObservableList<Integer> newActionOptions, List<Integer> selectedActionOptions,ConditionVBox<ConditionRow> ACVBox,
			TreeView<HBox> tv) {
		this(ID,newActionOptions, ACVBox);
		getItems().removeAll(actionCheckBoxVBox,booleanOperationTreeView);
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(newActionOptions, selectedActionOptions);
		booleanOperationTreeView = buildBooleanOperationTreeView(tv);
		getItems().addAll(actionCheckBoxVBox,booleanOperationTreeView);

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
			System.out.println("expand");
		} else {
			this.setPrefHeight(COLLAPSED_HEIGHT);
			operationTreeView.setPrefHeight(COLLAPSED_HEIGHT);
			System.out.println("collapse");
		}
	}

	public Condition getCondition() {

		try {
			if (integerTF.getText().equals("")) {
				showError(INVALID_INPUT_MESSAGE, INTEGER_INPUT_MESSAGE);
				return null;
			} else
				return new Condition(Integer.parseInt(integerTF.getText()),
						(BooleanOperation) operationNameTreeItem.makeOperation());

		} catch (Exception e) {
			showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
			return null;
		}

	}

	@Override
	public Object getSelectedActions() throws NullPointerException {
		if(( (List<Integer>)actionCheckBoxVBox.getCurrentValue()).isEmpty()) throw new NullPointerException(INVALID_SELECTED_ACTIONS_MESSAGE);
		else return actionCheckBoxVBox.getCurrentValue();
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
	
	private VBox buildBooleanOperationTreeView(TreeView<HBox> operationTreeView) {
		VBox booleanOperationTreeView = new VBox(VBOX_SPACING);
		booleanOperationTreeView.getChildren().addAll(makeIntegerInputPrompt(integerTF), new Label("Choose Boolean Operation: "),
				operationTreeView);
		return booleanOperationTreeView;
	}

	private HBox makeIntegerInputPrompt(TextField tf) {
		Label lb = new Label();
		lb.textProperty().bind(DisplayLanguage.createStringBinding(PRIORITY_NUMBER_PROMPT));
		HBox vb = new HBox(lb, tf);
		return vb;
	}

	private TextField createIntegerTextField() {
		TextField tf = new TextField();
		tf.setPrefWidth(INTEGER_TEXTFIELD_WIDTH);
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

}
