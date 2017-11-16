package player;

import gui.welcomescreen.MenuOptionsTemplate;
import javafx.stage.Stage;

public class GameSelector extends MenuOptionsTemplate {

	private static final String SELECTOR_PATH = "Selector.gif";
	private static final int SELECTOR_WIDTH = 125;
	private static final int SELECTOR_HEIGHT = 125;
	private static final int HEADING_PADDING = 0;
	
	public GameSelector(Stage currentStage) {
		super(currentStage);
		createOptionScreen(SELECTOR_PATH, SELECTOR_WIDTH, SELECTOR_HEIGHT, HEADING_PADDING);
	}
	
	public void createGameSelector() {
		
	}
	
}
