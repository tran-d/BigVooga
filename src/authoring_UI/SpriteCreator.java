package authoring_UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteNameManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring.SpritePanels.DisplayPanel;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class for creating new sprites
 * @author taekwhunchung
 */
public class SpriteCreator extends TabPane {
	private static final String PATH = "resources/";
	private static final String SPRITECREATORRESOURCES_PATH = "TextResources/SpriteCreatorResources";
	private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;

	private TextField nameField;
	private String fileName;
	private SpriteObject newSprite;
	private ResourceBundle spriteCreatorResources;
	private DisplayPanel myDP;
	private AuthoringEnvironmentManager myAEM;
	private SpriteNameManager mySNM;
	private Pane myPane;
	private HBox nameBox;
	private VBox myStatePanel;
	private StackPane myImageStack;
	private int spriteCount = 1;

	public SpriteCreator(AuthoringEnvironmentManager AEM) {
		spriteCreatorResources = ResourceBundle.getBundle(SPRITECREATORRESOURCES_PATH);
		myAEM = AEM;
		mySNM = new SpriteNameManager();
		myPane = new Pane();
		myPane.getChildren().add(this);
		this.setWidth(PANE_WIDTH);
		this.setLayoutX(ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
		Tab tab = makeTab();
		this.getTabs().add(tab);
	}

	private Tab makeTab() {
		Tab tab = new Tab();
		tab.setText(spriteCreatorResources.getString("SpriteTab") + spriteCount);
		spriteCount++;
		HBox hb = addParentHBox(tab);
		addStatePanel(hb);
		addImageStackPane(hb);
		addToolBox(hb);
		return tab;
	}

	private void addToolBox(HBox parentBox) {
		VBox toolBox = new VBox();
		toolBox.setPrefSize(200, WelcomeScreen.HEIGHT);
		Button b1 = new Button(spriteCreatorResources.getString("TestTool1"));
		Button b2 = new Button(spriteCreatorResources.getString("TestTool2"));
		toolBox.getChildren().addAll(b1, b2);
		parentBox.getChildren().add(toolBox);
	}

	private void addImageStackPane(HBox parentBox) {
		myImageStack = new StackPane();
		myImageStack.setMinWidth(PANE_WIDTH / 2 - 100);
		myImageStack.setMaxSize(PANE_WIDTH / 2 - 100, WelcomeScreen.HEIGHT);
		myImageStack.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
				BorderWidths.DEFAULT);
		myImageStack.setBorder(new Border(border));

		newSprite = new SpriteObject();
		nameBox = makeNameBox();

		myImageStack.getChildren().addAll(newSprite, nameBox);
		parentBox.getChildren().add(myImageStack);
	}

	private HBox makeNameBox() {
		HBox nameBox = new HBox(10);
		Text enterName = new Text(spriteCreatorResources.getString("NameField"));
		nameField = new TextField();

		nameBox.getChildren().addAll(enterName, nameField);

		nameField.setOnAction(e -> {
			System.out.println("name entered");
			if (mySNM.isNameValidTemplate(nameField.getText())) {
				// add to mySNM
				mySNM.addTemplateName(nameField.getText());
				System.out.println("name is valid");
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Input Error");
				alert.setHeaderText("Name already exists");
				alert.setContentText("Change to a unique name");
				alert.showAndWait();
			}
		});
		return nameBox;
	}

	private void addStatePanel(HBox parentBox) {
		myStatePanel = new VBox();
		myStatePanel.setPrefWidth(PANE_WIDTH / 2);
		myStatePanel.setMaxWidth(PANE_WIDTH / 2);
		myStatePanel.setPrefHeight(WelcomeScreen.HEIGHT);
		myStatePanel.setMaxWidth(PANE_WIDTH);
		myStatePanel.setStyle("-fx-background-color: #ffaadd;");

		HBox buttonBox = new HBox(10);

		addButtons(buttonBox);
		VBox stateBox = createStateBox();

		TabPane spritePane = addSpriteTabs();

		myStatePanel.getChildren().addAll(buttonBox, stateBox, spritePane);

		parentBox.getChildren().add(myStatePanel);
	}

