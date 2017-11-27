package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterI;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
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
	private Button myLoad;
	private Button mySave;
	private AuthoringEnvironmentManager myAEM;
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;
	private VBox myParamTabVBox;
	private TextArea myParameterErrorMessage;

	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private final static double MENU_WIDTH = 400;
	private final static double MENU_HEIGHT = 400;
	
	protected Menu(AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		setUpMenu();
		
	}
	
	private void setErrorMessage(){
		myParameterErrorMessage = new TextArea("Either no active cells or active cells have different parameters");
//	System.out.println("Making error message");
	}

	
	private HashMap<String, ArrayList<SpriteParameterI>> getParametersOfActiveCells() throws Exception {
		return myAEM.getActiveCell().getParameters();
	}
	
	private void setUpMenu() {
		setErrorMessage();
		createButtons();
		createCategoryTabs();
		createSpriteTabs();

		createStatePane(new VBox());
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
		myParamTabVBox.getChildren().addAll(myParamTabs,  createApplyButton());
		
		setDefaultParameterVBox();

		return myParamTabVBox;
	}
	
	private HBox createAddAndApplyButtons() {
		Button applyButton = new Button();
		Button addParameterButton = new Button();
		
		applyButton.textProperty().setValue("Apply");
		applyButton.setOnAction(e -> {
			apply();
		});
		
		addParameterButton.textProperty().setValue("Add Parameter");
		addParameterButton.setOnAction(e -> {
			addParameter();
		});
		HBox buttonBox = new HBox(applyButton, addParameterButton);
		buttonBox.setSpacing(330);
		return buttonBox;
	
	}
	
	private Button createAddButton() {
		Button addParameterButton = new Button();
		addParameterButton.textProperty().setValue("Add Parameter");
		addParameterButton.setOnAction(e -> {
			addParameter();
		});
		
		return addParameterButton;
				
	}
	
	private Button createApplyButton() {
		Button applyButton = new Button();
		applyButton.textProperty().setValue("Apply");
		applyButton.setOnAction(e -> {
			apply();
		});
		
		return applyButton;
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
	
	protected void updateParameterTab() {
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
			VBox paramListAndButton = new VBox(createAddButton(), createStatePane(newFactory));
			paramListAndButton.setMinHeight(500);
			newCategory.setContent(paramListAndButton);
			newCategory.setClosable(false);
			myParamTabs.getTabs().add(newCategory);
			
	}
		
		} catch (Exception e){
			setDefaultParameterVBox();
		}
		this.setPrefWidth(MENU_WIDTH);
	}
	
	private void formatParametersVBox(VBox in) {
		in.setPrefWidth(400);
		in.setPrefHeight(700);
	}
	
	protected void addSpriteCreator(SpriteCreator spriteCreator) {
		GridPane spriteCreatorGrid = spriteCreator.getGrid();
		this.getChildren().add(spriteCreatorGrid);
		System.out.println("sprite creator added");
	}

	private ScrollPane createStatePane(VBox temp) {
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setMinSize(MENU_WIDTH,MENU_HEIGHT);
//		myStateSP_dummy.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		formatParametersVBox(temp);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
	}
	
	private void buttonInteraction() {
		
	}
	
	private void apply() {
		
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
