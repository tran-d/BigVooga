package authoring_UI;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class ActionTab extends Tab {
	
	private static final String TITLE = "Actions";
	private static final String ACTIONTAB_RESOURCE_PATH = "TextResources/ActionTabResources";
	
	private ResourceBundle actionTabResources;
	private VBox actionConditionManager;
	private VBox conditions;
	private VBox actions;
	private ToolBar conditionButtons;
	private ToolBar actionButtons;
	
	public ActionTab() {
		super(TITLE);
		actionTabResources = ResourceBundle.getBundle(ACTIONTAB_RESOURCE_PATH);
		actionConditionManager = new VBox();
		setContent(actionConditionManager);
		conditionButtons = addTopToolBar("AddConditionButtonLabel","ConditionOptions","ConditionSelectorLabel","RemoveConditionButtonLabel");
		actionButtons = addTopToolBar("AddActionButtonLabel","ActionOptions","ActionSelectorLabel","RemoveActionButtonLabel");
		conditions = new VBox();
		actions = new VBox();
		Separator separator = makeVerticalSeparator();
		actionConditionManager.getChildren().addAll(conditionButtons,actionButtons,conditions,separator,actions);
	}
	
	private ToolBar addTopToolBar(String addButtonTitle,String optionsTitle,String selectorLabel,String remove) {
		Button addButton = new Button(actionTabResources.getString(addButtonTitle));
		ChoiceBox<String> options = new ChoiceBox<String>(convertToObservableList(actionTabResources.getString(optionsTitle)));
		VBox selectorVBox = LabelforNode.addVBoxwithLabel(actionTabResources.getString(selectorLabel), options);
		Separator separator = makeVerticalSeparator();
		separator.setOrientation(Orientation.VERTICAL);
		Button removeButton = new Button(actionTabResources.getString(remove));
		ChoiceBox<Integer> removeRow = new ChoiceBox<Integer>();
		VBox removeRowVBox = LabelforNode.addVBoxwithLabel(actionTabResources.getString("RemoverLabel"),removeRow);
		return new ToolBar(addButton,selectorVBox,separator,removeButton,removeRowVBox);
	}
	
	private ObservableList<String> convertToObservableList(String options) {
		String[] optionsSplit = options.split(actionTabResources.getString("Splitter"));
		ObservableList<String> listOptions = FXCollections.observableArrayList();
		for(String option : optionsSplit) {
			listOptions.add(option);
		}
		return listOptions;
	}
	
	private Separator makeVerticalSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}

}
