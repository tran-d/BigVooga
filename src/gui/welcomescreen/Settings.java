package gui.welcomescreen;

import javafx.stage.Stage;

public class Settings extends MenuOptionsTemplate{

	private static final String GEARS_PATH = "Gears.gif";
	private static final int GEARS_WIDTH = 125;
	private static final int GEARS_HEIGHT = 125;
	private static final int HEADING_PADDING = 10;

	public Settings(Stage currentStage) {
		super(currentStage);
		createOptionScreen(GEARS_PATH, GEARS_WIDTH, GEARS_HEIGHT, HEADING_PADDING);
		
	}
	
}