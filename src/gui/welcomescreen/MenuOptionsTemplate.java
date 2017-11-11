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
import javafx.scene.layout.VBox;
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

	public void createOptionScreen(String titleLogoPath, int titleLogoWidth, int titleLogoHeight, int topAndBottomPadding) {
		rootPane.setStyle(WelcomeScreen.SET_BACKGROUND_COLOR + WelcomeScreen.BACKGROUND_COLOR);
		rootPane.setTop(createHeading(titleLogoPath, titleLogoWidth, titleLogoHeight, topAndBottomPadding));
		rootPane.setCenter(createContentBox());

	}
	
	private HBox createHeading(String titleLogoPath, int titleLogoWidth, int titleLogoHeight, int topAndBottomPadding) {
		HBox backButton = createBack(e -> handleBackSelection());
		HBox titleLogo = createLogo(titleLogoPath, titleLogoWidth, titleLogoHeight);
		HBox heading = new HBox((WelcomeScreen.WIDTH/2) - titleLogoWidth/2 - BACK_WIDTH);
		heading.setPadding(new Insets(topAndBottomPadding, 0, topAndBottomPadding, 0));
		heading.getChildren().addAll(backButton, titleLogo);
		return heading;
	}
	
	private HBox createBack(EventHandler<? super MouseEvent> handler) {
		
		backStaticImage = GUITools.createImage(BACK_STATIC_PATH, BACK_WIDTH, BACK_HEIGHT);
		backImage = GUITools.createImage(BACK_PATH, BACK_WIDTH, BACK_HEIGHT);
		back = GUITools.createImageView(backStaticImage);
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

		Image logoImage = GUITools.createImage(titleLogoPath, titleLogoWidth, titleLogoWidth);
		ImageView logo = GUITools.createImageView(logoImage);
		
		HBox logoBox = new HBox();
		logoBox.getChildren().add(logo);
		return logoBox;

	}
	
	private VBox createContentBox() {
		VBox contentBox = new VBox();
		contentBox.setPrefWidth(WelcomeScreen.WIDTH);
		contentBox.setPrefWidth(WelcomeScreen.HEIGHT);
		contentBox.setStyle(GUITools.styleBox(WelcomeScreen.BORDER_COLOR));
		return contentBox;
	}
}
