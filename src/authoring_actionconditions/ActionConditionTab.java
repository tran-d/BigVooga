package authoring_actionconditions;

import java.util.ResourceBundle;

import authoring_UI.Menu;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class ActionConditionTab extends Tab {
	
	private static final double SPACING = 10;
	
	private ResourceBundle actionTabResources;
	private ScrollPane actionConditionManager;
	private TopToolBar buttons;
	private ActionConditionVBox actionConditionVBox;
	private Boolean isConditionTab;
	
	public ActionConditionTab(String title) {
		super(title);
		determineTabType(title);
		String resourcePath = "TextResources/" + title.substring(0,title.length() - 1) + "TabResources";
		actionTabResources = ResourceBundle.getBundle(resourcePath); 
		actionConditionManager = new ScrollPane();
		setContent(actionConditionManager);
		setUpActionConditionManager();
		ControllerTopToolBar controllerTopToolBar = new ControllerTopToolBar(buttons,actionConditionVBox);
	}

	private void setUpActionConditionManager() {
		buttons = new TopToolBar(actionTabResources,"AddButtonLabel","Options","SelectorLabel","RemoveButtonLabel");
		actionConditionVBox = new ActionConditionVBox(actionTabResources.getString("SelectorLabel"),isConditionTab);
		VBox mainVBox = new VBox(SPACING);
		mainVBox.getChildren().addAll(buttons,actionConditionVBox);
		actionConditionManager.setContent(mainVBox);
	}
	
	protected void addTopToolBarListChangeListener(ListChangeListener<Integer> listChangeListener) {
		buttons.addRemoveRowVBoxListener(listChangeListener);
	}
	
	protected ObservableList<Integer> getCurrentActions() {
		return buttons.getRemoveRowVBoxOptions();
	}
	
	protected void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		actionConditionVBox.setNewActionOptions(newActionOptions);
	}
	
	private void determineTabType(String title) {
		if(title.equals(Menu.conditionActionTitles.getString("ConditionsTabTitle"))) isConditionTab = true;
		else isConditionTab = false;
	}
}
