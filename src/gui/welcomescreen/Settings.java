package gui.welcomescreen;

import java.util.Locale;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.DisplayLanguage;

/**
 * 
 * @author Samarth
 *
 */
public class Settings extends MenuOptionsTemplate{

	private static final String GEARS_PATH = "Gears.gif";
	private static final int GEARS_WIDTH = 125;
	private static final int GEARS_HEIGHT = 125;
	private static final int HEADING_PADDING = 10;
	private static final String LANGUAGE_PATH = "Language_Static.png";
	private static final int LANGUAGE_WIDTH = 100;
	private static final int LANGUAGE_HEIGHT = 100;
	private static final String LANGUAGE_HEADING = "Language";
	private static final String LANGUAGE_HEADING_SIZE = 50 + "pt;";
	private static final String ENGLISH_BUTTON = "English";
	private static final String SPANISH_BUTTON = "Spanish";
	private static final String LANGUAGE_TYPE_SIZE = 30 + "pt;";
	private static final int LANGUAGE_BUTTON_HORIZONTAL_GAP = 20;
	private static final int LANGUAGE_BUTTON_VERTICAL_GAP = 20;
	private static final int SETTINGS_VERTICAL_GAP = 50;
	
	private ScrollPane contentPane = new ScrollPane();
	ToggleGroup languageGroup = new ToggleGroup();

	public Settings(Stage currentStage) {
		super(currentStage);
		createOptionScreen(GEARS_PATH, GEARS_WIDTH, GEARS_HEIGHT, HEADING_PADDING);
		
	}
	
	public void createSettings(){
		VBox contentBox = consolidateText();
		
		contentPane = getScrollPane();
		contentPane.setContent(contentBox);
	}
	
	private VBox consolidateText() {
		VBox allSettings = new VBox(SETTINGS_VERTICAL_GAP);
		VBox languageBox = createSetting(LANGUAGE_HEADING, LANGUAGE_HEADING_SIZE);
		allSettings.getChildren().add(languageBox);
		return allSettings;
	}
	
	private VBox createSetting(String languageText, String languageSize) {
		
		Label headingText = GUITools.generateLabel(languageText, WelcomeScreen.MAIN_FONT, WelcomeScreen.MAIN_COLOR, languageSize);
		Image languageImage = GUITools.createImage(LANGUAGE_PATH, LANGUAGE_WIDTH, LANGUAGE_HEIGHT);
		ImageView language = GUITools.createImageView(languageImage);
		HBox languageSelection = createLanguageSelection();
		
		HBox settingHeading = new HBox();
		settingHeading.getChildren().addAll(headingText, language);
		settingHeading.setAlignment(Pos.CENTER);
		VBox settingBox = new VBox(LANGUAGE_BUTTON_VERTICAL_GAP);
		settingBox.getChildren().addAll(settingHeading, languageSelection);
		return settingBox;
	}
	
	private HBox createLanguageSelection() {
		
		RadioButton englishButton = createLanguageButton(ENGLISH_BUTTON, Locale.ENGLISH);
		englishButton.setSelected(true);
		RadioButton spanishButton = createLanguageButton(SPANISH_BUTTON, DisplayLanguage.SPANISH);
		
		HBox languageChoices = new HBox(LANGUAGE_BUTTON_HORIZONTAL_GAP);
		languageChoices.getChildren().addAll(englishButton, spanishButton);
		return languageChoices;
	}
	
	private RadioButton createLanguageButton(String languageType, Locale language) {
		RadioButton languageButton = new RadioButton(languageType);
		languageButton.getStyleClass().remove("radio-button");
		languageButton.getStyleClass().add("toggle-button");
		languageButton.getStylesheets().add(Settings.class.getResource("SettingsButtonStyle.css").toExternalForm());
		languageButton.setToggleGroup(languageGroup);
		languageButton.textProperty().bind(DisplayLanguage.createStringBinding(languageType));
		languageButton.setOnAction(e -> DisplayLanguage.setLocale(language));
		return languageButton;
	}
}