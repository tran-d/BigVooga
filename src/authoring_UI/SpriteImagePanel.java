package authoring_UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteNameManager;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.Parameters.SpriteParameterI;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class for image panel in SpriteCreator
 * 
 * @author taekwhunchung
 *
 */
public class SpriteImagePanel extends VBox {

	private static final String PATH = "resources/";
	private static final String SPRITECREATORRESOURCES_PATH = "TextResources/SpriteCreatorResources";
	private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;

	private ResourceBundle spriteCreatorResources;
	private HBox buttonBox;
	private HBox nameBox;
	private HBox imageBox;
	private String fileName;
	private TextField nameField;
	private TextField categoryField;
	private SpriteObject newSprite;
	private SpriteNameManager mySNM;
	private SpriteCreatorImageGrid myImageGrid;
	private AuthoringEnvironmentManager myAEM;

	protected SpriteImagePanel(AuthoringEnvironmentManager AEM, SpriteCreatorImageGrid imageGrid) {
		spriteCreatorResources = ResourceBundle.getBundle(SPRITECREATORRESOURCES_PATH);

		this.setMinWidth(PANE_WIDTH / 2 - 300);
		this.setMinHeight(500);
		this.setMaxSize(PANE_WIDTH / 2 - 300, WelcomeScreen.HEIGHT);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
				BorderWidths.DEFAULT);
		this.setStyle("-fx-background-color: transparent;");
		this.setBorder(new Border(border));

		myAEM = AEM;
		mySNM = new SpriteNameManager();
		buttonBox = new HBox(10);
		imageBox = new HBox();
		addButtons();
		addImageGrid(imageGrid);

		this.getChildren().addAll(buttonBox, imageBox);
	}

	private void addImageGrid(SpriteCreatorImageGrid imageGrid) {
		myImageGrid = imageGrid; 
		imageBox.getChildren().add(myImageGrid);

	}

	private void addButtons() {
		Button loadImageButton = new Button(spriteCreatorResources.getString("LoadImageButton"));
		loadImageButton.setOnAction(e -> {
			try {
				openImage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button createSpriteButton = new Button(spriteCreatorResources.getString("CreateSpriteButton"));

		createSpriteButton.setOnAction(e -> {

			String spriteName = nameField.getText();
			if (mySNM.isNameValidTemplate(spriteName)) {
				try {
					newSprite.setName(spriteName);
					if (categoryField.getText() == null) {
						myAEM.addUserSprite(newSprite);
					} else {
						myAEM.addUserSprite(categoryField.getText(), newSprite);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("name is valid");

				mySNM.addTemplateName(spriteName);

				nameField.clear();
				categoryField.clear();

				this.getChildren().removeAll(newSprite, nameBox);
				newSprite = new SpriteObject();

				this.getChildren().addAll(newSprite, nameBox);

				this.getChildren().remove(1);
				// this.getChildren().add(addSpriteTabs());
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Name already exists");
				alert.setContentText("Change to a unique name");
				alert.showAndWait();
			}

		});
		buttonBox.getChildren().addAll(loadImageButton, createSpriteButton);
	}

	private void openImage() throws IOException {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setInitialDirectory(new File("resources/"));
		imageChooser.setTitle(spriteCreatorResources.getString("ImageChooser"));
		File file = imageChooser.showOpenDialog(new Stage());

		if (file != null) {
			Files.copy(file.toPath(), Paths.get(PATH + file.getName()), StandardCopyOption.REPLACE_EXISTING);

			fileName = file.getName();
//			nameField.setText(fileName.substring(0, fileName.indexOf(".")));
//			categoryField.setText("General");

			newSprite = new SpriteObject();
			newSprite.setImageURL(file.getName());
			newSprite.setNumCellsWidthNoException(1);
			newSprite.setNumCellsHeightNoException(1);
			
			if (myImageGrid.getSprite() == null) {
				myImageGrid.setSprite(newSprite);
			}
			else {
				myImageGrid.getChildren().remove(myImageGrid.getSprite());
				myImageGrid.setSprite(newSprite);
			}
//			this.getChildren().removeAll(newSprite, nameBox);


			// add params
			ArrayList<SpriteParameterI> myParams = new ArrayList<SpriteParameterI>();

			System.out.println("image loaded");
		}
	}
}
