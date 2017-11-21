package player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import gui.welcomescreen.GUITools;
import gui.welcomescreen.MenuOptionsTemplate;
import gui.welcomescreen.WelcomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameSelector extends MenuOptionsTemplate {

	private static final String SELECTOR_PATH = "Selector.gif";
	private static final String FILE_PATH = Paths.get(".").toAbsolutePath().normalize().toString() + "/data/games";
	private static final int SELECTOR_WIDTH = 125;
	private static final int SELECTOR_HEIGHT = 125;
	private static final int HEADING_PADDING = 0;
	private static final String GAME_TITLE_FONT = 50 + "pt;";
	private static final int ENTRY_SPACING = 100;
	private static final int TREE_ITEM_WIDTH = 1000;
	private static final int TREE_ITEM_HEIGHT = 150;

	private ScrollPane contentPane;
	private VBox entriesBox;

	public GameSelector(Stage currentStage) {
		super(currentStage);
		createOptionScreen(SELECTOR_PATH, SELECTOR_WIDTH, SELECTOR_HEIGHT, HEADING_PADDING);

		contentPane = getScrollPane();
		entriesBox = new VBox();

	}

	public void createGameSelector() {

		try (Stream<Path> filePathStream = Files.walk(Paths.get(FILE_PATH))) {
			filePathStream.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					System.out.println(filePath.getFileName());
					TreeView<HBox> tree = createGameEntry(
							filePath.getFileName().toString().replaceAll("\\.[^.]*$", ""));
					entriesBox.getChildren().add(tree);
				}
			});

			contentPane.setContent(entriesBox);

		} catch (IOException e) {
			// show error
			e.printStackTrace();
		}
	}

	public HBox createTitleItem(String gameTitle) {
		return new HBox(
				GUITools.generateLabel(gameTitle, WelcomeScreen.MAIN_FONT, WelcomeScreen.MAIN_COLOR, GAME_TITLE_FONT));
	}

	private HBox createButtonPanel() {

		HBox buttonPanel = new HBox(ENTRY_SPACING);
		Button newGame = createNewGameButton(e -> handleNewGame());
		Button continueGame = createContinueGameButton(e -> handleContinueGame());

		buttonPanel.setAlignment(Pos.CENTER_LEFT);
		buttonPanel.getChildren().addAll(newGame, continueGame);

		return buttonPanel;
	}

	private void handleNewGame() {
		// do stuff
	}

	private void handleContinueGame() {
		// do stuff
	}

	private Button createNewGameButton(EventHandler<ActionEvent> handler) {
		Button btn = new Button("New Game");
		btn.setOnAction(handler);
		return btn;
	}

	private Button createContinueGameButton(EventHandler<ActionEvent> handler) {
		Button btn = new Button("Continue");
		btn.setOnAction(handler);
		return btn;
	}

	@SuppressWarnings("unchecked")
	public TreeView<HBox> createGameEntry(String gameTitle) {

		TreeItem<HBox> rootItem = new TreeItem<HBox>(createTitleItem(gameTitle));
		TreeItem<HBox> buttons = new TreeItem<HBox>(createButtonPanel());
		rootItem.getChildren().addAll(buttons);

		TreeView<HBox> tree = new TreeView<HBox>(rootItem);
		tree.setPrefWidth(TREE_ITEM_WIDTH);
		tree.setPrefHeight(TREE_ITEM_HEIGHT);
		tree.setStyle(WelcomeScreen.SET_BACKGROUND_COLOR + WelcomeScreen.BACKGROUND_COLOR);
//		tree.setOnMouseClicked(e -> rootItem.setExpanded(tree.getExpandedItemCount() == 1));
		
		return tree;
	}

}
