package authoring_UI;

import controller.welcomeScreen.SceneController;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
	
	private MenuButton fileOptions;
	private MenuButton settings;
	private SceneController sceneController;
	private MenuButton views;

	public Toolbar(SceneController currentSceneController) {
		
		sceneController = currentSceneController;
		
		createFileOptions();
		createViews();
		createSettings();
		
		this.getItems().addAll(
				fileOptions,
				views,
				settings
				);
		
	}
	
	private void createFileOptions() {
		
		MenuItem save = new MenuItem("Save");
		//TODO save.setOnAction(e -> ());
		MenuItem importOption = new MenuItem("Import");
		//TODO save.setOnAction(e -> ());
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> sceneController.switchScene(SceneController.WELCOME_SCREEN_KEY));
		
		fileOptions = new MenuButton ("File", null, save, importOption, exit);
		
	}
	
	private void createViews() {
		
		MenuItem objectView = new MenuItem("Object View");
		//TODO language.setOnAction(e -> ());

		views = new MenuButton ("Views", null, objectView);
		
	}
	
	private void createSettings() {
		
		MenuItem language = new MenuItem("Yo");
		//TODO language.setOnAction(e -> ());

		settings = new MenuButton ("Settings", null, language);
		
	}
}
