package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public class ActionConditionVBox extends VBox implements ActionConditionVBoxI{
	
	private String selectorLabel;
	private List<ActionConditionRow> rows;
	private boolean isConditionVBox;
	
	public ActionConditionVBox(String selectorString,boolean isConditionVBox) {
		super();
		selectorLabel = selectorString;
		this.isConditionVBox = isConditionVBox;
		rows = new LinkedList<ActionConditionRow>();
	}
	
	protected void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		rows.forEach(row -> row.setNewActionCheckBoxVBoxOptions(newActionOptions));
	}

	@Override
	public void addActionCondition(String label,ObservableList<Integer> currentActions) {
		ActionConditionRow actionConditionRow = new ActionConditionRow(rows.size() + 1,label,selectorLabel,isConditionVBox,currentActions);
		rows.add(actionConditionRow);
		getChildren().add(actionConditionRow);
	}

	@Override
	public void removeActionCondition(int row) {
		getChildren().remove(rows.get(row));
		rows.remove(row);
		for(int i = row; i < rows.size(); i++) rows.get(i).decreaseLabelID();
	}

}
