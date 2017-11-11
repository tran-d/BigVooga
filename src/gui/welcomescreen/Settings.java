package gui.welcomescreen;

import javafx.stage.Stage;

public class Settings extends MenuOptionsTemplate{

	private static final int GEARS_WIDTH = 125;
	private static final int GEARS_HEIGHT = 125;
	private static final String GEARS_PATH = "Gears.gif";

	public Settings(Stage currentStage) {
		super(currentStage);
		createOptionScreen(GEARS_PATH, GEARS_WIDTH, GEARS_HEIGHT);
		
	}
	
}