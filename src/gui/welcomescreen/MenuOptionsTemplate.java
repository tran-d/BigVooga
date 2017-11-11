package gui.welcomescreen;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MenuOptionsTemplate {

	private static final int BACK_WIDTH = 50;
	private static final int BACK_HEIGHT = 50;
	private static final String BACK_STATIC_PATH = "Back_Static.png";
	private static final String BACK_PATH = "Back.gif";
	
	private Stage stage;
	private BorderPane rootPane;
	
	private Image backStaticImage;
	private Image backImage;
	private ImageView back;

	public MenuOptionsTemplate(Stage currentStage) {
		stage = currentStage;
		rootPane = new BorderPane();
		Scene scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);

		stage.setScene(scene);
	}

	public void createOptionScreen(String titleLogoPath, int titleLogoWidth, int titleLogoHeight) {
		rootPane.setStyle(WelcomeScreen.SET_BACKGROUND_COLOR + WelcomeScreen.BACKGROUND_COLOR);
		rootPane.setTop(createHeading(titleLogoPath, titleLogoWidth, titleLogoHeight));

	}
	
	private HBox createHeading(String titleLogoPath, int titleLogoWidth, int titleLogoHeight) {
		HBox backButton = createBack(e -> handleBackSelection());
		HBox titleLogo = createLogo(titleLogoPath, titleLogoWidth, titleLogoHeight);
		HBox heading = new HBox((WelcomeScreen.WIDTH/2) - titleLogoWidth/2 - BACK_WIDTH);
		heading.setPadding(new Insets(10, 0, 0, 0));
		heading.getChildren().addAll(backButton, titleLogo);
		return heading;
	}
	
	private HBox createBack(EventHandler<? super MouseEvent> handler) {
		
		backStaticImage = createImage(BACK_STATIC_PATH, BACK_WIDTH, BACK_HEIGHT);
		backImage = createImage(BACK_PATH, BACK_WIDTH, BACK_HEIGHT);
		back = createImageView(backStaticImage);
		handleHover();
		
		HBox backBox = new HBox();
		backBox.getChildren().add(back);
		backBox.setAlignment(Pos.CENTER);
		backBox.setOnMouseClicked(handler);
		return backBox;
		
	}
	
	private void handleHover() {
		back.setOnMouseEntered(e -> backMouseEnter());
		back.setOnMouseExited(e -> backMouseExit());
	}
	
	private void backMouseEnter(){
		back.setImage(backImage);
	}
	
	private void backMouseExit() {
		back.setImage(backStaticImage);
	}
	
	private void handleBackSelection() {
		
		WelcomeScreen welcome = new WelcomeScreen(stage);
		welcome.createWelcomeScreen();
		
	}

	private HBox createLogo(String titleLogoPath, int titleLogoWidth, int titleLogoHeight) {

		Image logoImage = createImage(titleLogoPath, titleLogoWidth, titleLogoWidth);
		ImageView logo = createImageView(logoImage);
		
		HBox logoBox = new HBox();
		logoBox.getChildren().add(logo);
		return logoBox;

	}

	private Image createImage(String path, int width, int height) {
		Image image = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(path), width, height, true, true);
		return image;
	}
	private ImageView createImageView(Image image) {
		ImageView imageView = new ImageView(image);
		return imageView;
	}
}
