package authoring_actionconditions;

import java.util.List;

import ActionConditionClasses.ActionCheckBoxVBox;
import ActionConditionClasses.ActionCheckBoxVBoxI;
import engine.operations.OperationFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/**
 * Class representing a condition row for sprites.
 * 
 * @author DavidTran
 *
 */
public class ConditionRow extends ActionConditionRow implements ActionCheckBoxVBoxI {
	
	private ActionCheckBoxVBox<Integer> actionCheckBoxVBox;
	private OperationFactory operationFactory = new OperationFactory();
	private TreeItem<HBox> operation;
	private TreeView<HBox> operationTreeView;

	public ConditionRow(int ID, String label, String selectorLabel,String selectedCondition,ObservableList<Integer> newActionOptions, ConditionVBox<ConditionRow> ACVBox) {
		super(ID, ACVBox);
		addActionCheckBox(newActionOptions);
	
		
	}
	
	public ConditionRow(int ID,String label,String selectorLabel, String selectedCondition, ObservableList<Integer> newActionOptions,
			List<Integer> selectedActionOptions,ConditionVBox<ConditionRow> ACVBox) {
		this(ID,label,selectorLabel,selectedCondition,newActionOptions,ACVBox);
		getItems().remove(actionCheckBoxVBox);
		actionCheckBoxVBox = new ActionCheckBoxVBox<Integer>(newActionOptions,selectedActionOptions);
		getItems().add(2, actionCheckBoxVBox);
		
	}

	public TreeItem<HBox> getRootTreeItem() {
		return operation;
	}

	public TreeView<HBox> getTreeView() {
		return operationTreeView;
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
	
	


}
