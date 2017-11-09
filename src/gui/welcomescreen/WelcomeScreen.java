package gui.welcomescreen;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeScreen {

	public static final int WIDTH = 1000;
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
	private static final String TITLE_SIZE = INFINITY_HEIGHT-185 + "pt;";
	private static final int TITLE_POSITION_Y = 55;
	private static final int INFINITY_POSITION_X = 25;
	private static final int INFINITY_BORDER_WIDTH = 75;
	private static final String MOTTO_TEXT = "The Game Engine with Infinite Possibilities";
	private static final String MOTTO_COLOR = TITLE_COLOR;
	private static final String MOTTO_FONT = TITLE_FONT;
	private static final String MOTTO_SIZE = 16 + "pt;";
	private static final String PLAY_STATIC_PATH = "Play_Static.png";
	private static final String PLAY_PATH = "Play.gif";
	private static final int PLAY_WIDTH = 150;
	private static final int PLAY_HEIGHT = 150;
	private static final String CREATE_STATIC_PATH = "Build_Static.png";
	private static final String CREATE_PATH = "Build.gif";
	private static final int CREATE_WIDTH = 150;
	private static final int CREATE_HEIGHT = 150;
	private static final String LEARN_STATIC_PATH = "Learn_Static.png";
	private static final String LEARN_PATH = "Learn.gif";
	private static final int LEARN_WIDTH = 150;
	private static final int LEARN_HEIGHT = 150;
	private static final String SETTINGS_STATIC_PATH = "Settings_Static.png";
	private static final String SETTINGS_PATH = "Settings.gif";
	private static final int SETTINGS_WIDTH = 150;
	private static final int SETTINGS_HEIGHT = 150;
	private static final int OPTIONS_HORIZONTAL_GAP = 100;
	private static final int OPTIONS_BOTTOM_PADDING = 50;
	private static final String OPTIONS_BOX_BORDER_COLOR = TITLE_COLOR;
	private static final String PLAY_CAPTION = "Play";
	private static final String CREATE_CAPTION = "Create";
	private static final String LEARN_CAPTION = "Learn";
	private static final String SETTINGS_CAPTION = "Settings";
	private static final int TITLE_FADE_DURATION_MILLIS = 2500;
	private static final int OPTIONS_FADE_DURATION_MILLIS = 1500;
	private static final int TITLE_TRANSITION_DURATION_MILLIS = 1500;
	private static final int OPTIONS_TRANSITION_DURATION_MILLIS = 500;

	private Stage stage;
	private BorderPane rootPane;
	
	private VBox titleAndMotto;
	private HBox options;
	
	private Image playImage;
	private Image playStaticImage;
	private ImageView play;
	private Image createImage;
	private Image createStaticImage;
	private ImageView create;
	private Image learnImage;
	private Image learnStaticImage;
	private ImageView learn;
	private Image settingsImage;
	private Image settingsStaticImage;
	private ImageView settings;
	private HBox playHBox;
	private HBox createHBox;
	private HBox learnHBox;
	private HBox settingsHBox;
	private VBox playVBox;
	private VBox createVBox;
	private VBox learnVBox;
	private VBox settingsVBox;
	
	private Timeline timeline;
	private FadeTransition titleTransition;
	private FadeTransition mottoTransition;
	
	
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
		titleAndMotto = createTitleAndMotto();
		titleAndMotto.setOpacity(0);
		
		options = createWelcomeOptions();
		rootPane.setBottom(options);
		
		animationTimeline();
		
		rootPane.setTop(titleAndMotto);
		
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
		Image infinityImage = createImage(INFINITY_PATH, INFINITY_WIDTH, INFINITY_HEIGHT);
		ImageView infinity = createImageView(infinityImage);
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
	
	private Image createImage(String path, int width, int height) {
		Image image = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(path), width, height, true, true);
		return image;
	}
	private ImageView createImageView(Image image) {
		ImageView imageView = new ImageView(image);
		return imageView;
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
		
		createWelcomeImageViews();
		
		HBox optionBox = new HBox(OPTIONS_HORIZONTAL_GAP);
		optionBox.getChildren().addAll(playVBox, createVBox, learnVBox, settingsVBox);
		optionBox.setAlignment(Pos.CENTER);
		optionBox.setPadding(new Insets(0, 0, OPTIONS_BOTTOM_PADDING, 0));
		return optionBox;
	}
	
	private void createWelcomeImageViews() {
		
		createOptionImages();
		initializeOptionImageViews();
		createImageBorders();
		createWelcomeBoxes();
		handleHover();
		
	}
	
	private void createOptionImages(){
		
		playImage = createImage(PLAY_PATH, PLAY_WIDTH, PLAY_HEIGHT);
		createImage = createImage(CREATE_PATH, CREATE_WIDTH, CREATE_HEIGHT);
		learnImage = createImage(LEARN_PATH, LEARN_WIDTH, LEARN_HEIGHT);
		settingsImage = createImage(SETTINGS_PATH, SETTINGS_WIDTH, SETTINGS_HEIGHT);
		
		playStaticImage = createImage(PLAY_STATIC_PATH, PLAY_WIDTH, PLAY_HEIGHT);
		createStaticImage = createImage(CREATE_STATIC_PATH, CREATE_WIDTH, CREATE_HEIGHT);
		learnStaticImage = createImage(LEARN_STATIC_PATH, LEARN_WIDTH, LEARN_HEIGHT);
		settingsStaticImage = createImage(SETTINGS_STATIC_PATH, SETTINGS_WIDTH, SETTINGS_HEIGHT);
	}
	
	private void initializeOptionImageViews() {
		
		play = createImageView(playStaticImage);
		create = createImageView(createStaticImage);
		learn = createImageView(learnStaticImage);
		settings = createImageView(settingsStaticImage);
	}
	
	private void createImageBorders() {
		//Trying to use border generator but won't work with field variables
		borderGenerator();
	}
	private void borderGenerator() {
		playHBox = new HBox();
		playHBox.getChildren().add(play);
		playHBox.setStyle(styleBox(OPTIONS_BOX_BORDER_COLOR));
		
		createHBox = new HBox();
		createHBox.getChildren().add(create);
		createHBox.setStyle(styleBox(OPTIONS_BOX_BORDER_COLOR));
		
		learnHBox = new HBox();
		learnHBox.getChildren().add(learn);
		learnHBox.setStyle(styleBox(OPTIONS_BOX_BORDER_COLOR));
		
		settingsHBox = new HBox();
		settingsHBox.getChildren().add(settings);
		settingsHBox.setStyle(styleBox(OPTIONS_BOX_BORDER_COLOR));
		
	}
	
	private void createWelcomeBoxes() {
		playVBox = playBoxGenerator();
		playVBox.setOpacity(0);
		createVBox = createBoxGenerator();
		createVBox.setOpacity(0);
		learnVBox = learnBoxGenerator();
		learnVBox.setOpacity(0);
		settingsVBox = settingsBoxGenerator();
		settingsVBox.setOpacity(0);
	}
	
	private VBox playBoxGenerator() {
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		Label label = labelGenerator(PLAY_CAPTION, MOTTO_FONT, MOTTO_COLOR, MOTTO_SIZE);
		box.getChildren().addAll(label, playHBox);
		return box;
	}
	
	private VBox createBoxGenerator() {
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		Label label = labelGenerator(CREATE_CAPTION, MOTTO_FONT, MOTTO_COLOR, MOTTO_SIZE);
		box.getChildren().addAll(label, createHBox);
		return box;
	}
	
	private VBox learnBoxGenerator() {
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		Label label = labelGenerator(LEARN_CAPTION, MOTTO_FONT, MOTTO_COLOR, MOTTO_SIZE);
		box.getChildren().addAll(label, learnHBox);
		return box;
	}
	
	private VBox settingsBoxGenerator() {
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		Label label = labelGenerator(SETTINGS_CAPTION, MOTTO_FONT, MOTTO_COLOR, MOTTO_SIZE);
		box.getChildren().addAll(label, settingsHBox);
		return box;
	}
	
	/**
	 * Sets border color
	 * @param color
	 * @return
	 */
	private String styleBox(String color) {
		return "-fx-border-style: solid inside;" + 
               "-fx-border-width: 2;" + 
               "-fx-border-radius: 5;" + 
               "-fx-border-color: " + color + ";";
	}
	
	private void handleHover() {
		play.setOnMouseEntered(e -> playMouseEnter());
		play.setOnMouseExited(e -> playMouseExit());
		create.setOnMouseEntered(e -> createMouseEnter());
		create.setOnMouseExited(e -> createMouseExit());
		learn.setOnMouseEntered(e -> learnMouseEnter());
		learn.setOnMouseExited(e -> learnMouseExit());
		settings.setOnMouseEntered(e -> settingsMouseEnter());
		settings.setOnMouseExited(e -> settingsMouseExit());
	}
	
	private void playMouseEnter() {
		play.setImage(playImage);
	}
	
	private void playMouseExit() {
		play.setImage(playStaticImage);
	}
	
	private void createMouseEnter() {
		create.setImage(createImage);
	}
	
	private void createMouseExit() {
		create.setImage(createStaticImage);
	}
	
	private void learnMouseEnter() {
		learn.setImage(learnImage);
	}
	
	private void learnMouseExit() {
		learn.setImage(learnStaticImage);
	}
	
	private void settingsMouseEnter() {
		settings.setImage(settingsImage);
	}
	
	private void settingsMouseExit() {
		settings.setImage(settingsStaticImage);
	}
	
	private void animationTimeline() {
		timeline = new Timeline();
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.millis(0), e -> createTransition(titleAndMotto, TITLE_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS), e -> createTransition(playVBox, OPTIONS_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS + (OPTIONS_TRANSITION_DURATION_MILLIS*1)), e -> createTransition(createVBox, OPTIONS_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS + (OPTIONS_TRANSITION_DURATION_MILLIS*2)), e -> createTransition(learnVBox, OPTIONS_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS + (OPTIONS_TRANSITION_DURATION_MILLIS*3)), e -> createTransition(settingsVBox, OPTIONS_FADE_DURATION_MILLIS))
				);
		timeline.play();
	}
	
	private void createTransition(VBox box, int duration) {
		FadeTransition ft = new FadeTransition(Duration.millis(duration), box);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
	}
	
}
