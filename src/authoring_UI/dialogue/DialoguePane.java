package authoring_UI.dialogue;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.layout.Pane;

public class DialoguePane {
	
	Pane pane;
	DialogueTabPane dView;
	DialogueEditor dEditor;
	
	public DialoguePane() {
		
		pane = new Pane();
		pane.setPrefWidth(WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		pane.setPrefHeight(WelcomeScreen.HEIGHT);
		dView = new DialogueTabPane();
		dEditor = new DialogueEditor();
		
		
	}
	
	public Pane getParent() {
		return pane;
	}
}
