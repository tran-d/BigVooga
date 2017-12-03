package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteParameterI;
import authoring_actionconditions.ActionConditionTab;
import authoring_actionconditions.ControllerConditionActionTabs;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class DisplayPanel extends VBox {
	
	private AuthoringEnvironmentManager myAEM;
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;
	private VBox myParamTabVBox;
	private TextArea myParameterErrorMessage;

	private SpriteParameterTabsAndInfo mySParameterTAI;
	private SpriteInventoryTabAndInfo mySInventoryTAI;

	private VBox spriteEditorAndApplyButtonVBox;

	private static final String ACTIONCONDITIONTITLES_PATH = "TextResources/ConditionActionTitles";
	private static final double DISPLAY_PANEL_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH/2;
	private static final double DISPLAY_PANEL_HEIGHT = WelcomeScreen.HEIGHT/2;
	
	public static final ResourceBundle conditionActionTitles = ResourceBundle.getBundle(ACTIONCONDITIONTITLES_PATH);
	

	protected DisplayPanel(AuthoringEnvironmentManager AEM, MapManager myManager) {
		mySParameterTAI = new SpriteParameterTabsAndInfo();
		mySInventoryTAI = new SpriteInventoryTabAndInfo(AEM);
		System.out.println("made SPTAI in MENU");
		myAEM = AEM;
		setUpMenu();

	}

	private void setErrorMessage() {
		myParameterErrorMessage = new TextArea("Either no active cells or active cells have different parameters");
		// System.out.println("Making error message");
	}

	
	private void setSpriteInfoAndVBox() {
		spriteEditorAndApplyButtonVBox = new VBox(10);
		spriteEditorAndApplyButtonVBox.getChildren().addAll(mySpriteTabs, this.makeApplyButton());
	}
	// protected void displayParams() {
	// Map<String, ArrayList<SpriteParameterI>> paramMap = new HashMap<String,
	// ArrayList<SpriteParameterI>>();
	// try {
	// paramMap = myAEM.getActiveCellParameters().getParameters();
	// for (Map.Entry<String, ArrayList<SpriteParameterI>> entry :
	// paramMap.entrySet()) {
	// String category = entry.getKey();
	// ArrayList<SpriteParameterI> newParams = entry.getValue();
	// FEParameterFactory newFactory = new FEParameterFactory(newParams);
	// myStateSP.setContent(newFactory);
	// }
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// myStateSP.setContent(new TextArea("No params to show"));
	// }
	//
	//
	// this.setPrefWidth(MENU_WIDTH);
	// }

	private HashMap<String, ArrayList<SpriteParameterI>> getParametersOfActiveCells() throws Exception {
		return myAEM.getActiveCell().getParameters();
	}

	private SpriteObject getActiveCell() throws Exception {
//		System.out.println("MYAEMACTIVE: " + myAEM.getActiveCell());
		return myAEM.getActiveCell();
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
	}
	
	private void createInventoryTab(){
		Tab inventory = new Tab("Inventory");
		inventory.setContent(mySInventoryTAI.getContainingVBox());
		mySpriteTabs.getTabs().addAll(inventory);
	}

	private void createSpriteTabs() {
		mySpriteTabs = new TabPane();
		mySpriteTabs.setSide(Side.TOP);
		createParameterTab();
		createDialogueTab();
		createActionConditionTabs();
		createInventoryTab();
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
		applyButton.textProperty().setValue("Apply Button");
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
	}

	protected void removeSpriteEditorVBox() {
		if (this.getChildren().contains(spriteEditorAndApplyButtonVBox)) {
			this.getChildren().remove(spriteEditorAndApplyButtonVBox);
		}
	}

	private void addSpriteEditorVBox() {
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
			clearAllSpriteEditorTabs();
			removeSpriteEditorErrorMessage();
			mySParameterTAI.create(getActiveCell());
			mySInventoryTAI.setSpriteObjectAndUpdate(getActiveCell());
			// HashMap<String, ArrayList<SpriteParameterI>> params =
			// getParametersOfActiveCells();
			//
			// for (Map.Entry<String, ArrayList<SpriteParameterI>> entry :
			// params.entrySet()) {
			//
			// String category = entry.getKey();
			// System.out.println(category);
			// ArrayList<SpriteParameterI> newParams = entry.getValue();
			// FEParameterFactory newFactory = new
			// FEParameterFactory(newParams);
			//
			// Tab newCategory = new Tab(category);
			// newCategory.setContent(createStatePane(newFactory));
			// newCategory.setClosable(false);
			// myParamTabs.getTabs().add(newCategory);

			// }
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
		// return in;
	}

	/*private void createSpriteCreator() {

		Button createSpriteButton = new Button("Create Sprite");

		createSpriteButton.setOnAction(e -> {
//			System.out.println("Button pressed");
			SpriteCreator mySpriteCreatorClass = myMapManager.createNewSpriteCreator();
			VBox mySpriteCreator = mySpriteCreatorClass.getVBox();
			this.getChildren().remove(createSpriteButton);
			this.getChildren().add(mySpriteCreator);
			mySpriteCreatorClass.onCreate(f -> {
				this.getChildren().remove(mySpriteCreator);
				this.getChildren().add(createSpriteButton);
			});

		});

		// container.getChildren().add(mySpriteCreator);
		// this.getChildren().add(mySpriteCreator);
		this.getChildren().add(createSpriteButton);
	}*/

	private ScrollPane createStatePane(VBox temp) {
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setPrefSize(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT);
		myStateSP_dummy.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		// VBox temp = new VBox();
		// temp.getChildren().add(new Text("states go here"));
		// temp.setPrefWidth(500);
		// temp.setPrefHeight(500);
		formatParametersVBox(temp);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
		// this.getChildren().add(myStateSP);
	}

	private void buttonInteraction() {
		// TODO
	}

	// private void apply() {
	//
	// for (Tab t: myParamTabs.getTabs()){
	// ScrollPane SP = (ScrollPane) t.getContent();
	// VBox paramsVbox = (VBox) SP.getContent();
	// for (Node node: paramsVbox.getChildren()){
	// FEParameter FEP = (FEParameter) node;
	// FEP.updateParameter();
	// myAEM.getSpriteParameterSidebarManager().apply();
	// }
	//
	// }
	//
	// }

	private void apply() {
		mySParameterTAI.apply();
		mySInventoryTAI.apply();
//		OwensActiosn.apply()
		myAEM.getSpriteParameterSidebarManager().apply();
	}

	private void addParameter() {
		List<String> choices = new ArrayList<>();
		choices.add("Boolean");
		choices.add("String");
		choices.add("Double");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Boolean", choices);
		dialog.setTitle("Add Parameter");
		dialog.setContentText("Choose parameter type:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(type -> createNewParameter(type));
	}

	private void createNewParameter(String type) {

	}

}
