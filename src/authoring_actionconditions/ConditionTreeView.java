package authoring_actionconditions;

import engine.Condition;
import engine.operations.VoogaType;
import engine.operations.booleanops.BooleanOperation;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

public class ConditionTreeView extends TreeView<HBox> {

	private static final double TREE_VIEW_WIDTH = 400;
	private static final double INTEGER_TEXTFIELD_WIDTH = 100;
	private static final double EXPANDED_HEIGHT = 300;
	private static final double COLLAPSED_HEIGHT = 35;
	private static final double VBOX_SPACING = 10;
	private static final String PRIORITY_NUMBER_PROMPT = "EnterPriority";

	private OperationNameTreeItem operationNameTreeItem;
	private TextField priorityIntegerTF;
	private VBox booleanOperationTreeView;
	private ConditionRow conditionRow;

	public ConditionTreeView(ConditionRow conditionRow) {
		super();
		this.conditionRow = conditionRow;
		operationNameTreeItem = new OperationNameTreeItem("Boolean", "Choose Boolean Operation: ", VoogaType.BOOLEAN,
				() -> changeRowTVSize());
		setRoot(operationNameTreeItem);
		setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
		priorityIntegerTF = createIntegerTextField();
		booleanOperationTreeView = buildBooleanOperationTreeView(this);
	}

	protected VBox getTreeViewVBox() {
		return booleanOperationTreeView;
	}

	public Condition getCondition() {

		try {
			if (priorityIntegerTF.getText().equals("")) {
				// showError(INVALID_INPUT_MESSAGE, INTEGER_INPUT_MESSAGE);
				// return null;
				throw new NumberFormatException();
			} else {
				Condition condition = new Condition(Integer.parseInt(priorityIntegerTF.getText()),
						(BooleanOperation) operationNameTreeItem.makeOperation());
				return condition;
			}

		} catch (NullPointerException e) {
			// showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
			throw e;
			// return null;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	private HBox makeIntegerInputPrompt(TextField tf) {
		Label lb = new Label();
		lb.textProperty().bind(DisplayLanguage.createStringBinding(PRIORITY_NUMBER_PROMPT));
		if (tf == null)
			System.out.println("tf is null");
		if (lb == null)
			System.out.println("lb is null");
		HBox vb = new HBox(lb, tf);
		return vb;
	}

	private VBox buildBooleanOperationTreeView(TreeView<HBox> operationTreeView) {
		VBox booleanOperationTreeView = new VBox(VBOX_SPACING);
		booleanOperationTreeView.getChildren().addAll(makeIntegerInputPrompt(priorityIntegerTF), operationTreeView);
		return booleanOperationTreeView;
	}

	protected void changeRowTVSize() {
		if (operationNameTreeItem.isExpanded()) {
			this.setPrefHeight(EXPANDED_HEIGHT);
			conditionRow.setPrefHeight(EXPANDED_HEIGHT);
		} else {
			this.setPrefHeight(COLLAPSED_HEIGHT);
			conditionRow.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}

	private void checkIntegerInput(TextField tf) {
		try {
			if (!tf.getText().equals(""))
				Integer.parseInt(tf.getText());
		} catch (NumberFormatException e) {
			// showError(INVALID_INPUT_MESSAGE, INTEGER_INPUT_MESSAGE);
			tf.clear();
			throw e;
		}
	}

	private TextField createIntegerTextField() {
		TextField tf = new TextField();
		tf.setPrefWidth(INTEGER_TEXTFIELD_WIDTH);
		tf.setOnKeyReleased(e -> {
			checkIntegerInput(tf);
		});
		return tf;
	}

	protected static void showError(String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Invalid input");
		alert.setContentText(content);
		alert.show();
	}

}
