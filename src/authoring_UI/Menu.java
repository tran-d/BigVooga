package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterI;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	

	private MapManager myMapManager;


	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private final static double MENU_WIDTH = 300;
	private final static double MENU_HEIGHT = 500;
	
	protected Menu(AuthoringEnvironmentManager AEM, Stage stage, MapManager mapManager) {
		myAEM = AEM;
		myStage = stage;
		myMapManager = mapManager;
		setUpMenu();
		
	}
	
	private void setErrorMessage(){
		myParameterErrorMessage = new TextArea("Either no active cells or active cells have different parameters");
//	System.out.println("Making error message");
	}
	
	
//	protected void displayParams() {
//		Map<String, ArrayList<SpriteParameterI>> paramMap = new HashMap<String, ArrayList<SpriteParameterI>>();
//		try {
//			paramMap = myAEM.getActiveCellParameters().getParameters();
//			for (Map.Entry<String, ArrayList<SpriteParameterI>> entry : paramMap.entrySet()) {
//				String category = entry.getKey();
//				ArrayList<SpriteParameterI> newParams = entry.getValue();
//				FEParameterFactory newFactory = new FEParameterFactory(newParams);
//				myStateSP.setContent(newFactory);
//			}
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			myStateSP.setContent(new TextArea("No params to show"));
//		}
//		
//		
//		this.setPrefWidth(MENU_WIDTH);
//	}
	
	private HashMap<String, ArrayList<SpriteParameterI>> getParametersOfActiveCells() throws Exception {
		return myAEM.getActiveCell().getParameters();
	}
	
	private void setUpMenu() {
		setErrorMessage();
		createButtons();
		createCategoryTabs();
		createSpriteTabs();

		createStatePane(new VBox());
		this.setPrefWidth(400);
//		createCategoryTabs();
//		createSpriteTabs();
		createSpriteCreator();
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
//		parameters.setOnSelectionChanged(e->{displayParams();});
//		parameters.setContent(myParamTabs);
		parameters.setContent(createParameterTab());
		Tab actions = new Tab("Actions");
		actions.setContent(new TextArea("actions go here"));
		Tab dialogue = new Tab("Dialogue");
		dialogue.setContent(new TextArea("dialogue goes here"));
		
		mySpriteTabs.getTabs().addAll(parameters, actions, dialogue);
		mySpriteTabs.setSide(Side.TOP);
		this.getChildren().add(mySpriteTabs);
	}
	
	private VBox createParameterTab() {
		myParamTabVBox = new VBox();
		Button applyButton = new Button();
		myParamTabVBox.getChildren().addAll(myParamTabs,  applyButton);
		
		setDefaultParameterVBox();
		
//		theHorizTabs = myParamTabs;
		
		applyButton.textProperty().setValue("Apply Button");
		applyButton.setOnAction(e -> {
			apply();
		});
		
//		myParamTabVBox.getChildren().addAll(theHorizTabs,  applyButton);
//		addParameterErrorMessage();
		return myParamTabVBox;
	}
	
	private void createCategoryTabs() {
		myParamTabs = new TabPane();
		myParamTabs.setSide(Side.RIGHT);
//		setDefaultParameterTab();
//		Tab newCategory = new Tab("Category");
//		newCategory.setContent(myStateSP);
//		newCategory.setClosable(false);
//		myParamTabs.getTabs().add(newCategory);
//		myParamTabs.setSide(Side.RIGHT);
		
		
//		this.getChildren().addAll(myParamTabs);	
//		return myParamTabs;
	}
	
	private void clearParameterTab() {
		myParamTabs.getTabs().clear();
	}
	
	private void setDefaultParameterVBox(){
		clearParameterTab();
		addParameterErrorMessage();
	}
	
	private void removeParameterErrorMessage(){
		if (myParamTabVBox.getChildren().contains(myParameterErrorMessage)) {
			myParamTabVBox.getChildren().remove(myParameterErrorMessage);
		}
	}
	
	private void addParameterErrorMessage(){
		if (!myParamTabVBox.getChildren().contains(myParameterErrorMessage)) {
			System.out.println(myParamTabVBox.getChildren().size());
			myParamTabVBox.getChildren().add(1, myParameterErrorMessage);
		}
	}
	
	public void updateParameterTab() {
		try{
	    clearParameterTab();
	    removeParameterErrorMessage();
		HashMap<String, ArrayList<SpriteParameterI>> params = getParametersOfActiveCells();
		System.out.println("Params size: "+params.size());
		System.out.println("Params keys: "+params.keySet());
		System.out.println("Params values: "+params.values());
//		if (params.size()>0)
		for (Map.Entry<String, ArrayList<SpriteParameterI>> entry : params.entrySet()) {
			
			String category = entry.getKey();
			System.out.println(category);
			ArrayList<SpriteParameterI> newParams = entry.getValue();
			FEParameterFactory newFactory = new FEParameterFactory(newParams);
			
			Tab newCategory = new Tab(category);
			newCategory.setContent(createStatePane(newFactory));
			newCategory.setClosable(false);
			myParamTabs.getTabs().add(newCategory);
			
	}
		
		} catch (Exception e){
			setDefaultParameterVBox();
		}
		this.setPrefWidth(MENU_WIDTH);
	}
	
	private void formatParametersVBox(VBox in) {
		in.setPrefWidth(300);
		in.setPrefHeight(500);
//		return in;
	}
	
	private void createSpriteCreator() {
		mySpriteCreator = (new SpriteCreator(myStage, myAEM, myMapManager)).getGrid();
		this.getChildren().add(mySpriteCreator);
		System.out.println("sprite creator added");
	}

	private ScrollPane createStatePane(VBox temp) {
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setPrefSize(MENU_WIDTH,MENU_HEIGHT);
		myStateSP_dummy.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//		VBox temp = new VBox();
//		temp.getChildren().add(new Text("states go here"));
//		temp.setPrefWidth(500);
//		temp.setPrefHeight(500);
		formatParametersVBox(temp);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
//		this.getChildren().add(myStateSP);
	}
	
	private void buttonInteraction() {
		
	}
	
	private void apply() {
		
	}
	

}
