package authoring_UI;

import java.util.ResourceBundle;

import actionTabControllers.ControllerTopToolBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActionConditionTab extends Tab {
	
	private static final String ACTIONTAB_RESOURCE_PATH = "TextResources/ActionTabResources";
	
	private ResourceBundle actionTabResources;
	private ScrollPane actionConditionManager;
	private TopToolBar conditionButtons;
	private TopToolBar actionButtons;
	private ActionConditionVBox conditions;
	private ActionConditionVBox actions;
	
	public ActionConditionTab(String title) {
		super(title);
		actionTabResources = ResourceBundle.getBundle(ACTIONTAB_RESOURCE_PATH); 
		actionConditionManager = new ScrollPane();
		setContent(actionConditionManager);
		setUpActionConditionManager();
		ControllerTopToolBar controllerTopToolBar = new ControllerTopToolBar(conditionButtons,actionButtons,conditions,actions);
	}

	private void setUpActionConditionManager() {
		conditionButtons = new TopToolBar(actionTabResources,"AddConditionButtonLabel","ConditionOptions","ConditionSelectorLabel","RemoveConditionButtonLabel");
		actionButtons = new TopToolBar(actionTabResources,"AddActionButtonLabel","ActionOptions","ActionSelectorLabel","RemoveActionButtonLabel");
		conditions = new ActionConditionVBox(actionTabResources.getString("ConditionSelectorLabel"));
		actions = new ActionConditionVBox(actionTabResources.getString("ActionSelectorLabel"));
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		VBox mainVBox = new VBox();
		mainVBox.getChildren().addAll(conditionButtons,actionButtons,conditions,separator,actions);
		actionConditionManager.setContent(mainVBox);
	}
}
