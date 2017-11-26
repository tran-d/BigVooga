package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteParameterI;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends VBox {
	private Stage myStage;
	private Button myLoad;
	private Button mySave;
	private AuthoringEnvironmentManager myAEM;
	private ScrollPane myStateSP;
	private GridPane mySpriteCreator;
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;
	private VBox myParamTabVBox;
	private TextArea myParameterErrorMessage;
	private SpriteParameterTabsAndInfo mySPTAI;

	private MapManager myMapManager;

	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private final static double MENU_WIDTH = 400;
	private final static double MENU_HEIGHT = 500;

	protected Menu(AuthoringEnvironmentManager AEM, Stage stage, MapManager mapManager) {
		mySPTAI = new SpriteParameterTabsAndInfo();
		myAEM = AEM;
		myStage = stage;
		myMapManager = mapManager;
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

		// createStatePane(new VBox());

		 createSpriteCreator();
		this.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
	}

	private void createButtons() {
		HBox myButtons = new HBox();
		myLoad = new Button(LOAD);
		mySave = new Button(SAVE);
		myButtons.getChildren().addAll(myLoad, mySave);
		buttonInteraction();

		this.getChildren().add(myButtons);

	}

	private void createSpriteTabs() {
		mySpriteTabs = new TabPane();
		Tab parameters = new Tab("Parameters");
		// .setOnSelectionChanged(e->{displayParams();});
		// parameters.setContent(myParamTabs);
		parameters.setContent(createParameterTab());
		Tab actions = new Tab("Actions");
		actions.setContent(new TextArea("actions go here"));
		Tab dialogue = new Tab("Dialogue");
		dialogue.setContent(new TextArea("dialogue goes here"));

		mySpriteTabs.getTabs().addAll(parameters, actions, dialogue);
		mySpriteTabs.setSide(Side.TOP);
		// this.getChildren().add(mySpriteTabs);
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
		myParamTabs.getTabs().clear();
	}

	private void removeParameterTab() {
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

	public void updateParameterTab() {

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

	private void createSpriteCreator() {
		// GridPane container = new GridPane();
		// ColumnConstraints cc = new ColumnConstraints();
		// cc.setPercentWidth(100);
		// RowConstraints rc = new RowConstraints();
		// rc.setPercentHeight(50);
		// container.getColumnConstraints().add(cc);
		// container.getRowConstraints().add(rc);

		
		Button createSpriteButton = new Button("Create Sprite");
		
		createSpriteButton.setOnAction(e -> {
			System.out.println("Button pressed");
			SpriteCreator mySpriteCreatorClass = new SpriteCreator(myStage, myAEM, myMapManager);
			mySpriteCreator = mySpriteCreatorClass.getGrid();
				this.getChildren().remove(createSpriteButton);
				this.getChildren().add(mySpriteCreator);
				mySpriteCreatorClass.onCreate(f -> {
					this.getChildren().remove(mySpriteCreator);
					this.getChildren().add(createSpriteButton);
				});
			
		});

//		container.getChildren().add(mySpriteCreator);
		// this.getChildren().add(mySpriteCreator);
		this.getChildren().add(createSpriteButton);
		System.out.println("sprite creator added");
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

}
