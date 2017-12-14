package authoring_UI.displayable;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class DisplayableTabPane extends TabPane {

	public DisplayableTabPane() {
		
	}
	
	public void addDefaultDisplayableButton(int number, Button button) {
		// do nothing
	}
	
	public void addUserDisplayableButton(int number, Button btn) {
		// do nothing
	}

	public void removeUserDisplayableButton(int id) {
		// do nothing
	}
	
	public int getButtonIndex(Button btn) {
		return 0;
	}
}
