package authoring_UI;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteCreatorSpriteManager;
import authoring.SpriteNameManager;
import authoring.Sprite.SpriteObject;
import authoring.SpritePanels.DisplayPanel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class for creating new sprites
 * 
 * @author taekwhunchung
 *
 */

public class SpriteCreator extends TabPane {

	private static final String PATH = "resources/";
	private static final String SPRITECREATORRESOURCES_PATH = "TextResources/SpriteCreatorResources";
	private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;
	private static final String ADD_TAB = "+";
	private static final String TAB_TAG = "Sprite";

	
	private Stage myStage;
	private Tab addTab;
	private Tab currentTab;
	private TextField nameField;
	private TextField categoryField;
	private String fileName;
	private SpriteObject newSprite;
	private ResourceBundle spriteCreatorResources;
	private DisplayPanel myDP;
	private AuthoringEnvironmentManager myAEM;
	private SpriteCreatorManager mySCM;
	private SpriteCreatorSpriteManager mySM;
	private SpriteNameManager mySNM;
	private SpriteCreatorGridHandler myGridHandler;
	private SingleSelectionModel <Tab> mySelectModel;
	private List<DraggableGrid> allWorlds = new ArrayList<DraggableGrid>();
	private Pane myPane;
	private HBox nameBox;
	private VBox myStatePanel;
	private StackPane myImageStack;
	private int spriteCount = 1;
	private int myTabCount = 1;

	public SpriteCreator(AuthoringEnvironmentManager AEM, SpriteCreatorManager SCM, SpriteCreatorImageGrid imageGrid, SpriteCreatorSpriteManager SM, SpriteCreatorGridHandler mySCGridHandler) {
		setup(AEM, SCM, SM);
		SpriteTab tab = new SpriteTab(AEM,SCM, imageGrid, mySM, mySCGridHandler);
		this.getTabs().add(tab);

	}

