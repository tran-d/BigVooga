package player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

import default_pkg.SceneController;
import engine.utilities.data.GameDataHandler;
import gui.welcomescreen.MenuOptionsTemplate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameSelector extends MenuOptionsTemplate {

	private static final String SELECTOR_PATH = "Selector.gif";
	private static final String FILE_PATH = Paths.get(".").toAbsolutePath().normalize().toString() + "/data/games";
	private static final int SELECTOR_WIDTH = 125;
	private static final int SELECTOR_HEIGHT = 125;
	private static final int HEADING_PADDING = 0;
	private static final int ENTRY_SPACING = 25;
	private static final int TREE_WIDTH = 955;
	private static final int TREE_HEIGHT = 800;

	private Stage stage;
	private SceneController sceneController;
	private ScrollPane contentPane;
	private VBox entriesBox;
	private String gameEntry;
	private TreeView<HBox> tree;
	private TreeItem<HBox> root;

	public GameSelector(Stage currentStage, SceneController currentSceneController) {
		super(currentStage, currentSceneController);
		createOptionScreen(SELECTOR_PATH, SELECTOR_WIDTH, SELECTOR_HEIGHT, HEADING_PADDING);

		stage = currentStage;
		sceneController = currentSceneController;
		contentPane = getScrollPane();
		entriesBox = new VBox();
		entriesBox.setAlignment(Pos.TOP_CENTER);

	}

	public void createGameSelector() {

		try (Stream<Path> filePathStream = Files.walk(Paths.get(FILE_PATH))) {

			createGameList();

			Set<String> gameSet = GameDataHandler.knownProjectsWithDateModified().keySet();
			for (String game : gameSet) {
				createGameEntry(game);
			}
			
			/*filePathStream.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					System.out.println(filePath.getFileName());
					createGameEntry(filePath.getFileName().toString().replaceAll("\\.[^.]*$", ""));
				}
			});*/
			entriesBox.getChildren().add(tree);
			contentPane.setContent(entriesBox);

		} catch (IOException e) {
			// show error
			e.printStackTrace();
		}
	}

	public HBox createTitleItem(String gameTitle) {
		// return new HBox(
		// GUITools.generateLabel(gameTitle, WelcomeScreen.MAIN_FONT,
		// WelcomeScreen.MAIN_COLOR, GAME_TITLE_FONT));
		HBox title = new HBox(new Label(gameTitle));
		title.setAlignment(Pos.BASELINE_CENTER);
		title.setId("title");
		return title;

	}

	private HBox createButtonPanel(String gameName) {

		HBox buttonPanel = new HBox(ENTRY_SPACING);
		Button newGame = createPlayGameButton("New Game", e -> handleNewGame(gameName));
		Button continueGame = createPlayGameButton("Continue Game", e -> handleContinueGame());

		buttonPanel.setAlignment(Pos.BASELINE_CENTER);
		buttonPanel.getChildren().addAll(newGame, continueGame);

		return buttonPanel;
	}

	private void handleNewGame(String theGame) {
		GameDisplay gameDisplay = sceneController.getDisplay();
		try {
			GameController gameController = new GameController (stage, theGame, gameDisplay);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sceneController.switchScene(SceneController.GAME_DISPLAY_KEY);
	}

	private void handleContinueGame() {
		// do stuff
	}

	private Button createPlayGameButton(String label, EventHandler<ActionEvent> handler) {
		Button btn = new Button(label);
		btn.setOnAction(handler);
		return btn;
	}

	@SuppressWarnings("unchecked")
	public void createGameEntry(String gameTitle) {

		TreeItem<HBox> title = new TreeItem<HBox>(createTitleItem(gameTitle));
		TreeItem<HBox> buttons = new TreeItem<HBox>(createButtonPanel(gameTitle));
		title.getChildren().addAll(buttons);
		title.setExpanded(false);

		addGameEntry(title);
	}

	public void createGameList() {
		root = new TreeItem<HBox>();
		root.setExpanded(true);
		tree = new TreeView<HBox>(root);
		tree.setPrefWidth(TREE_WIDTH);
		tree.setPrefHeight(TREE_HEIGHT);

		tree.getStylesheets().add(GameSelector.class.getResource("GameListStyle.css").toExternalForm());
	}

	public void addGameEntry(TreeItem<HBox> title) {
		root.getChildren().add(title);

	}

}
