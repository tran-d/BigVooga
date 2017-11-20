package actionTabControllers;

import authoring_UI.ActionConditionVBox;
import authoring_UI.ActionTab;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ControllerTopToolBar {
	
	private Button addCondition;
	private Button addAction;
	private ChoiceBox<String> addConditionChoiceBox;
	private ChoiceBox<String> addActionChoiceBox;
	private ActionConditionVBox conditionVBox;
	private ActionConditionVBox actionVBox;
	
	public ControllerTopToolBar(Button addCondition,Button addAction,ChoiceBox<String> addActionChoiceBox,ChoiceBox<String> addConditionChoiceBox,
			ActionConditionVBox conditionVBox,ActionConditionVBox actionVBox) {
		this.addCondition = addCondition;
		this.addAction = addAction;
		this.addActionChoiceBox = addActionChoiceBox;
		this.addConditionChoiceBox = addConditionChoiceBox;
		this.conditionVBox = conditionVBox;
		this.actionVBox = actionVBox;
		addListeners();
	}
	
	private void addListeners() {
		addCondition.setOnAction(e -> conditionVBox.addActionCondition(addConditionChoiceBox.getValue()));
		addAction.setOnAction(e -> actionVBox.addActionCondition(addActionChoiceBox.getValue()));
	}
}
