package authoring_UI.dialogue;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class DialogueTabPane {
	TabPane tabPane;
	Tab defaultTab;
	Tab userTab;
	

	public DialogueTabPane() {
		tabPane = new TabPane();
		
		defaultTab = new DefaultDialogueTab("Default Dialogues");
		userTab = new UserDialogueTab("User Created Dialogues");
		tabPane.getTabs().addAll(defaultTab, userTab);
	}

	public TabPane getParent() {
		return tabPane;
	}
}
