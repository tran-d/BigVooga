package authoring_actionconditions;

import java.util.List;

import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ActionCheckBoxVBoxI;
import engine.Condition;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

/**
 * Class representing a condition row for sprites.
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ConditionRow extends ActionConditionRow implements ActionCheckBoxVBoxI {

	private static final String INVALID_SELECTED_ACTIONS_MESSAGE = "Please select valid actions";
	public static final double ROW_EXPANDED_HEIGHT = ActionConditionRow.EXPANDED_HEIGHT
			+ ConditionTreeView.INTEGER_TEXTFIELD_HEIGHT + 2 * ConditionTreeView.VBOX_SPACING;
	public static final double ROW_WIDTH = ActionConditionRow.ROW_WIDTH + 100;
	private ConditionTreeView operationTreeView;
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;
	private VBox treeViewVBox;

	public ConditionRow(int ID, ObservableList<Integer> newActionOptions, ConditionVBox<ConditionRow> ACVBox) {
		super(ID, ACVBox);
		addActionCheckBox(newActionOptions);

		this.setPrefSize(ROW_WIDTH, ROW_EXPANDED_HEIGHT);

		operationTreeView = new ConditionTreeView(this);
		treeViewVBox = operationTreeView.getTreeViewVBox();
		this.getItems().addAll(treeViewVBox);

	}

	public ConditionRow(int ID, ObservableList<Integer> newActionOptions, List<Integer> selectedActionOptions,
			ConditionVBox<ConditionRow> ACVBox, ConditionTreeView tv) {
		this(ID, newActionOptions, ACVBox);
		getItems().removeAll(actionCheckBoxVBox, treeViewVBox);
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(newActionOptions, selectedActionOptions);
		treeViewVBox = tv.getTreeViewVBox();
		operationTreeView = tv;
		getItems().addAll(actionCheckBoxVBox, treeViewVBox);

	}

	public ConditionRow(int ID, ObservableList<Integer> newActionOptions, List<Integer> selectedActionOptions,
			ConditionVBox<ConditionRow> ACVBox, String selectedOperation, Condition condition) {
		this(ID, newActionOptions, ACVBox);
		getItems().removeAll(actionCheckBoxVBox, treeViewVBox);
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(newActionOptions, selectedActionOptions);
		operationTreeView = new ConditionTreeView(this, selectedOperation, condition);
		treeViewVBox = operationTreeView.getTreeViewVBox();
		getItems().addAll(actionCheckBoxVBox, treeViewVBox);
	}

	/********************** PUBLIC METHODS ***********************/

	public ConditionTreeView getTreeView() {
		return operationTreeView;
	}

	protected void reduceTreeView() {
		this.getTreeView().getRoot().setExpanded(false);
		this.getTreeView().changeRowTVSize();
	}

	public void changeRowTVSize() {
		operationTreeView.changeRowTVSize();
	}

	public Condition getCondition() {
		try {
			return operationTreeView.getCondition();
		} catch (NullPointerException | NumberFormatException e) {
			throw e;
		}
	}

	@Override

	public List<Integer> getSelectedActions() throws NullPointerException {
		if (((List<Integer>) actionCheckBoxVBox.getCurrentValue()).isEmpty())
			throw new NullPointerException(INVALID_SELECTED_ACTIONS_MESSAGE);
		else
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

}
