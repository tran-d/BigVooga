package authoring_UI.dialogue;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.layout.VBox;

public class DialogueEditor {
	private VBox view;
	
	public DialogueEditor() {
		view = new VBox();
		view.setPrefWidth(WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		view.setPrefHeight(WelcomeScreen.HEIGHT);
	}
	
	
	
	public VBox getParent() {
		return view;
	}
}
