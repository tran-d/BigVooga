package authoring_actionconditions;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.layout.VBox;

public abstract class ActionConditionVBox<T> extends VBox implements ActionConditionVBoxI<T> {

	private String selectorLabel;
	private List<T> rows;

	public ActionConditionVBox(String selectorString) {
		super();
		selectorLabel = selectorString;
		rows = new LinkedList<T>();
	}
	
	public ActionConditionVBox(String selectorString,List<T> rows) {
		this(selectorString);
		this.rows = rows;
	}
	
	@Override
	public List<T> getRows() {
		return rows;
	}
		
//		ActionConditionRow actionConditionRow = new ActionConditionRow(rows.size() + 1, label, selectorLabel,
//				isConditionVBox, currentActions, this);
//		rows.add(actionConditionRow);
//		if (!isConditionVBox) {
//			
//		} else
//			getChildren().add(actionConditionRow);
	//}

	@Override
	public void removeConditionAction(int row) {
		getChildren().remove(rows.get(row));
		rows.remove(row);
		for (int i = row; i < rows.size(); i++)
			((ActionConditionRow) rows.get(i)).decreaseLabelID();
	}

	@Override
	public String getSelectorLabel() {
		return selectorLabel;
	}

	@Override
	public void addToRows(ActionConditionRow actionConditionRow) {
		rows.add((T) actionConditionRow);
	}

}
