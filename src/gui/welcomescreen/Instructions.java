package gui.welcomescreen;

import default_pkg.SceneController;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Samarth
 *
 */
public class Instructions extends MenuOptionsTemplate {

	private static final String CRYSTAL_PATH = "Crystal.gif";
	private static final int CRYSTAL_WIDTH = 125;
	private static final int CRYSTAL_HEIGHT = 125;
	private static final String INSTRUCTIONS_INTRO_HEADING_SIZE = 50 + "pt;";
	private static final String INSTRUCTIONS_CONTENT_HEADING_SIZE = 30 + "pt;";
	private static final String INSTRUCTIONS_BODY_SIZE = 12 + "pt;";
	private static final String INSTRUCTIONS_INTRO_HEADING = "IntroHeading";
	private static final String INSTRUCTIONS_INTRO_BODY = "IntroBody";
	private static final String INSTRUCTIONS_AUTHORING_HEADING = "AuthoringHeading";
	private static final String INSTRUCTIONS_AUTHORING_BODY = "AuthoringBody";
	private static final String INSTRUCTIONS_PLAYER_HEADING = "PlayerHeading";
	private static final String INSTRUCTIONS_PLAYER_BODY = "PlayerBody";
	private static final String INSTRUCTIONS_FAQ_HEADING = "FAQHeading";
	private static final String INSTRUCTIONS_FAQ_BODY = "FAQBody";

	private ScrollPane contentPane = new ScrollPane();
	public Instructions(Stage currentStage, SceneController sceneController) {
		super(currentStage, sceneController);
		createOptionScreen(CRYSTAL_PATH, CRYSTAL_WIDTH, CRYSTAL_HEIGHT, 0);
	}
	
	public void createInstructions() {
		
		VBox contentBox = consolidateText();
		
		contentPane = getScrollPane();
		contentPane.setContent(contentBox);
	}
	
	private VBox consolidateText() {
		
		VBox allText = new VBox();
		VBox introText = createText(INSTRUCTIONS_INTRO_HEADING, INSTRUCTIONS_INTRO_HEADING_SIZE, INSTRUCTIONS_INTRO_BODY);
		VBox authoringText = createText(INSTRUCTIONS_AUTHORING_HEADING, INSTRUCTIONS_CONTENT_HEADING_SIZE, INSTRUCTIONS_AUTHORING_BODY);
		VBox playerText = createText(INSTRUCTIONS_PLAYER_HEADING, INSTRUCTIONS_CONTENT_HEADING_SIZE, INSTRUCTIONS_PLAYER_BODY);
		VBox faqText = createText(INSTRUCTIONS_FAQ_HEADING, INSTRUCTIONS_CONTENT_HEADING_SIZE, INSTRUCTIONS_FAQ_BODY);
		
		allText.getChildren().addAll(introText, authoringText, playerText, faqText);
		return allText;
	}
	
	private VBox createText(String heading, String headingSize, String body) {
		
		VBox textBox = new VBox();
		Label headingText = GUITools.generateLabel(heading, WelcomeScreen.MAIN_FONT, WelcomeScreen.MAIN_COLOR, headingSize);
		Label bodyText = GUITools.generateLabel(body, WelcomeScreen.MAIN_FONT, WelcomeScreen.MAIN_COLOR, INSTRUCTIONS_BODY_SIZE);
		
		/**
		 *The content inset size times four is subtracted from the screen width because the ScrollPane is the entire width of the screen,
		 *minus its two insets on the left and two on the right, which are all the same size. Thus, to properly wrap the text, the subtraction of
		 *the shared inset size times four correctly accounts for all the horizontal padding that exists for the ScrollPane. Then, an additional
		 *four is subtracted because the border width is two, so for both the left and right border that makes four additional pixels.
		 */
		bodyText.setPrefWidth(WelcomeScreen.WIDTH - CONTENT_INSET_SIZE*4 - 4);
		bodyText.setWrapText(true);
		headingText.setPrefWidth(WelcomeScreen.WIDTH - CONTENT_INSET_SIZE*4 - 4);
		headingText.setWrapText(true);
		
		textBox.getChildren().addAll(headingText, bodyText);
		return textBox;
	}
	
}
