package authoring_actionconditions;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;

public interface ActionTabI<T> {
	
	public Integer getRemoveValue();
	public void addButtonListener(EventHandler<ActionEvent> e);
	public void addRemoveListener(EventHandler<ActionEvent> e);
	public ActionConditionVBox<T> getActionConditionVBox();
	public TopToolBar getTopToolBar();
	public String getSelectorLabel();
	public ActionConditionVBox<T> setActionConditionVBox();
	public ObservableList<Integer> getCurrentActions();
	public String getActionCondition();
	public void addAction(String label);
	public void addRemoveOption();
	public void removeActionCondtion(Integer row);
	public void removeRowOption(Integer row);
	public void addTopToolBarListChangeListener(ListChangeListener<Integer> listChangeListener);
	
}
