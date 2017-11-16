package authoring_UI;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class Menu extends VBox {
	private Button myLoad;
	private Button mySave;
	private ScrollPane myStateSP;
	
	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private final static double MENU_WIDTH = 200;
	private final static double MENU_HEIGHT = 400;
	
	protected Menu() {
		createButtons();
		buttonInteraction();
		//createTabs();
		this.setPrefWidth(MENU_WIDTH);
	}
	
	private void createButtons() {
		VBox myButtons = new VBox();
		myLoad = new Button(LOAD);
		mySave = new Button(SAVE);
		createStatePane();
		
		myButtons.getChildren().addAll(myLoad, mySave, myStateSP);
		
		this.getChildren().add(myButtons);
	}

	private void createStatePane() {
		myStateSP = new ScrollPane();
		myStateSP.setPrefSize(MENU_WIDTH,MENU_HEIGHT);
		// we should get this vbox from authoring backend
		myStateSP.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		VBox temp = new VBox();
		temp.getChildren().add(new Text("states go here"));
		temp.setPrefWidth(500);
		temp.setPrefHeight(500);
		myStateSP.setContent(temp);
	}
	
	private void buttonInteraction() {
		
	}
	
	private void createTabs() {
		
	}

}
