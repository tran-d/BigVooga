package authoring_actionconditions;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public abstract class ActionConditionVBox<T> extends VBox implements ActionConditionVBoxI<T> {

	private String selectorLabel;
	private ObservableList<T> rows;

	public ActionConditionVBox(String selectorString) {
		super();
		selectorLabel = selectorString;
		rows = FXCollections.observableArrayList();
		rows.addListener((ListChangeListener<T>) c -> addOrRemoveRows(c));
	}
	
	public ActionConditionVBox(String selectorString,List<T> rows) {
		this(selectorString);
		this.rows.setAll(FXCollections.observableArrayList(rows));
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
	
	private void addOrRemoveRows(Change<? extends T> c) {
		while(c.next()) {
			System.out.println("added row size " + c.getAddedSize());
			System.out.println("removed row size " + c.getRemovedSize());
			for(T row : c.getRemoved()) {
//				System.out.println("should be removing from vbox");
//				System.out.println("row class" + row.getClass());
				getChildren().remove(row);
			}
			for(T row : c.getAddedSubList()) {
//				System.out.println("should be adding to vbox");
//				System.out.println("added row class " + row.getClass());
				getChildren().add((Node) row);
			}
		}
	}

}
