package authoring_actionconditions;

import javafx.collections.ObservableList;

public class ConditionTab<T> extends ActionTab<T> implements ConditionTabI {

	public ConditionTab(String title) {
		super(title);
	}
	
	public ConditionTab(String title,ConditionVBox<T> actionConditionVBox, ActionConditionHBox topToolBar) {
		super(title,actionConditionVBox,topToolBar);
	}

	@Override
	public ActionConditionVBox<T> setActionConditionVBox() {
		return new ConditionVBox<T>(getSelectorLabel());
	}
	
	@Override
	public void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		((ConditionVBox<T>) getActionConditionVBox()).setNewActionOptions(newActionOptions);
	}
	
	@Override
	public void addActionOption() {
		((ConditionVBox<T>) getActionConditionVBox()).addActionOption();
	}
	
	@Override
	public void removeActionOption(Integer action) {
		((ConditionVBox<T>) getActionConditionVBox()).removeActionOption(action);
	}

	@Override
	public void addCondition(String label, ObservableList<Integer> currentActions) {
		((ConditionVBox<T>) getActionConditionVBox()).addCondition(label,currentActions);
	}

}
