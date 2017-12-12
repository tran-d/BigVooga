package authoring_actionconditions;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConditionTab<T> extends ActionTab<T> implements ConditionTabI {
	
	private static final String DIALOG_TYPE = "ERROR";
	private static final String ERROR_SUMMARY = "Invalid selected actions";

	public ConditionTab(String title) {
		super(title);
	}
	
	public ConditionTab(String title,ConditionVBox<T> actionConditionVBox, ActionConditionHBox topToolBar) {
		super(title,actionConditionVBox,topToolBar);
	}
	
	public void displayRowExceptionMessage(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(DIALOG_TYPE);
		alert.setHeaderText(ERROR_SUMMARY);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public ActionConditionVBox<T> setActionConditionVBox() {
		return new ConditionVBox<T>();
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
	public void addCondition(ObservableList<Integer> currentActions) {
		((ConditionVBox<T>) getActionConditionVBox()).addCondition(currentActions);
	}

}
