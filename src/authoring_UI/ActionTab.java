package authoring_UI;

import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActionTab extends Tab {
	
	private static final String TITLE = "Actions";
	private static final int SPACING = 10;
	private static final String ACTIONTAB_RESOURCE_PATH = "ActionTabResources.properties";
	
	private ResourceBundle actionTabResources;
	private HBox actionConditionManager;
	private VBox conditions;
	private VBox actions;
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
		//addAction.setOnAction(e -> addNewActionConditionRow());
		Button removeAction = new Button(actionTabResources.getString("RemoveActionButtonLabel"));
		topButtons.getChildren().addAll(addAction,removeAction);
	}
	
	

}
