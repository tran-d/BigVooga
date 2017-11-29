package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public class ActionConditionVBox extends VBox implements ActionConditionVBoxI{
	
	private String selectorLabel;
	private List<ActionConditionRow> rows;
	private boolean isConditionVBox;
	private ObservableList<Integer> newActionOptions;
	
	public ActionConditionVBox(String selectorString,boolean isConditionVBox) {
		super();
		selectorLabel = selectorString;
		this.isConditionVBox = isConditionVBox;
		rows = new LinkedList<ActionConditionRow>();
	}
	
	protected void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		rows.forEach(row -> row.setNewActionCheckBoxVBoxOptions(newActionOptions));
		this.newActionOptions = newActionOptions;
	}

	@Override
	public void addActionCondition(String label) {
		ActionConditionRow actionConditionRow = new ActionConditionRow(rows.size() + 1,label,selectorLabel,isConditionVBox, newActionOptions);
		rows.add(actionConditionRow);
		getChildren().add(actionConditionRow);
	}

	@Override
	public void removeActionCondition(int row) {
		getChildren().remove(rows.get(row));
		rows.remove(row);
		for(int i = row; i < rows.size(); i++) rows.get(i).decreaseLabelID();
	}
	
	public ObservableList<Integer> getNewActionOptions() {
		return newActionOptions;
	}

	@Override
	public void addActionOption() {
		rows.forEach(row -> row.addActionOption());
	}

	@Override
	public void removeActionOption(Integer action) {
		rows.forEach(row -> row.removeActionOption(action));
	}

}
