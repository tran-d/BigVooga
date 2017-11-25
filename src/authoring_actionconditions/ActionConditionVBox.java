package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.layout.VBox;

public class ActionConditionVBox extends VBox implements ActionConditionVBoxI{
	
	private String selectorLabel;
	private List<ActionConditionRow> rows;
	
	public ActionConditionVBox(String selectorString) {
		super();
		selectorLabel = selectorString;
		rows = new LinkedList<ActionConditionRow>();
	}

	@Override
	public void addActionCondition(String label) {
		ActionConditionRow actionConditionRow = new ActionConditionRow(rows.size() + 1,label,selectorLabel);
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
