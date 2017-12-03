package authoring_UI;

import gui.welcomescreen.WelcomeScreen;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

/**
 * Class for creating new sprites
 * 
 * @author taekwhunchung
 *
 */

public class SpriteCreator {

	private TabPane myTabPane;


	public SpriteCreator() {
		myTabPane = new TabPane();
		Tab tab = new Tab();
		tab.setText("test");
		
		GridPane gp = new GridPane();
		gp.setPrefWidth(WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		gp.setPrefHeight(WelcomeScreen.HEIGHT);
		tab.setContent(gp);
		
		myTabPane.getTabs().add(tab);
//		myTabPane.setPrefWidth(WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
//		myTabPane.setPrefHeight(WelcomeScreen.HEIGHT);
	}

	public TabPane getParent() {
		return myTabPane;
	}
}
