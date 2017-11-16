package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
	private Button myLoad;
	private Button mySave;
	private AuthoringEnvironmentManager myAEM;
	
	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	
	protected Menu(AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		createButtons();
		buttonInteraction();
		//createTabs();
		this.setPrefWidth(100);
		this.setPrefHeight(500);
	}
	
	public void displayParams() {
		try {
			this.getChildren().add(myAEM.getActiveCellParameters());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
