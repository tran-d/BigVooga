package gui.welcomescreen;

import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelcomeScreen {

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;
	private static final String STAGE_TITLE = "VOOGA";
	private static final String WELCOME_BACKGROUND_COLOR = "#001E32;";
	private static final String TITLE_COLOR = "#47BDFF;";
	private static final String TITLE_FONT = "Segoe UI;";
	private static final String LEFT_SEGMENT_TITLE = "V";
	private static final String RIGHT_SEGMENT_TITLE = "GA";
	private static final String INFINITY_PATH = "Infinity.gif";
	private static final int INFINITY_WIDTH = 400;
	private static final int INFINITY_HEIGHT = 300;
	private static final String TITLE_SIZE = INFINITY_HEIGHT-175 + "pt;";
	private static final int TITLE_POSITION_Y = 25;
	private static final int INFINITY_POSITION_X = 25;
	private static final int INFINITY_BORDER_WIDTH = 75;
	private static final String MOTTO_TEXT = "The Game Engine with Infinite Possibilities";
	private static final String MOTTO_COLOR = TITLE_COLOR;
	private static final String MOTTO_FONT = TITLE_FONT;
	private static final String MOTTO_SIZE = 16 + "pt;";
	private static final String PLAY_STATIC_PATH = "Play_Static.png";
	private static final int PLAY_STATIC_WIDTH = 200;
	private static final int PLAY_STATIC_HEIGHT = 200;
	private static final String PLAY_PATH = "Play.gif";
	private static final int PLAY_WIDTH = 200;
	private static final int PLAY_HEIGHT = 200;
	private static final String BUILD_STATIC_PATH = "Build_Static.png";
	private static final int BUILD_STATIC_WIDTH = 200;
	private static final int BUILD_STATIC_HEIGHT = 200;
	private static final String BUILD_PATH = "Build.gif";
	private static final int BUILD_WIDTH = 200;
	private static final int BUILD_HEIGHT = 200;
	private static final String LEARN_STATIC_PATH = "Learn_Static.png";
	private static final int LEARN_STATIC_WIDTH = 200;
	private static final int LEARN_STATIC_HEIGHT = 200;
	private static final String LEARN_PATH = "Learn.gif";
	private static final int LEARN_WIDTH = 200;
	private static final int LEARN_HEIGHT = 200;
	private static final String SETTINGS_STATIC_PATH = "Settings_Static.png";
	private static final int SETTINGS_STATIC_WIDTH = 200;
	private static final int SETTINGS_STATIC_HEIGHT = 200;
	private static final String SETTINGS_PATH = "Settings.gif";
	private static final int SETTINGS_WIDTH = 200;
	private static final int SETTINGS_HEIGHT = 200;

	private Stage stage;
	private BorderPane rootPane;
	
	public WelcomeScreen(Stage currentStage) {
		
		stage = currentStage;
		rootPane = new BorderPane();
		Scene scene = new Scene(rootPane, WIDTH, HEIGHT);

		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle(STAGE_TITLE);
		stage.setResizable(false);
		stage.show();
	}
	
	public void createWelcomeScreen() {
		
		rootPane.setStyle("-fx-background-color: " + WELCOME_BACKGROUND_COLOR);
		VBox titleAndMotto = createTitleAndMotto();
		rootPane.setTop(titleAndMotto);
		
		HBox options = createWelcomeOptions();
		rootPane.setBottom(options);		
	}
	
	private VBox createTitleAndMotto() {
		HBox titleBox = createTitle();
		Label motto = createMotto();
		VBox titleAndMotto = new VBox();
		titleAndMotto.getChildren().addAll(titleBox, motto);
		titleAndMotto.setAlignment(Pos.CENTER);
		return titleAndMotto;
	}
	
	private HBox createTitle() {
		Image infinityImage = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(INFINITY_PATH), INFINITY_WIDTH, INFINITY_HEIGHT, true, true);
		ImageView infinity = new ImageView(infinityImage);
		Label leftTitle = labelGenerator(LEFT_SEGMENT_TITLE, TITLE_FONT, TITLE_COLOR, TITLE_SIZE);
		Label rightTitle = labelGenerator(RIGHT_SEGMENT_TITLE, TITLE_FONT, TITLE_COLOR, TITLE_SIZE);
		
		Pane titlePane = new Pane();
		titlePane.getChildren().addAll(leftTitle, infinity, rightTitle);
		
		positionTitle(leftTitle, infinity, rightTitle);
		
		HBox titleBox = new HBox();
		titleBox.getChildren().add(titlePane);
		titleBox.setAlignment(Pos.CENTER);
		return titleBox;
	}
	
	private Label labelGenerator(String labelText, String font, String color, String size) {
		Label label = new Label(labelText);
		label.setStyle(cssGenerator(font, color, size));
		return label;
	}
	
	private String cssGenerator(String font, String color, String size) {
		return "-fx-font-family: " + font +
				"-fx-text-fill: " + color +  
				"-fx-font-size: " + size  
				;
	}
	
	private void positionTitle(Label firstSegment, ImageView image, Label secondSegment) {
		firstSegment.toFront();
		firstSegment.setLayoutY(TITLE_POSITION_Y);
		image.setLayoutX(INFINITY_POSITION_X);
		secondSegment.setLayoutX(INFINITY_POSITION_X + INFINITY_WIDTH - INFINITY_BORDER_WIDTH);
		secondSegment.setLayoutY(TITLE_POSITION_Y);
	}
	
	private Label createMotto() {
		Label motto = labelGenerator(MOTTO_TEXT, MOTTO_FONT, MOTTO_COLOR, MOTTO_SIZE);
		return motto;
	}
	
	private HBox createWelcomeOptions() {
		
		Image playImage = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(PLAY_PATH), PLAY_WIDTH, PLAY_HEIGHT, true, true);
		ImageView play = new ImageView(playImage);
		
		Image buildImage = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(BUILD_PATH), BUILD_WIDTH, BUILD_HEIGHT, true, true);
		ImageView build = new ImageView(buildImage);
		
		Image learnImage = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(LEARN_PATH), LEARN_WIDTH, LEARN_HEIGHT, true, true);
		ImageView learn = new ImageView(learnImage);
		
		Image settingsImage = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(SETTINGS_PATH), SETTINGS_WIDTH, SETTINGS_HEIGHT, true, true);
		ImageView settings = new ImageView(settingsImage);
		
		HBox optionBox = new HBox(50);
		optionBox.getChildren().addAll(play, build, learn, settings);
		optionBox.setAlignment(Pos.CENTER);
		return optionBox;
	}
	
	//private VBox optionBoxGenerator() {
		
	//}
	
	private void createTransition() {
		
	}
	
}
