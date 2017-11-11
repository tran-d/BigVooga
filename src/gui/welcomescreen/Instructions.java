package gui.welcomescreen;

import javafx.stage.Stage;

public class Instructions extends MenuOptionsTemplate {

	private static final int CRYSTAL_WIDTH = 125;
	private static final int CRYSTAL_HEIGHT = 125;
	private static final String CRYSTAL_PATH = "Crystal.gif";

	public Instructions(Stage currentStage) {
		super(currentStage);
		createOptionScreen(CRYSTAL_PATH, CRYSTAL_WIDTH, CRYSTAL_HEIGHT);
		
	}
}
