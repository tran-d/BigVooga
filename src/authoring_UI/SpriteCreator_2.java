package authoring_UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Observable;
import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.SpriteParameterTabsAndInfo;
import authoring.SpritePanels.GameElementSelector;
import engine.utilities.data.GameDataHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class for creating new user sprite
 * 
 * @author taekwhunchung
 *
 */

public class SpriteCreator_2 extends Observable {

	private static final double GRID_WIDTH = 400;
	private static final double GRID_HEIGHT = 500;
	public static final String PATH = "resources/";
	private Stage myStage;
	private VBox myCreateSpriteBox;
	private GridPane myGrid;
	private GameElementSelector mySpriteManager;
	private GameDataHandler myGDH;
	private SpriteObject mySpriteObject;
	private File mySpriteFile;
	private SpriteParameterTabsAndInfo mySPTAI;
	private Alert myErrorMessage;
	Consumer<Button> myConsumer;
	private AuthoringEnvironmentManager myAEM;
	private TextField nameInput;
	private VBox imageChooseBox;

	protected SpriteCreator_2(Stage stage, GameElementSelector spriteManager, AuthoringEnvironmentManager AEM) {

//		myStage = stage;
		mySPTAI = new SpriteParameterTabsAndInfo();
		myAEM = AEM;
		myGDH = myAEM.getGameDataHandler();
		myCreateSpriteBox = new VBox();
		myCreateSpriteBox.setSpacing(5);
//		myGrid = new GridPane();
		mySpriteManager = spriteManager;
		addObserver(mySpriteManager);
		setGrid();
		
		mySpriteObject = new SpriteObject();
		mySPTAI.setSpriteObject(mySpriteObject);
		
	}

	private void setGrid() {
		// set row,col constraints
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(40);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(60);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(10);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(60);
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(30);

//		myGrid.getColumnConstraints().addAll(col1, col2);
//		myGrid.getRowConstraints().addAll(row1, row2, row3);
//		myGrid.setGridLinesVisible(true);
		
		addNameBox();
		addImageBox();
		addLoadSpriteButton();
		addSpriteParametersTabPane();
		addErrorMessage();
		addCreatebutton();
	}

	
	private void addSpriteParametersTabPane() {
		VBox myParamVBox = new VBox();
		TabPane myParamTabs = mySPTAI.getTabPane();
		myParamVBox.getChildren().add(myParamTabs);
		myCreateSpriteBox.getChildren().add(myParamVBox);
//		myGrid.add(myParamVBox, 0, 3);
	}
	
	private void addErrorMessage(){
		 myErrorMessage = new Alert(AlertType.ERROR);
		 myErrorMessage.setTitle("Error");
		 myErrorMessage.setContentText("");
//		 myGrid.add(myErrorMessage, 1, 2);
	}
	
	private void setErrorMessage(String message){
		myErrorMessage.setContentText(message);
		myErrorMessage.showAndWait();
	}

	protected VBox getVBox() {
		return myCreateSpriteBox;
	}
	/**
	 * Returns GridPane
	 * 
	 * @return GridPane
	 */
	public GridPane getGrid() {
//		ScrollPane SP = new ScrollPane();
//		SP.setContent(myGrid);
//		return SP;
		return myGrid;
	}
	
	public ScrollPane getScrollPane(){
		ScrollPane SP = new ScrollPane();
		SP.setContent(getGrid());
		return SP;
	}

	private void addCreatebutton() {
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.BASELINE_RIGHT);

