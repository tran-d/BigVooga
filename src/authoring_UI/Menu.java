package authoring_UI;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
	private Button myLoad;
	private Button mySave;
	
	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	
	protected Menu() {
		createButtons();
		buttonInteraction();
		//createTabs();
		this.setPrefWidth(80);
	}
	
	private void createButtons() {
		VBox myButtons = new VBox();
		myLoad = new Button(LOAD);
		mySave = new Button(SAVE);
		myButtons.getChildren().addAll(myLoad, mySave);
		
		this.getChildren().add(myButtons);
	}
	
	private void buttonInteraction() {
		
	}
	
	private void createTabs() {
		
	}

}
