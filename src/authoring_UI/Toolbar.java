package authoring_UI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.welcomeScreen.SceneController;
import gui.welcomescreen.FileSelector;
import gui.welcomescreen.WelcomeScreen;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.DisplayLanguage;

public class Toolbar extends ToolBar {
	private static final String FILE_STRING = "File";
	private static final String LOAD_STRING = "Load";
	private static final String SAVE_STRING = "Save";
	private static final String IMPORT_STRING = "Import";
	private static final String EXPORT_STRING = "Export";
	private static final String EXIT_STRING = "Exit";
	private static final String VIEWS_STRING = "Views";
	private static final String ELEMENT_VIEWER_STRING = "ElementViewer";
	private static final String MAP_VIEWER_STRING = "MapViewer";
	private static final String SETTINGS_STRING = "Settings";
	public static final String FILE_SELECTOR = "File Selector";
	
	private MenuButton fileOptions;
	private MenuButton settings;
	private SceneController sceneController;
	private MenuButton views;
	private Stage myStage;

	public Toolbar(Stage stage, SceneController currentSceneController) {
		myStage = stage;
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
		MenuItem export = new MenuItem();
		export.textProperty().bind(DisplayLanguage.createStringBinding(EXPORT_STRING));
		export.setOnAction(e -> sceneController.exportToEngine());
		
		MenuItem load = new MenuItem();
		load.textProperty().bind(DisplayLanguage.createStringBinding(LOAD_STRING));
		load.setOnAction(e -> this.loadNewGame());
		
		MenuItem save = new MenuItem();
		save.textProperty().bind(DisplayLanguage.createStringBinding(SAVE_STRING));
		save.setOnAction(e -> sceneController.saveWorlds());
		
		Menu importOption = new Menu();
		importOption.textProperty().bind(DisplayLanguage.createStringBinding(IMPORT_STRING));	
		List<MenuItem> importItems = createImportOptions();
		for (MenuItem item : importItems) {
			importOption.getItems().add(item);
		}
		importOption.textProperty().bind(DisplayLanguage.createStringBinding(IMPORT_STRING));
		
		MenuItem exit = new MenuItem();
		exit.textProperty().bind(DisplayLanguage.createStringBinding(EXIT_STRING));
		exit.setOnAction(e -> sceneController.switchScene(SceneController.WELCOME_SCREEN_KEY));

		
		
		fileOptions = new MenuButton(FILE_STRING, null, export, load, save, importOption, exit);
		fileOptions.textProperty().bind(DisplayLanguage.createStringBinding(FILE_STRING));
	}
	
	private void loadNewGame() {
		myStage.close();
		SceneController newScene = new SceneController(new Stage());
		newScene.switchScene(FILE_SELECTOR);
	}
	
	private List<MenuItem> createImportOptions() {
		List<MenuItem> importItems = new ArrayList<MenuItem>();
		File f = new File("data/UserCreatedGames");
		File[] listOfFiles = f.listFiles();
		for (File file: listOfFiles) {
			if (file.getName().charAt(0) != '.') {
				MenuItem tempItem = new MenuItem(file.getName());
				tempItem.setOnAction(e -> sceneController.importWorlds(file.getName()));
				importItems.add(tempItem);
			}
		}
		return importItems;
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