		Button createSprite = new Button("Finish Creating");
		createSprite.setOnAction(e-> {
			System.out.println(getSpriteObject());
			System.out.println("Spritename when creating: "+getSpriteObject().getName());
			mySPTAI.apply();
//			copySpriteFileToProject();
			setChanged();
			notifyObservers(getSpriteObject());
			myConsumer.accept(null);
		});
		buttonBox.getChildren().add(createSprite);


//		myGrid.add(buttonBox, 1, 0);

		
		myCreateSpriteBox.getChildren().add(buttonBox);
//		myGrid.add(buttonBox, 0, 0);
	}
	
	private SpriteObject getSpriteObject() {
		System.out.println("Getting sprite object");
		System.out.println(mySpriteObject);
		return mySpriteObject;
	}

	private void addNameBox() {

		HBox nameBox = new HBox(10);
		Text name = new Text("name: ");
		nameInput = new TextField("Enter Sprite Name");
		nameInput.textProperty().addListener((observable, oldValue, newValue)->{
			System.out.println("oldname: "+oldValue);
			mySpriteObject.setName(newValue);
			System.out.println("newname: "+mySpriteObject.getName());
		});
		nameBox.getChildren().addAll(name, nameInput);

		
	
		myCreateSpriteBox.getChildren().add(nameBox);
//		myGrid.add(nameBox, 0, 1);
		
	}
	
	private void addImageBox(){
		imageChooseBox = new VBox(10);
		Text chooseImage = new Text("choose image file: ");
		Button chooseImageButton = new Button("choose image");
//		ImageView im = new ImageView(new Image("pikachu.png"));
//		im.setFitWidth(45);
//		im.setFitHeight(45);
		chooseImageButton.setOnAction(e -> {
			try {
				openImage();
				addSpriteImage();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				setErrorMessage("Choose a valid image file");
			}
		});
		imageChooseBox.getChildren().addAll(chooseImage, chooseImageButton);

//		myGrid.add(imageChooseBox, 0, 2);

//		myGrid.add(nameBox, 0, 1);
//		myGrid.add(imageChooseBox, 0, 2);
		
		myCreateSpriteBox.getChildren().add(imageChooseBox);
		
	}
	
	private void addSpriteImage(){
		ImageView img = new ImageView(new Image(mySpriteObject.getImageURL()));
		img.setFitWidth(70);
		img.setFitHeight(70);
		if (imageChooseBox.getChildren().get(1) instanceof ImageView){
			imageChooseBox.getChildren().remove(1);
		}
		imageChooseBox.getChildren().add(1, img);
	}
	
	private void addLoadSpriteButton() {
		HBox spriteChooseBox = new HBox(10);
		Text chooseSprite = new Text("choose sprite to load from: ");
		Button chooseSpriteButton = new Button("choose sprite");
		chooseSpriteButton.setOnAction(e -> {
			try {
				chooseSpriteFileandLoadSprite();
				addSpriteImage();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				setErrorMessage("Choose a valid Sprite");
			}
		});

		spriteChooseBox.getChildren().addAll(chooseSprite, chooseSpriteButton);
		myCreateSpriteBox.getChildren().add(spriteChooseBox);
//		myGrid.add(spriteChooseBox, 0, 0);
	}

	private void chooseSpriteFileandLoadSprite() throws Exception {
		File newChosenSpriteFile = myGDH.chooseSpriteFile(myStage);
		mySpriteFile = newChosenSpriteFile;
		AbstractSpriteObject tempSprite = myGDH.loadSprite(newChosenSpriteFile);
		if (tempSprite instanceof SpriteObject){
		mySpriteObject = (SpriteObject) tempSprite;
		} else {
			throw new Exception("Sprite is not a valid SpriteObject");
		}
		mySPTAI.setSpriteObject(mySpriteObject);
		nameInput.setText(mySpriteObject.getName());
	}

	private void openImage() throws IOException {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setInitialDirectory(new File("resources/"));
		imageChooser.setTitle("Open Image");
		File file = imageChooser.showOpenDialog(myStage);
		
		
		if (file != null) {
			System.out.println("pathhhh " + file.getAbsolutePath());

			Files.copy(file.toPath(), Paths.get(PATH+file.getName()), StandardCopyOption.REPLACE_EXISTING);
			System.out.println(file.getName());
			File absFile = new File(file.getAbsolutePath());
			mySpriteObject.setImageURL(absFile.getName());
//			setChanged();
//			System.out.print(file.getName());
//			notifyObservers(file.getName());
			System.out.println("image chosen");

		}

	}
	
	public void onCreate(Consumer<Button> button){
		myConsumer = button;
	}

}
