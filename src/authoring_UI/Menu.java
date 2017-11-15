package authoring_UI;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
	private Button myLoad;
	private Button mySave;
	private Button myNewMap;
	private MapManager myMap;
	
	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private final static String NEW_MAP = "New Map";
	
	protected Menu(MapManager map) {
		myMap = map;
		createButtons();
		buttonInteraction();
		//createTabs();

	}
	
	private void createButtons() {
		VBox myButtons = new VBox();
		myLoad = new Button(LOAD);
		mySave = new Button(SAVE);
		myNewMap = new Button(NEW_MAP);
		myButtons.getChildren().addAll(myLoad, mySave, myNewMap);
		
		this.getChildren().add(myButtons);
	}
	
	private void buttonInteraction() {
		
	}
	
	private void createTabs() {
		
	}

}
