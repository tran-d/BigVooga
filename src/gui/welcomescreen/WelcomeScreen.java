package gui.welcomescreen;

import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelcomeScreen {

	public static final int WIDTH = 1400;
	public static final int HEIGHT = 700;
	private static final String STAGE_TITLE = "VOOGA";
	private static final String WELCOME_BACKGROUND_COLOR = "#001E32;";
	private static final String TITLE_COLOR = "#47BDFF;";
	private static final String TITLE_FONT = "Segoe UI;";
	private static final String INFINITY_PATH = "Infinity.gif";
	private static final int INFINITY_WIDTH = 400;
	private static final int INFINITY_HEIGHT = 300;
	private static final String TITLE_HEIGHT = INFINITY_HEIGHT/2 + "pt;";

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
		HBox titleBox = createTitle();
		rootPane.setTop(titleBox);
		
		
	}
	
	private HBox createTitle() {
		Image infinity = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(INFINITY_PATH), INFINITY_WIDTH, INFINITY_HEIGHT, true, true);
		ImageView infinite = new ImageView(infinity);
		Label v = labelGenerator("V", TITLE_FONT, TITLE_COLOR, TITLE_HEIGHT);
		Label ga = labelGenerator("GA", TITLE_FONT, TITLE_COLOR, TITLE_HEIGHT);
		HBox title = new HBox();
		title.getChildren().addAll(v, infinite, ga);
		title.setAlignment(Pos.CENTER);
		return title;
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
	
	private void createMotto() {
		
	}
	
}
