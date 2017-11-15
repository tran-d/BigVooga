package authoring_UI;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
	private Button myLoad;
	private Button mySave;
	private Button myMap;
	private MapManager myManager;
	
	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private final static String NEW_MAP = "New Map";
	
	protected Menu(double width, double height) {
		createButtons();
		buttonInteraction();
		//createTabs();

	}
	
	private void createButtons() {
		HBox myButtons = new HBox();
		myLoad = new Button(LOAD);
		mySave = new Button(SAVE);
		myMap = new Button(NEW_MAP);
		myManager = new MapManager();
		myButtons.getChildren().addAll(myLoad, mySave, myMap);
		
		this.getChildren().add(myButtons);
	}
	
	private void buttonInteraction() {
		
	}
	
	private void createTabs() {
		
	}

}
