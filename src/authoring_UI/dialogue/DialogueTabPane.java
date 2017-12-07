package authoring_UI.dialogue;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

/**
 * Class that holds all dialogue tabs
 * 
 * @author DavidTran
 *
 */
public class DialogueTabPane extends TabPane {

	private static final String DEFAULT = "DefaultDialogues";
	private static final String USER = "UserCreatedDialogues";
	private DefaultDialogueTab defaultTab;
	private UserDialogueTab userTab;

	public DialogueTabPane() {
		
		defaultTab = new DefaultDialogueTab(DEFAULT);
		userTab = new UserDialogueTab(DEFAULT);
		this.getTabs().addAll(defaultTab, userTab);
	}
	
	public void addDefaultDialogueButton(int number, Button btn) {
		defaultTab.addDialogue(number, btn);
	}
	
	public void addUserDialogueButton(int number, Button btn) {
		userTab.addDialogue(number, btn);
	}

}
