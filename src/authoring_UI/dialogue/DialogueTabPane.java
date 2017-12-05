package authoring_UI.dialogue;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Class that holds all dialogue tabs
 * 
 * @author DavidTran
 *
 */
public class DialogueTabPane extends TabPane {

	private DefaultDialogueTab defaultTab;
	private UserDialogueTab userTab;

	public DialogueTabPane() {
		
		defaultTab = new DefaultDialogueTab("Default Dialogues");
		userTab = new UserDialogueTab("User Created Dialogues");
		this.getTabs().addAll(defaultTab, userTab);
		this.setPrefWidth((WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH)/2);
		this.setPrefHeight(WelcomeScreen.HEIGHT);
	}
	
	public void addDefaultDialogueButton(int number, Button btn) {
		defaultTab.addDialogue(number, btn);
	}
	
	public void addUserDialogueButton(int number, Button btn) {
		userTab.addDialogue(number, btn);
	}

}
