package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Optional;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteParameterI;
import authoring_actionconditions.ActionConditionTab;
import authoring_actionconditions.ControllerActionCheckBoxVBox;
import default_pkg.SceneController;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Menu extends VBox {
	
	private SceneController sceneController;
	private Button myBack;
	private Button myLoad;
	private Button mySave;
	private AuthoringEnvironmentManager myAEM;
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;
	private VBox myParamTabVBox;
	private TextArea myParameterErrorMessage;
	private SpriteParameterTabsAndInfo mySPTAI;
	private MapManager myMapManager;
	private static final String LOAD = "Load";
	private static final String SAVE = "Save";
	private static final String ACTIONCONDITIONTITLES_PATH = "TextResources/ConditionActionTitles";
	private static final double MENU_WIDTH = 435;
	private static final double MENU_HEIGHT = 500;
	
	public static final ResourceBundle conditionActionTitles = ResourceBundle.getBundle(ACTIONCONDITIONTITLES_PATH);
	
	protected Menu(AuthoringEnvironmentManager AEM, MapManager myManager, SceneController currentSceneController) {
		mySPTAI = new SpriteParameterTabsAndInfo();
		myAEM = AEM;
		myMapManager = myManager;
		sceneController = currentSceneController;
		setUpMenu();

	}

	private void setErrorMessage() {
		myParameterErrorMessage = new TextArea("Either no active cells or active cells have different parameters");
		// System.out.println("Making error message");
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
		System.out.println("MYAEMACTIVE: " + myAEM.getActiveCell());
		return myAEM.getActiveCell();
	}

	private void setUpMenu() {
		setErrorMessage();
		createButtons();
		createCategoryTabs();
		createSpriteTabs();
		createSpriteCreator();
		createOverviewWindow();
		this.setPrefSize(MENU_WIDTH, MENU_HEIGHT);

		// createStatePane(new VBox());
	}

	private void createButtons() {
		HBox myButtons = new HBox();
		myBack = createBack();
		myLoad = new Button(LOAD);
		mySave = new Button(SAVE);
		myButtons.getChildren().addAll(myBack, myLoad, mySave);
		buttonInteraction();

		this.getChildren().add(myButtons);

	}
	
	private Button createBack() {
		Button back = new Button("Back");
		back.setOnMouseClicked(e -> sceneController.switchScene(SceneController.WELCOME_SCREEN_KEY));
		return back;
	}
	
	private void createActionConditionTabs() {
		ActionConditionTab conditions = new ActionConditionTab(conditionActionTitles.getString("ConditionsTabTitle"));
		ActionConditionTab actions = new ActionConditionTab(conditionActionTitles.getString("ActionsTabTitle"));
		ControllerActionCheckBoxVBox controllerActionCheckBoxVBox= new ControllerActionCheckBoxVBox(conditions,actions);
		mySpriteTabs.getTabs().addAll(conditions,actions);
	}

	private void createSpriteTabs() {
		mySpriteTabs = new TabPane();
		Tab parameters = new Tab("Parameters");
		// .setOnSelectionChanged(e->{displayParams();});
		// parameters.setContent(myParamTabs);
		parameters.setContent(createParameterTab());
		Tab dialogue = new Tab("Dialogue");
		dialogue.setContent(new TextArea("dialogue goes here"));
		
		mySpriteTabs.getTabs().addAll(parameters, dialogue);
		mySpriteTabs.setSide(Side.TOP);
		createActionConditionTabs();
		this.getChildren().add(mySpriteTabs);
	}

	private VBox createParameterTab() {
		myParamTabVBox = new VBox();
		Button applyButton = new Button();
		myParamTabVBox.getChildren().addAll(myParamTabs, applyButton);

		setDefaultErrorNoSpriteTabPane();

		// theHorizTabs = myParamTabs;

		applyButton.textProperty().setValue("Apply Button");
		applyButton.setOnAction(e -> {
			apply();
		});

		// myParamTabVBox.getChildren().addAll(theHorizTabs, applyButton);
		// addParameterErrorMessage();
		return myParamTabVBox;
	}

	private void createCategoryTabs() {
		// mySPTAI.createCategoryTabs();
		myParamTabs = mySPTAI.getTabPane();

		// myParamTabs = new TabPane();
		// myParamTabs.setSide(Side.RIGHT);
		// myParamTabs.setPrefHeight(500);
		// myParamTabs.setPrefWidth(400);

	}

	private void clearParameterTab() {
//		myParamTabs.getTabs().clear();
		mySPTAI.clearTabPane();
	}

	protected void removeParameterTab() {
		if (this.getChildren().contains(mySpriteTabs)) {
			this.getChildren().remove(mySpriteTabs);
		}
	}

	private void addParameterTab() {
		if (!this.getChildren().contains(mySpriteTabs)) {
			this.getChildren().add(mySpriteTabs);
		}
	}

	private void setDefaultErrorNoSpriteTabPane() {
		clearParameterTab();
		removeParameterTab();
		addParameterErrorMessage();
	}

	private void removeParameterErrorMessage() {
		if (this.getChildren().contains(myParameterErrorMessage)) {
			this.getChildren().remove(myParameterErrorMessage);
		}
	}

	private void addParameterErrorMessage() {
		if (!this.getChildren().contains(myParameterErrorMessage)) {
			// System.out.println(myParamTabVBox.getChildren().size());
			int numChildren = this.getChildren().size();
			this.getChildren().add(numChildren - 1, myParameterErrorMessage);
		}
	}

	protected void updateParameterTab() {

		System.out.println("Updating....");
		try {
			clearParameterTab();
			removeParameterErrorMessage();
			mySPTAI.create(getActiveCell());
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
			addParameterTab();
		} catch (Exception e) {
			// throw new RuntimeException();
			setDefaultErrorNoSpriteTabPane();

		}
		this.setPrefWidth(MENU_WIDTH);
	}

	private void formatParametersVBox(VBox in) {
		in.setPrefWidth(500);
		in.setPrefHeight(500);
		// return in;
	}

	private void createOverviewWindow() {
		Button openOverView = new Button("Open Overview");
		openOverView.setOnAction(e -> {
			System.out.println("Overview button pressed");
			OverviewWindow overviewWindow = new OverviewWindow();
			overviewWindow.getStage().show();
		});
		this.getChildren().add(openOverView);
	}

	private void createSpriteCreator() {

		Button createSpriteButton = new Button("Create Sprite");

		createSpriteButton.setOnAction(e -> {
			System.out.println("Button pressed");
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
	}

	private ScrollPane createStatePane(VBox temp) {
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
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
		mySPTAI.apply();
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
