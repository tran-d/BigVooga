package authoring_UI.dialogue;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.layout.HBox;

/**
 * Class that represents the pane containing all dialogue authoring components
 * 
 * @author DavidTran
 *
 */
public class DialogueManager {
	
	private HBox pane;
	private DialogueTabPane dView;
	private DialogueEditor dEditor;
	
	public DialogueManager() {
		
		pane = new HBox();
		pane.setPrefWidth(WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		pane.setPrefHeight(WelcomeScreen.HEIGHT);
		dView = new DialogueTabPane();
		dEditor = new DialogueEditor();
		
		pane.getChildren().addAll(dView, dEditor.getParent());
		
		
	}
	
	public HBox getParent() {
		return pane;
	}
}
