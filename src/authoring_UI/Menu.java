package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterI;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

public class Menu extends VBox {
	private Button myLoad;
	private Button mySave;
	private AuthoringEnvironmentManager myAEM;
	private ScrollPane myStateSP;
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;

	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private static final String CONDITIONS = "Conditions";
	private static final String ACTIONS = "Actions";
	private final static double MENU_WIDTH = 400;
	private final static double MENU_HEIGHT = 500;
	
	protected Menu(AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		setUpMenu();

	}
	
	protected void displayParams() {
		Map<String, ArrayList<SpriteParameterI>> paramMap = new HashMap<String, ArrayList<SpriteParameterI>>();
		try {
			paramMap = myAEM.getActiveCellParameters().getParameters();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Map.Entry<String, ArrayList<SpriteParameterI>> entry : paramMap.entrySet()) {
			String category = entry.getKey();
			ArrayList<SpriteParameterI> newParams = entry.getValue();
			FEParameterFactory newFactory = new FEParameterFactory(newParams);
			myStateSP.setContent(newFactory);
		}
		
		this.setPrefWidth(MENU_WIDTH);
	}
	
	private void setUpMenu() {
		createButtons();
		createStatePane();
		createCategoryTabs();
		createSpriteTabs();
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
		parameters.setContent(myParamTabs);
		ActionConditionTab conditions = new ActionConditionTab(CONDITIONS);
		ActionConditionTab actions = new ActionConditionTab(ACTIONS);
		Tab dialogue = new Tab("Dialogue");
		dialogue.setContent(new TextArea("dialogue goes here"));
		
		mySpriteTabs.getTabs().addAll(parameters,conditions, actions, dialogue);
		mySpriteTabs.setSide(Side.TOP);
		this.getChildren().add(mySpriteTabs);
	}
	
	private void createCategoryTabs() {
		myParamTabs = new TabPane();
		Tab newCategory = new Tab("Category");
		newCategory.setContent(myStateSP);
		newCategory.setClosable(false);
		myParamTabs.getTabs().add(newCategory);
		myParamTabs.setSide(Side.RIGHT);
		
		this.getChildren().add(myParamTabs);		
	}


	private void createStatePane() {
		myStateSP = new ScrollPane();
		myStateSP.setPrefSize(MENU_WIDTH,MENU_HEIGHT);
		myStateSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		VBox temp = new VBox();
		temp.getChildren().add(new Text("states go here"));
		temp.setPrefWidth(500);
		temp.setPrefHeight(500);
		myStateSP.setContent(temp);
		
		this.getChildren().add(myStateSP);
	}
	
	private void buttonInteraction() {
		
	}
	

}
