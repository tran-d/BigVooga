package authoring_UI;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActionTab extends Tab {
	
	private static final String TITLE = "Actions";
	private static final int SPACING = 10;
	private static final String ACTIONTAB_RESOURCE_PATH = "TextResources/ActionTabResources";
	
	private ResourceBundle actionTabResources;
	private HBox actionConditionManager;
	private VBox conditions;
	private VBox actions;
	private VBox conditionSelector;
	private VBox actionSelector;
	private VBox actionEliminator;
	private HBox topButtons;
	
	public ActionTab() {
		super(TITLE);
		actionTabResources = ResourceBundle.getBundle(ACTIONTAB_RESOURCE_PATH);
		actionConditionManager = new HBox();
		conditions = new VBox();
		actions = new VBox();
		setUpTopButtons();
		setContent(actionConditionManager);
		actionConditionManager.getChildren().addAll(topButtons,conditions,actions);
	}
	
	private void setUpTopButtons() {
		topButtons = new HBox(SPACING);
		topButtons.setPadding(new Insets(SPACING));
		topButtons.setTranslateY(SPACING);
		Button addAction = new Button(actionTabResources.getString("AddActionButtonLabel"));
		ChoiceBox<String> conditionOptions = new ChoiceBox<String>(convertToObservableList(actionTabResources.getString("ConditionOptions")));
		ChoiceBox<String> actionOptions = new ChoiceBox<String>(convertToObservableList(actionTabResources.getString("ActionOptions")));
		VBox conditionSelectorVBox = LabelforNode.addVBoxwithLabel(actionTabResources.getString("ConditionSelectorLabel"), conditionOptions);
		VBox actionSelectorVBox = LabelforNode.addVBoxwithLabel(actionTabResources.getString("ActionSelectorLabel"), actionOptions);
		Button removeAction = new Button(actionTabResources.getString("RemoveActionButtonLabel"));
		ChoiceBox<Integer> removeRow = new ChoiceBox<Integer>();
		VBox removeRowVBox = LabelforNode.addVBoxwithLabel(actionTabResources.getString("ActionRemoverLabel"),removeRow);
		topButtons.getChildren().addAll(addAction,conditionSelectorVBox,actionSelectorVBox,removeAction,removeRowVBox);
	}
	
	private ObservableList<String> convertToObservableList(String options) {
		String[] optionsSplit = options.split(actionTabResources.getString("Splitter"));
		ObservableList<String> listOptions = FXCollections.observableArrayList();
		for(String option : optionsSplit) {
			listOptions.add(option);
		}
		return listOptions;
	}

}
