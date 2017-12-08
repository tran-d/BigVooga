package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import authoring.AbstractSpriteObject;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteParameterI;
import authoring.SpriteParameterSidebarManager;
import authoring.SpriteSetHelper;
import authoring_actionconditions.ActionConditionTab;
import authoring_actionconditions.ControllerConditionActionTabs;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class DisplayPanel extends VBox {
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;
	private VBox myParamTabVBox;
	private TextArea myParameterErrorMessage;
	private SpriteParameterTabsAndInfo mySParameterTAI;
	private SpriteInventoryTabAndInfo mySInventoryTAI;
	private SpriteUtilityTabAndInfo mySUtilityTAI;
	private SpriteAnimationSequenceTabsAndInfo mySAnimationSequenceTAI;
	private ObjectProperty<Boolean> multipleCellsActiveProperty;
	private SpriteParameterSidebarManager mySPSM;
	private VBox spriteEditorAndApplyButtonVBox;
	private AuthoringEnvironmentManager myAEM;

	private static final String ACTIONCONDITIONTITLES_PATH = "TextResources/ConditionActionTitles";
	private static final double DISPLAY_PANEL_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH/2 - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH-155;
	private static final double DISPLAY_PANEL_HEIGHT = 495;
	
	public static final ResourceBundle conditionActionTitles = ResourceBundle.getBundle(ACTIONCONDITIONTITLES_PATH);
//	private SpriteSetHelper mySSH;
	
	protected DisplayPanel(SpriteParameterSidebarManager SPSM, AuthoringEnvironmentManager AEM) {
		mySPSM = SPSM;
		myAEM = AEM;
		multipleCellsActiveProperty = new SimpleObjectProperty<Boolean>();
		mySParameterTAI = new SpriteParameterTabsAndInfo();
		mySInventoryTAI = new SpriteInventoryTabAndInfo(myAEM);
		mySAnimationSequenceTAI = new SpriteAnimationSequenceTabsAndInfo();
		mySUtilityTAI = new SpriteUtilityTabAndInfo();
		System.out.println("made SPTAI in MENU");
		setUpMenu();
	}
	

	public DisplayPanel() {
		// TODO Auto-generated constructor stub
	}
	

	private void setErrorMessage() {
		myParameterErrorMessage = new TextArea("Either no active cells or active cells have different parameters");
	}

	private void setSpriteInfoAndVBox() {
		spriteEditorAndApplyButtonVBox = new VBox();
		spriteEditorAndApplyButtonVBox.getChildren().addAll(mySpriteTabs, this.makeApplyButton());
	}

	private Map<String, List<SpriteParameterI>> getParametersOfActiveCells() throws Exception {
		return mySPSM.getActiveSprite().getParameters();
	}

	private SpriteObject getActiveCell() throws Exception {
//		System.out.println("MYAEMACTIVE: " + myAEM.getActiveCell());
		return mySPSM.getActiveSprite();
	}
	
	private void checkMultipleCellsActive(){
		this.multipleCellsActiveProperty.set(mySPSM.multipleActive());
	}

	private void setUpMenu() {
		setErrorMessage();
		createParameterCategoryTabs();
		createSpriteTabs();
	//createSpriteCreator();
		this.setPrefSize(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT);
		setSpriteInfoAndVBox();

		// createStatePane(new VBox());
	}

	
	
	private void createActionConditionTabs() {
		ActionConditionTab conditions = new ActionConditionTab(conditionActionTitles.getString("ConditionsTabTitle"));
		ActionConditionTab actions = new ActionConditionTab(conditionActionTitles.getString("ActionsTabTitle"));
		ControllerConditionActionTabs controllerConditionActionTabs = new ControllerConditionActionTabs(conditions,actions);
		mySpriteTabs.getTabs().addAll(conditions,actions);
	}
	
	private void createParameterTab(){
		Tab parameters = new Tab("Parameters");
		parameters.setContent(createContainingVBoxToPlaceInParameterTab());
		mySpriteTabs.getTabs().addAll(parameters);
	}
	
	private void createDialogueTab(){
		Tab dialogue = new Tab("Dialogue");
		dialogue.setContent(new TextArea("dialogue goes here"));
		mySpriteTabs.getTabs().addAll(dialogue);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus)->{
			dialogue.setDisable(newStatus);
		});
	}
	
	private void createInventoryTab(){
		Tab inventory = new Tab("Inventory");
		inventory.setContent(mySInventoryTAI.getContainingVBox());
		mySpriteTabs.getTabs().addAll(inventory);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus)->{
			inventory.setDisable(newStatus);
		});
	}
	
	private void createUtilityTab(){
		Tab utility = new Tab("Utility");
		utility.setContent(mySUtilityTAI.getScrollPane());
		mySpriteTabs.getTabs().addAll(utility);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus)->{
			utility.setDisable(newStatus);
		});
	}
	
	private void createAnimationTab(){
		Tab animations = new Tab("Animations");
		animations.setContent(mySAnimationSequenceTAI.getScrollPane());
		mySpriteTabs.getTabs().addAll(animations);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus)->{
			animations.setDisable(newStatus);
		});
	}
	

	private void createSpriteTabs() {
		mySpriteTabs = new TabPane();
		mySpriteTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		mySpriteTabs.setSide(Side.TOP);
		createParameterTab();
		createDialogueTab();
		createActionConditionTabs();
		createInventoryTab();
		createUtilityTab();
		createAnimationTab();
		// .setOnSelectionChanged(e->{displayParams();});
		// parameters.setContent(myParamTabs);
//		mySpriteTabs.getTabs().addAll(parameters, dialogue);
		
	}

	/**
	 * Creates the VBox that will contain the TabPane of the Active Sprite's parameters
	 * This VBox will be the content of the Tab that says parameters.
	 * @author Samuel
	 * @return VBox - the VBox to contain Sprite Parameter Info
	 */
	private VBox createContainingVBoxToPlaceInParameterTab() {
		myParamTabVBox = new VBox();
		
		myParamTabVBox.getChildren().addAll(myParamTabs);

		setDefaultErrorNoSpriteTabPane();

		// theHorizTabs = myParamTabs;


		// myParamTabVBox.getChildren().addAll(theHorizTabs, applyButton);
		// addParameterErrorMessage();
		return myParamTabVBox;
	}
	
	/**
	 * Make's the apply button that the user will click to apply changes the Active Sprite
	 * Makes button's set on action call the 'apply' method in this class. 
	 * @author Samuel
	 * @return Button - the button UI component.
	 */
	private Button makeApplyButton(){
		Button applyButton = new Button();
		applyButton.textProperty().setValue("Apply");
		applyButton.setOnAction(e -> {
			apply();
		});
		return applyButton;
	}

	/**
	 * Ensures that the instance variable containing the TabPane of the Active Sprite's 
	 * Parameters is pointed to the class controlling that info. 
	 * 
	 * @author Samuel
	 * 
	 */
	private void createParameterCategoryTabs() {
		// mySPTAI.createCategoryTabs();
		myParamTabs = mySParameterTAI.getTabPane();

		// myParamTabs = new TabPane();
		// myParamTabs.setSide(Side.RIGHT);
		// myParamTabs.setPrefHeight(500);
		// myParamTabs.setPrefWidth(400);

	}

	private void clearAllSpriteEditorTabs() {
//		myParamTabs.getTabs().clear();
		mySParameterTAI.clearTabPane();
		mySInventoryTAI.reset();
//		mySUtilityTAI.reset();
//		mySAnimationSequenceTAI.reset();
	}

	protected void removeSpriteEditorVBox() {
		if (this.getChildren().contains(spriteEditorAndApplyButtonVBox)) {
			this.getChildren().remove(spriteEditorAndApplyButtonVBox);
		}
	}

	void addSpriteEditorVBox() {
		if (!this.getChildren().contains(spriteEditorAndApplyButtonVBox)) {
			this.getChildren().addAll(spriteEditorAndApplyButtonVBox);
		}
	}

	private void setDefaultErrorNoSpriteTabPane() {
		clearAllSpriteEditorTabs();
		removeSpriteEditorVBox();
		addSpriteEditorErrorMessage();
	}

	private void removeSpriteEditorErrorMessage() {
		if (this.getChildren().contains(myParameterErrorMessage)) {
			this.getChildren().remove(myParameterErrorMessage);
		}
	}

	private void addSpriteEditorErrorMessage() {
		if (!this.getChildren().contains(myParameterErrorMessage)) {
			// System.out.println(myParamTabVBox.getChildren().size());
			int numChildren = this.getChildren().size();
			this.getChildren().add(numChildren, myParameterErrorMessage);
		}
	}

	protected void updateParameterTab() {

		System.out.println("Updating....");
		try {
			AbstractSpriteObject activeCell = getActiveCell();
			this.checkMultipleCellsActive();
			clearAllSpriteEditorTabs();
			removeSpriteEditorErrorMessage();
			mySParameterTAI.create(activeCell);
			
			if (!mySPSM.multipleActive()){	
				mySInventoryTAI.setSpriteObjectAndUpdate(activeCell);
				mySUtilityTAI.setSpriteObjectAndUpdate(activeCell);
				mySAnimationSequenceTAI.setSpriteObject(activeCell);
			}
			addSpriteEditorVBox();
		} catch (Exception e) {
			// throw new RuntimeException();
			e.printStackTrace();
			setDefaultErrorNoSpriteTabPane();
		}
		this.setPrefWidth(DISPLAY_PANEL_WIDTH);
	}

	private void formatParametersVBox(VBox in) {
		in.setPrefWidth(500);
		in.setPrefHeight(500);
	}

	private ScrollPane createStatePane(VBox temp) {
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setPrefSize(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT);
		myStateSP_dummy.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		formatParametersVBox(temp);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
	}

//	private void buttonInteraction() {
//		// TODO
//	}

	private void apply() {
		mySParameterTAI.apply();
		if (!mySPSM.multipleActive()){
		mySInventoryTAI.apply();
		mySAnimationSequenceTAI.apply();
		}
//		OwensActiosn.apply()
		mySPSM.apply();
	}

	private void addParameter() {
		List<String> choices = new ArrayList<>();
		choices.add("Boolean");
		choices.add("String");
		choices.add("Double");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Boolean", choices);
		dialog.setTitle("Add Parameter");
		dialog.setContentText("Choose Parameter Type:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(type -> createNewParameter(type));
	}

	private void createNewParameter(String type) {
	}
}
