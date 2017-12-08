package authoring_UI;

import controller.welcomeScreen.SceneController;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import tools.DisplayLanguage;

public class Toolbar extends ToolBar {
	private static final String FILE_STRING = "File";
	private static final String LOAD_STRING = "Load";
	private static final String SAVE_STRING = "Save";
	private static final String IMPORT_STRING = "Import";
	private static final String EXIT_STRING = "Exit";
	private static final String VIEWS_STRING = "Views";
	private static final String ELEMENT_VIEWER_STRING = "ElementViewer";
	private static final String MAP_VIEWER_STRING = "MapViewer";
	private static final String SETTINGS_STRING = "Settings";
	
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
		MenuItem load = new MenuItem();
		load.textProperty().bind(DisplayLanguage.createStringBinding(LOAD_STRING));
		//TODO load.setOnAction(e -> ());
		MenuItem save = new MenuItem();
		save.textProperty().bind(DisplayLanguage.createStringBinding(SAVE_STRING));
		//save.setOnAction(e -> sceneController.saveWorlds());
		MenuItem importOption = new MenuItem();
		importOption.textProperty().bind(DisplayLanguage.createStringBinding(IMPORT_STRING));
		//TODO importOption.setOnAction(e -> ());
		MenuItem exit = new MenuItem();
		exit.textProperty().bind(DisplayLanguage.createStringBinding(EXIT_STRING));
		exit.setOnAction(e -> sceneController.switchScene(SceneController.WELCOME_SCREEN_KEY));

		fileOptions = new MenuButton(FILE_STRING, null, load, save, importOption, exit);
		fileOptions.textProperty().bind(DisplayLanguage.createStringBinding(FILE_STRING));
		
	}
	
	private void createViews() {
		MenuItem elementViewer = new MenuItem();
		elementViewer.textProperty().bind(DisplayLanguage.createStringBinding(ELEMENT_VIEWER_STRING));
		elementViewer.setOnAction(e -> new ElementViewer());
		
		MenuItem mapViewer = new MenuItem();
		mapViewer.textProperty().bind(DisplayLanguage.createStringBinding(MAP_VIEWER_STRING));
		//TODO language.setOnAction(e -> ());

		views = new MenuButton (VIEWS_STRING, null, elementViewer, mapViewer);
		views.textProperty().bind(DisplayLanguage.createStringBinding(VIEWS_STRING));
	}
	
	private void createSettings() {
		settings = new MenuButton (SETTINGS_STRING, null);
		settings.textProperty().bind(DisplayLanguage.createStringBinding(SETTINGS_STRING));
	}
}