	private TabPane addSpriteTabs() {
		TabPane tp = new TabPane();
		Tab defaultTab = makeTab("Default");
		Tab userTab = makeTab("User");
		Tab importTab = makeTab("Imported");

		ScrollPane defaultSP = createGrid(myAEM.getDefaultSpriteController().getAllSprites());
		defaultTab.setContent(defaultSP);

		ScrollPane userSP = createGrid(myAEM.getCustomSpriteController().getAllSprites());
		userTab.setContent(userSP);

		ScrollPane importSP = new ScrollPane();
		importTab.setContent(importSP);

		tp.getTabs().addAll(defaultTab, userTab, importTab);
		return tp;
	}

	private Tab makeTab(String tabName) {
		return new Tab(tabName);
	}

	private void addButtons(HBox buttonBox) {
		Button loadImageButton = new Button(spriteCreatorResources.getString("LoadImageButton"));
		loadImageButton.setOnAction(e -> {
			try {
				openImage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button createSpriteButton = new Button("Create Sprite Template");

		createSpriteButton.setOnAction(e -> {

			String spriteName = nameField.getText();
			if (mySNM.isNameValidTemplate(spriteName)) {
				try {
					newSprite.setName(spriteName);
					myAEM.addUserSprite(newSprite);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("name is valid");

				mySNM.addTemplateName(spriteName);

				nameField.clear();
				myImageStack.getChildren().removeAll(newSprite, nameBox);
				newSprite = new SpriteObject();

				myImageStack.getChildren().addAll(newSprite, nameBox);

				myStatePanel.getChildren().remove(2);
				myStatePanel.getChildren().add(addSpriteTabs());
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
			nameField.setText(fileName.substring(0, fileName.indexOf(".")));

			myImageStack.getChildren().removeAll(newSprite, nameBox);

			newSprite = new SpriteObject();
			newSprite.setImageURL(file.getName());
			newSprite.setNumCellsWidthNoException(1);
			newSprite.setNumCellsHeightNoException(1);
			// add params
			List<SpriteParameterI> myParams = new ArrayList<SpriteParameterI>();

			myImageStack.getChildren().addAll(newSprite, nameBox);

			System.out.println("image loaded");
		}
	}
	
	private ScrollPane createDefaultGrid() {
		ScrollPane sp = new ScrollPane();
		return sp;
	}

	private ScrollPane createGrid(List<AbstractSpriteObject> sprites) {
		GridPane gp = new GridPane();

		int counter = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 10; j++) {
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));

				if (counter < sprites.size()) {
					AbstractSpriteObject toLoad = sprites.get(counter);
					toLoad.setOnMouseClicked(e -> {
						System.out.println("SPRITE CLICKED");
						myDP.addSpriteEditorVBox();
					});
					sp.getChildren().add(toLoad);
				}
				counter++;

				gp.add(sp, j, i);
			}
		}
		ScrollPane sp = new ScrollPane(gp);
		sp.setMaxHeight(WelcomeScreen.HEIGHT / 2);
		return sp;
	}

	private VBox createStateBox() {
		myDP = new DisplayPanel();
		myDP.setMaxHeight(WelcomeScreen.HEIGHT / 2);
		myDP.setPrefHeight(WelcomeScreen.HEIGHT / 2);
		myDP.setPrefWidth(PANE_WIDTH / 2);
		myDP.setStyle("-fx-background-color: #ffaadd;");
		return myDP;
	}

	private HBox addParentHBox(Tab tab) {
		HBox hb = new HBox();
		hb.setStyle("-fx-background-color: #FFFFFF;");
		hb.setPrefWidth(PANE_WIDTH);
		hb.setPrefHeight(WelcomeScreen.HEIGHT);
		tab.setContent(hb);
		return hb;
	}

	public Pane getPane() {
		return myPane;
	}
}