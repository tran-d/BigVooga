package gui.welcomescreen;

import java.io.File;

import authoring_UI.MainAuthoringGUI;
import controller.welcomeScreen.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FileSelector extends MenuOptionsTemplate {

	private static final String SQUARICLE_PATH = "Squaricle.gif";
	private static final int SQUARICLE_WIDTH = 125;
	private static final int SQUARICLE_HEIGHT = 125;
	private static final String FILE_SELECTOR_CSS = "FileSelector.css";
	
	private Stage stage;
	private BorderPane rootPane;
	private SceneController sceneController;
	private ScrollPane contentPane = new ScrollPane();
	private Scene scene;
	private TextField textField;
	private MainAuthoringGUI myAuthoringGUI;
	
	
	public FileSelector(Stage currentStage, SceneController currentSceneController) {
		super(currentStage, currentSceneController);
		createOptionScreen(SQUARICLE_PATH, SQUARICLE_WIDTH, SQUARICLE_HEIGHT, Settings.HEADING_PADDING, 0, WelcomeScreen.HEIGHT-200);
		stage = currentStage;
		scene = stage.getScene();
		sceneController = currentSceneController;
	}
	
	public void createFileSelector() {
		rootPane = getBorderPane();
		HBox newGame = createNewGame();
		rootPane.setCenter(newGame);
		
		VBox contentBox = createFiles();
		
		contentPane = getScrollPane();
		contentPane.setContent(contentBox);		
	}
	
	private HBox createNewGame() {
		HBox newGameBox = new HBox(10);
		newGameBox.setAlignment(Pos.CENTER);
		newGameBox.setPadding(new Insets(0, 0, 10, 0));
		Label newGameLabel = new Label("Create New Game:");
		newGameLabel.setStyle("-fx-text-fill: #47BDFF;" + 
							  "-fx-font-size: 14px");
		textField = new TextField();
		textField.setPromptText("Project name");
		Button submit = createSubmitButton();
		newGameBox.getChildren().addAll(newGameLabel, textField, submit);
		return newGameBox;
	}
	
	private Button createSubmitButton() {
		Button button = new Button("Submit");
		button.setId("submit");
		button.getStylesheets().add(FileSelector.class.getResource(FILE_SELECTOR_CSS).toExternalForm());
		button.setOnAction(e -> checkInput());
		return button;
	}
	
	private void checkInput() {
		if (!textField.getText().isEmpty()) {
			switchScene(textField.getText());
		}
	}
	
	private VBox createFiles() {
		VBox fileBox = new VBox();
		File f = new File("data/UserCreatedGames");
		File[] listOfFiles = f.listFiles();
		for (File file: listOfFiles) {
			if (file.getName().charAt(0) != '.') {
				Button fileButton = createFileButton(file.getName());
				fileBox.getChildren().add(fileButton);
			}
		}
		return fileBox;
	}
	
	private Button createFileButton(String fileName) {
		Button fileButton = new Button(fileName);
		fileButton.setPrefWidth(WelcomeScreen.WIDTH-20);
		fileButton.getStylesheets().add(FileSelector.class.getResource(FILE_SELECTOR_CSS).toExternalForm());
		fileButton.setOnAction(e -> switchScene(fileName));
		return fileButton;
	}
	
	private void switchScene(String fileName) {
		myAuthoringGUI = new MainAuthoringGUI(stage, sceneController, fileName);
		myAuthoringGUI.createAuthoringGUI();
		stage.setScene(myAuthoringGUI.getScene());
		stage.centerOnScreen();
	}
	
	public void saveWorlds() {
		myAuthoringGUI.saveWorlds();
	}
}