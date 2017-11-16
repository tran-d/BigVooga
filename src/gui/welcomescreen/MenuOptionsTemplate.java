package gui.welcomescreen;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 
 * @author Samarth
 *
 */
public class MenuOptionsTemplate {

	protected static final int CONTENT_INSET_SIZE = 10;
	private static final int BACK_WIDTH = 50;
	private static final int BACK_HEIGHT = 50;
	private static final String BACK_STATIC_PATH = "Back_Static.png";
	private static final String BACK_PATH = "Back.gif";
	private static final Insets CONTENT_PADDING = new Insets(0, CONTENT_INSET_SIZE, CONTENT_INSET_SIZE, CONTENT_INSET_SIZE);
	
	private Stage stage;
	private BorderPane rootPane;
	private ScrollPane contentPane;
	
	private Image backStaticImage;
	private Image backImage;
	private ImageView back;

	public MenuOptionsTemplate(Stage currentStage) {
		stage = currentStage;
		rootPane = new BorderPane();
		Scene scene = new Scene(rootPane, WelcomeScreen.WIDTH, WelcomeScreen.HEIGHT);
		scene.getStylesheets().add(MenuOptionsTemplate.class.getResource("MenuOptionsStyle.css").toExternalForm());

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
	
	private ScrollPane createContentBox() {		
		contentPane = new ScrollPane();
		contentPane.setPrefWidth(WelcomeScreen.WIDTH);
		contentPane.setPrefHeight(WelcomeScreen.HEIGHT);
		contentPane.setStyle(GUITools.styleBox(WelcomeScreen.BORDER_COLOR, WelcomeScreen.BUTTON_BACKGROUND_DEFAULT_COLOR));
		contentPane.setPadding(CONTENT_PADDING);
		BorderPane.setMargin(contentPane, CONTENT_PADDING);
		return contentPane;
	}
	
	protected ScrollPane getScrollPane() {
		return contentPane;
	}
}
