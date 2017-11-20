package authoring_UI;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActionTab extends Tab {
	
	private static final String TITLE = "Actions";
	private static final String ACTIONTAB_RESOURCE_PATH = "TextResources/ActionTabResources";
	
	private ResourceBundle actionTabResources;
	private VBox actionConditionManager;
	private ActionConditionVBox conditions;
	private ActionConditionVBox actions;
	
	public ActionTab() {
		super(TITLE);
		actionTabResources = ResourceBundle.getBundle(ACTIONTAB_RESOURCE_PATH);
		actionConditionManager = new VBox();
		setContent(actionConditionManager);
		ToolBar conditionButtons = addTopToolBar("AddConditionButtonLabel","ConditionOptions","ConditionSelectorLabel","RemoveConditionButtonLabel",true);
		ToolBar actionButtons = addTopToolBar("AddActionButtonLabel","ActionOptions","ActionSelectorLabel","RemoveActionButtonLabel",false);
		conditions = new ActionConditionVBox(actionTabResources.getString("ConditionSelectorLabel"));
		actions = new ActionConditionVBox(actionTabResources.getString("ActionSelectorLabel"));
		Separator separator = ActionTabUtil.makeVerticalSeparator();
		actionConditionManager.getChildren().addAll(conditionButtons,actionButtons,conditions,separator,actions);
	}
	
	private ToolBar addTopToolBar(String addButtonTitle,String optionsTitle,String selectorLabel,String remove,Boolean isCondition) {
		Button addButton = new Button(actionTabResources.getString(addButtonTitle));
		ChoiceBox<String> options = new ChoiceBox<String>(ActionTabUtil.convertToObservableList(actionTabResources.getString(optionsTitle)));
		VBox selectorVBox = ActionTabUtil.addVBoxwithLabel(actionTabResources.getString(selectorLabel), options);
		Separator separator = ActionTabUtil.makeVerticalSeparator();
		separator.setOrientation(Orientation.VERTICAL);
		Button removeButton = new Button(actionTabResources.getString(remove));
		ChoiceBox<Integer> removeRow = new ChoiceBox<Integer>();
		VBox removeRowVBox = ActionTabUtil.addVBoxwithLabel(actionTabResources.getString("RemoverLabel"),removeRow);
		return new ToolBar(addButton,selectorVBox,separator,removeButton,removeRowVBox);
	}

}
