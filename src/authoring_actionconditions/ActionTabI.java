package authoring_actionconditions;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface ActionTabI<T> {
	
	public Integer getRemoveValue();
	public void addButtonListener(EventHandler<ActionEvent> e);
	public void addRemoveListener(EventHandler<ActionEvent> e);
	public ActionConditionVBox<T> getActionConditionVBox();
	public ActionConditionVBox<T> setActionConditionVBox();
	public void setNoReturnActionConditionVBox(ActionConditionVBox<T> actionConditonVBoxNew);
	public TopToolBar getTopToolBar();
	public void setTopToolBar(TopToolBar topToolBar);
	public String getSelectorLabel();
	public ObservableList<Integer> getCurrentActions();
	public String getActionCondition();
	public void addAction(String label);
	public void addRemoveOption();
	public void removeActionCondtion(Integer row);
	public void removeRowOption(Integer row);
	public void addTopToolBarListChangeListener(ListChangeListener<Integer> listChangeListener);
	
}