	private void setup(AuthoringEnvironmentManager AEM, SpriteCreatorManager SCM, SpriteCreatorSpriteManager SM) {
		spriteCreatorResources = ResourceBundle.getBundle(SPRITECREATORRESOURCES_PATH);
		myAEM = AEM;
		mySCM = SCM;
		mySM = SM;
//		mySNM = new SpriteNameManager();
		myPane = new Pane();
		myPane.getChildren().add(this);
		mySelectModel = this.getSelectionModel();
		this.setWidth(PANE_WIDTH);
		this.setLayoutX(ViewSideBar.VIEW_MENU_HIDDEN_WIDTH);
	}

//	private Tab makeTab() {
//		Tab tab = new Tab();
//		tab.setText(spriteCreatorResources.getString("SpriteTab") + spriteCount);
//		spriteCount++;
//
//		HBox hb = addParentHBox(tab);
//		
//		myStatePanel = new SpriteStatePanel(myAEM);
//		hb.getChildren().add(myStatePanel);
//		
//		addImageStackPane(hb);
//		addToolBox(hb);
//
//		return tab;
//
//	}
//
//	private void addToolBox(HBox parentBox) {
//		VBox toolBox = new VBox();
//		toolBox.setPrefSize(200, WelcomeScreen.HEIGHT);
//		Button b1 = new Button(spriteCreatorResources.getString("TestTool1"));
//		Button b2 = new Button(spriteCreatorResources.getString("TestTool2"));
//		toolBox.getChildren().addAll(b1, b2);
//		parentBox.getChildren().add(toolBox);
//	}
//
//	private void addImageStackPane(HBox parentBox) {
//		VBox imageBox = new VBox();
//
//		HBox buttonBox = new HBox(10);
//		addButtons(buttonBox);
//
//		myImageStack = new StackPane();
//		// myImageStack.setPrefSize(PANE_WIDTH/2-100, WelcomeScreen.HEIGHT);
//		myImageStack.setMinWidth(PANE_WIDTH / 2 - 300);
//		myImageStack.setMinHeight(500);
//		myImageStack.setMaxSize(PANE_WIDTH / 2 - 300, WelcomeScreen.HEIGHT);
//		myImageStack.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
//		BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
//				BorderWidths.DEFAULT);
//		myImageStack.setStyle("-fx-background-color: transparent;");
//		myImageStack.setBorder(new Border(border));
//
//		// ImageView test = new ImageView("pikachu.png");
////		DraggableGrid myDG = new DraggableGrid(5,5);
////		setTab();
////		createTab(spriteCount, myDG);
//		
//		
//		newSprite = new SpriteObject();
//		nameBox = makeNameBox();
//
//		// myImageStack.getChildren().addAll(test, nameBox);
//		myImageStack.getChildren().addAll(nameBox, newSprite);
//
//		imageBox.getChildren().addAll(buttonBox, myImageStack);
//		parentBox.getChildren().add(imageBox);
//		// parentBox.getChildren().add(new Text("test"));
//	}
//	private void setTab() { //?
//		this.setSide(Side.TOP);
//		addTab = new Tab();
//		addTab.setClosable(false);
//		addTab.setText(ADD_TAB);
//		addTab.setOnSelectionChanged(e -> {
//			createTab(myTabCount, new DraggableGrid());
//			mySelectModel.select(currentTab);
//		});
//		this.getTabs().add(addTab);
//	}

//	private HBox setupScene(DraggableGrid w) { 
//		return setupFEAuthClasses(w);
////		return authMap;
//	}
	
	
//	private HBox setupFEAuthClasses(DraggableGrid w) { 
//		;
//		// TODO if it's old project, want all possible worlds, so many worlds!
//		allWorlds.add(w); // TODO unsure if needed
//		SpriteGridHandler mySpriteGridHandler = new SpriteGridHandler(myTabCount, w, 1); 
//		w.construct(mySpriteGridHandler);
//		mySpriteGridHandler.addKeyPress(myStage.getScene());
//		SpritePanels spritePanels = new SpritePanels(mySpriteGridHandler, myAEM);
//		mySpriteGridHandler.setDisplayPanel(spritePanels);
//		AuthoringMapEnvironment authMap = new AuthoringMapEnvironment(spritePanels, w);
//		return authMap;
//	}

//	private void createTab(int tabCount, DraggableGrid w) { //?
//		currentTab = new Tab();
//		StringProperty tabMap = new SimpleStringProperty();
//		tabMap.bind(Bindings.concat(DisplayLanguage.createStringBinding(TAB_TAG)).concat(" " + Integer.toString(tabCount)));
//		
//		currentTab.textProperty().bind(tabMap);
////		currentTab.textProperty().set(//TODO: World Name);
//		currentTab.setContent(setupScene(w));
//		this.getTabs().add(this.getTabs().size() - 1, currentTab);
//		myTabCount++;
//	}
//
//	private HBox makeNameBox() {
//		HBox nameBox = new HBox(10);
//		Text enterName = new Text(spriteCreatorResources.getString("NameField"));
//		enterName.setStroke(Color.WHITE);
//		nameField = new TextField();
//		Text enterCategory = new Text(spriteCreatorResources.getString("CategoryField"));
//		enterCategory.setStroke(Color.WHITE);
//		categoryField = new TextField();
//
//		// Button confirm = new
//		// Button(spriteCreatorResources.getString("ConfirmButton"));
//
//		nameBox.getChildren().addAll(enterName, nameField, enterCategory, categoryField);
//
//		// nameField.setOnAction(e -> {
//		// ;
//		// if (mySNM.isNameValidTemplate(nameField.getText())) {
//		// // add to mySNM
//		// mySNM.addTemplateName(nameField.getText());
//		// ;
//		// } else {
//		// Alert alert = new Alert(AlertType.ERROR);
//		// alert.setTitle("Input Error");
//		// alert.setHeaderText("Name already exists");
//		// alert.setContentText("Change to a unique name");
//		// alert.showAndWait();
//		// }
//		// });
//
//		// confirm.setOnAction(e -> {
//		// ;
//		// if (mySNM.isNameValidTemplate(nameField.getText())) {
//		// // add to mySNM
//		// ;
//		// } else {
//		// Alert alert = new Alert(AlertType.ERROR);
//		// alert.setTitle("Error");
//		// alert.setHeaderText("Name already exists");
//		// alert.setContentText("Change to a unique name");
//		// alert.showAndWait();
//		// }
//		// });
//
//		return nameBox;
//	}
//
//	private void addStatePanel(HBox parentBox) {
//		myStatePanel = new VBox();
//		myStatePanel.setPrefWidth(PANE_WIDTH / 2);
//		myStatePanel.setMaxWidth(PANE_WIDTH / 2);
//		myStatePanel.setPrefHeight(WelcomeScreen.HEIGHT);
//		myStatePanel.setMaxWidth(PANE_WIDTH);
//		myStatePanel.setStyle("-fx-background-color: transparent;");
//
//		// HBox buttonBox = new HBox(10);
//		// addButtons(buttonBox);
//		VBox stateBox = createStateBox();
//
//		TabPane spritePane = addSpriteTabs();
//		// ScrollPane sp = createGrid();
//
//		myStatePanel.getChildren().addAll(stateBox, spritePane);
//
//		parentBox.getChildren().add(myStatePanel);
//	}

//	private TabPane addSpriteTabs() {
//		TabPane tp = new TabPane();
//		Tab defaultTab = makeTab("Default");
//		Tab userTab = makeTab("User");
//		Tab importTab = makeTab("Imported");
//
//		ScrollPane defaultSP = createGrid(myAEM.getDefaultSpriteController().getAllSprites());
//		defaultTab.setContent(defaultSP);
//
//		ScrollPane userSP = createGrid(myAEM.getCustomSpriteController().getAllSprites());
//		userTab.setContent(userSP);
//
//		ScrollPane importSP = new ScrollPane();
//		importTab.setContent(importSP);
//
//		tp.getTabs().addAll(defaultTab, userTab, importTab);
//		return tp;
//	}

//	private Tab makeTab(String tabName) {
//
//		return new Tab(tabName);
//	}

//	private void addButtons(HBox buttonBox) {
//		Button loadImageButton = new Button(spriteCreatorResources.getString("LoadImageButton"));
//		loadImageButton.setOnAction(e -> {
//			try {
//				openImage();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
//
//		Button createSpriteButton = new Button("Create Sprite Template");
//
//		createSpriteButton.setOnAction(e -> {
//
//			String spriteName = nameField.getText();
//			if (mySNM.isNameValidTemplate(spriteName)) {
//				try {
//					newSprite.setName(spriteName);
//					if (categoryField.getText() == null) {
//						myAEM.addUserSprite(newSprite);
//					} else {
//						myAEM.addUserSprite(categoryField.getText(), newSprite);
//					}
//
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				;
//
//				mySNM.addTemplateName(spriteName);
//
//				nameField.clear();
//				categoryField.clear();
//
//				myImageStack.getChildren().removeAll(newSprite, nameBox);
//				newSprite = new SpriteObject();
//
//				myImageStack.getChildren().addAll(newSprite, nameBox);
//
//				myStatePanel.getChildren().remove(1);
//				myStatePanel.getChildren().add(addSpriteTabs());
//			} else {
//				Alert alert = new Alert(AlertType.ERROR);
//				alert.setTitle("Error");
//				alert.setHeaderText("Name already exists");
//				alert.setContentText("Change to a unique name");
//				alert.showAndWait();
//			}
//
//		});
//
//		buttonBox.getChildren().addAll(loadImageButton, createSpriteButton);
//
//	}

//	private void openImage() throws IOException {
//		FileChooser imageChooser = new FileChooser();
//		imageChooser.setInitialDirectory(new File("resources/"));
//		imageChooser.setTitle(spriteCreatorResources.getString("ImageChooser"));
//		File file = imageChooser.showOpenDialog(new Stage());
//
//		if (file != null) {
//			Files.copy(file.toPath(), Paths.get(PATH + file.getName()), StandardCopyOption.REPLACE_EXISTING);
//
//			fileName = file.getName();
//			nameField.setText(fileName.substring(0, fileName.indexOf(".")));
//			categoryField.setText("General");
//
//			myImageStack.getChildren().removeAll(newSprite, nameBox);
//
//			newSprite = new SpriteObject();
//			newSprite.setImageURL(file.getName());
//			newSprite.setNumCellsWidthNoException(1);
//			newSprite.setNumCellsHeightNoException(1);
//
//			// add params
//			ArrayList<SpriteParameterI> myParams = new ArrayList<SpriteParameterI>();
//
//			// newSprite.setName("test_name");
//
//			// imageView.setFitHeight(WelcomeScreen.HEIGHT);
//			// imageView.setFitWidth(PANE_WIDTH/2-100);
//
//			myImageStack.getChildren().addAll(newSprite, nameBox);
//
//			// myStatePanel.getChildren().add(imageView);
//			;
//			// ;
//			// mySpriteObject.setImageURL(file.getName());
//			// setChanged();
//			// System.out.print(file.getName());
//			// notifyObservers(file.getName());
//			// ;
//		}
//	}
//
//	private ScrollPane createDefaultGrid() {
//		ScrollPane sp = new ScrollPane();
//		return sp;
//	}

//	private ScrollPane createGrid(List<AbstractSpriteObject> sprites) {
//		GridPane gp = new GridPane();
//		int totalRows = (int) Math.ceil(sprites.size() / 10);
//		int DEFAULT_MIN_ROWS = 15;
//		totalRows = (totalRows < DEFAULT_MIN_ROWS) ? DEFAULT_MIN_ROWS : totalRows;
//		int counter = 0;
//		for (int i = 0; i < totalRows; i++) {
//			for (int j = 0; j < 10; j++) {
//				StackPane sp = new StackPane();
//				sp.setPrefHeight(50);
//				sp.setPrefWidth(50);
//				sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
//				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
//						BorderWidths.DEFAULT);
//				sp.setBorder(new Border(border));
//
//				if (counter < sprites.size()) {
//					AbstractSpriteObject toLoad = sprites.get(counter);
//					toLoad.setOnMouseClicked(e -> {
//						;
//						myDP.addSpriteEditorVBox();
//						// myDP.updateParameterTab();
//					});
//					sp.getChildren().add(toLoad);
//				}
//				counter++;
//
//				gp.add(sp, j, i);
//			}
//		}
//
//		ScrollPane sp = new ScrollPane(gp);
//		sp.setMaxHeight(WelcomeScreen.HEIGHT / 2);
//		return sp;
//	}

//	private VBox createStateBox() {
//		myDP = new DisplayPanel();
//		myDP.setMaxHeight(WelcomeScreen.HEIGHT / 2);
//		myDP.setPrefHeight(WelcomeScreen.HEIGHT / 2);
//		myDP.setPrefWidth(PANE_WIDTH / 2);
//		myDP.setStyle("-fx-background-color: transparent;");
//		return myDP;
//	}
//
//	private HBox addParentHBox(Tab tab) {
//		HBox hb = new HBox();
//		hb.setStyle("-fx-background-color: transparent;");
//		hb.setPrefWidth(PANE_WIDTH);
//		hb.setPrefHeight(WelcomeScreen.HEIGHT);
//		tab.setContent(hb);
//		return hb;
//	}

	public Pane getPane() {
		return myPane;
	}
}
