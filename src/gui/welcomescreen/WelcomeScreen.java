package gui.welcomescreen;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeScreen {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	public static final String SET_BACKGROUND_COLOR = "-fx-background-color: ";
	public static final String BACKGROUND_COLOR = "#001E32;";
	public static final String MAIN_TEXT_COLOR = "#47BDFF;";
	public static final String MAIN_FONT = "Segoe UI;";
	private static final String STAGE_TITLE = "VOOGA";
	private static final String LEFT_SEGMENT_TITLE = "V";
	private static final String RIGHT_SEGMENT_TITLE = "GA";
	private static final String INFINITY_PATH = "Infinity.gif";
	private static final int INFINITY_WIDTH = 400;
	private static final int INFINITY_HEIGHT = 300;
	private static final String TITLE_SIZE = INFINITY_HEIGHT-185 + "pt;";
	private static final int TITLE_POSITION_Y_WINDOWS = 35;
	private static final int TITLE_POSITION_Y_MAC = 55;
	//private static final int TITLE_POSITION_Y = 37;
	//private static final int TITLE_POSITION_Y = 55;
	private static final int INFINITY_POSITION_X = 20;
	//private static final int INFINITY_POSITION_X_WINDOWS = 20;
	//private static final int INFINITY_POSITION_Y_WINDOWS = 20;
	//private static final int INFINITY_POSITION_X_MAC = 15;
	//private static final int INFINITY_POSITION_Y_MAC = 55;
	private static final int INFINITY_BORDER_WIDTH = 75;
	private static final String MOTTO_TEXT = "The Game Engine with Infinite Possibilities";
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
	private static final String OPTIONS_BOX_BORDER_COLOR = MAIN_TEXT_COLOR;
	private static final String OPTION_SIZE = MOTTO_SIZE;
	private static final String PLAY_CAPTION = "Play";
	private static final String CREATE_CAPTION = "Create";
	private static final String LEARN_CAPTION = "Learn";
	private static final String SETTINGS_CAPTION = "Settings";
	private static final int TITLE_FADE_DURATION_MILLIS = 1500;
	private static final int OPTIONS_FADE_DURATION_MILLIS = 1000;
	private static final int TITLE_TRANSITION_DURATION_MILLIS = 1500;
	private static final int OPTIONS_TRANSITION_DURATION_MILLIS = 500;
	private static final String OS = System.getProperty("os.name").toLowerCase();

	private Stage stage;
	private BorderPane rootPane;
	private boolean clickEnabled = false;
	
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
	private VBox playVBox;
	private VBox createVBox;
	private VBox learnVBox;
	private VBox settingsVBox;	
	
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
		
		rootPane.setStyle(SET_BACKGROUND_COLOR + BACKGROUND_COLOR);
		titleAndMotto = createTitleAndMotto();
		titleAndMotto.setOpacity(0);
		rootPane.setTop(titleAndMotto);
		
		options = createWelcomeOptions();
		rootPane.setBottom(options);
		
		animationTimeline();
		
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
		Label leftTitle = labelGenerator(LEFT_SEGMENT_TITLE, MAIN_FONT, MAIN_TEXT_COLOR, TITLE_SIZE);
		Label rightTitle = labelGenerator(RIGHT_SEGMENT_TITLE, MAIN_FONT, MAIN_TEXT_COLOR, TITLE_SIZE);
		
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
		
		/*int xPos;
		int yPos;
		if (isMac()) {
			xPos = INFINITY_POSITION_X_MAC;
			yPos = INFINITY_POSITION_Y_MAC;
		}
		else {
			xPos = INFINITY_POSITION_X_WINDOWS;
			yPos = INFINITY_POSITION_Y_WINDOWS;
		}*/
		
		image.setLayoutX(INFINITY_POSITION_X);
		//image.setLayoutY(xPos);
		//image.setLayoutY(yPos);
		secondSegment.setLayoutX(INFINITY_POSITION_X + INFINITY_WIDTH - INFINITY_BORDER_WIDTH);
		if (isMac()){
			firstSegment.setLayoutY(TITLE_POSITION_Y_MAC);
			secondSegment.setLayoutY(TITLE_POSITION_Y_MAC);
		}
		else {
			firstSegment.setLayoutY(TITLE_POSITION_Y_WINDOWS);
			secondSegment.setLayoutY(TITLE_POSITION_Y_WINDOWS);
		}
	}
	
	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}
	
	private Label createMotto() {
		Label motto = labelGenerator(MOTTO_TEXT, MAIN_FONT, MAIN_TEXT_COLOR, MOTTO_SIZE);
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
	
	private void createWelcomeBoxes() {
		playVBox = boxGenerator(
				borderGenerate(play, e -> handlePlaySelection()),
				PLAY_CAPTION
				);
		createVBox = boxGenerator(
				borderGenerate(create, e -> handleCreateSelection()),
				CREATE_CAPTION
				);
		learnVBox = boxGenerator(
				borderGenerate(learn, e -> handleLearnSelection()),
				LEARN_CAPTION);
		settingsVBox = boxGenerator(
				borderGenerate(settings, e -> handleSettingsSelection()),
				SETTINGS_CAPTION
				);
	}
	
	private HBox borderGenerate(ImageView optionLogo, EventHandler<? super MouseEvent> handler) {
		HBox optionBox = new HBox();
		optionBox.getChildren().add(optionLogo);
		optionBox.setStyle(styleBox(OPTIONS_BOX_BORDER_COLOR));
		optionBox.setOnMouseClicked(handler);
		return optionBox;
	}
	
	private VBox boxGenerator(HBox hbox, String caption) {
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		Label label = labelGenerator(caption, MAIN_FONT, MAIN_TEXT_COLOR, OPTION_SIZE);
		box.getChildren().addAll(label, hbox);
		box.setOpacity(0);
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
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.millis(0), e -> createTransition(titleAndMotto, TITLE_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS), e -> createTransition(playVBox, OPTIONS_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS + (OPTIONS_TRANSITION_DURATION_MILLIS*1)), e -> createTransition(createVBox, OPTIONS_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS + (OPTIONS_TRANSITION_DURATION_MILLIS*2)), e -> createTransition(learnVBox, OPTIONS_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS + (OPTIONS_TRANSITION_DURATION_MILLIS*3)), e -> createTransition(settingsVBox, OPTIONS_FADE_DURATION_MILLIS)),
				new KeyFrame(Duration.millis(TITLE_TRANSITION_DURATION_MILLIS + (OPTIONS_TRANSITION_DURATION_MILLIS*4)))
				);
		timeline.play();
		timeline.setOnFinished(e -> clickEnabled = true);
	}
	
	private void createTransition(VBox box, int duration) {
		FadeTransition ft = new FadeTransition(Duration.millis(duration), box);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
	}
	
	private void handlePlaySelection() {
		
	}
	
	private void handleCreateSelection() {
		
	}
	
	private void handleLearnSelection() {
		if (!clickEnabled) { return; }
		
		Instructions instructions = new Instructions(stage);
		
	}
	
	private void handleSettingsSelection() {
		if (!clickEnabled) { return; }
		
		Settings settings = new Settings(stage);
	}
	
}
