package authoring_UI;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
	private HBox buttons;
	
	private final static String LOAD = "Load";
	private final static String SAVE = "Save";
	private final static String NEW_MAP = "New Map";
	
	public Menu(double width, double height) {
		createButtons();
		//createTabs();
		this.getChildren().add(buttons);
	}
	
	private void createButtons() {
		buttons = new HBox();
		Button load = new Button(LOAD);
		Button save = new Button(SAVE);
		Button map = new Button(NEW_MAP);
		buttons.getChildren().addAll(load, save, map);
	}
	
	private void createTabs() {
		
	}

}
