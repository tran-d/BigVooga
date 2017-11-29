package authoring_actionconditions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface ActionConditionTabI {
	
	public Integer getRemoveValue();
	public void addButtonListener(EventHandler<ActionEvent> e);
	public void addRemoveListener(EventHandler<ActionEvent> e);
	public void addActionOption();
	public void removeActionOption(Integer action);

}
