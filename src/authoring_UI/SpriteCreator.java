package authoring_UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Observable;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import engine.utilities.data.GameDataHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class for creating new user sprite
 * 
 * @author taekwhunchung
 *
 */

public class SpriteCreator extends Observable {

	private static final double GRID_WIDTH = 400;
	private static final double GRID_HEIGHT = 500;
	public static final String PATH = "resources/";

	private Stage myStage;
	private AuthoringEnvironmentManager myAEM;
	private GridPane myGrid;
	private MapManager myMapManager;
	private GameDataHandler myGDH;
	private TextField myNameInput;
	private SpriteObject mySpriteObject;

	protected SpriteCreator(Stage stage, AuthoringEnvironmentManager AEM, MapManager mapManager) {

		myStage = stage;
		myAEM = AEM;
		myGDH = new GameDataHandler("User Sprites");
		myGrid = new GridPane();
		myMapManager = mapManager;
		this.addObserver(myMapManager);
		setGrid();
		mySpriteObject = new SpriteObject();
	}

	private void setGrid() {
		// this.setPrefSize(GRID_WIDTH, GRID_HEIGHT);
		// this.setMaxSize(GRID_WIDTH, GRID_HEIGHT);

		// set row,col constraints
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(50);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(50);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(15);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(15);
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(5);

		myGrid.getColumnConstraints().addAll(col1, col2);
		myGrid.getRowConstraints().addAll(row1, row2, row3);
		myGrid.setGridLinesVisible(true);
		myGrid.setPrefSize(300, 200);

		addNameBox();
		addCreatebutton();

	}

	/**
	 * Returns GridPane
	 * 
	 * @return GridPane
	 */
	public GridPane getGrid() {
		return myGrid;
	}

	private void addCreatebutton() {
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.BASELINE_RIGHT);

		Button createSprite = new Button("create sprite");
		createSprite.setOnAction(e-> {
			System.out.println(getSpriteObject());
			System.out.println(getSpriteObject().getName());
			setChanged();
			notifyObservers(getSpriteObject());
		});
		buttonBox.getChildren().add(createSprite);

		myGrid.add(buttonBox, 1, 2);
	}
	
	private SpriteObject getSpriteObject() {
		System.out.println("Getting sprite object");
		System.out.println(mySpriteObject);
		return mySpriteObject;
	}

	private void addNameBox() {

		HBox nameBox = new HBox(10);
		Text name = new Text("name: ");
		TextField nameInput = new TextField("Enter Sprite Name");
		nameInput.textProperty().addListener((observable, oldValue, newValue)->{
			System.out.println("oldname: "+oldValue);
			mySpriteObject.setName(newValue);
			System.out.println("newname: "+mySpriteObject.getName());
		});
		nameBox.getChildren().addAll(name, nameInput);

		HBox imageChooseBox = new HBox(10);
		Text chooseImage = new Text("choose image file: ");
		Button chooseImageButton = new Button("choose image");
		chooseImageButton.setOnAction(e -> {
			try {
				openImage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		imageChooseBox.getChildren().addAll(chooseImage, chooseImageButton);

		myGrid.add(nameBox, 0, 1);
		myGrid.add(imageChooseBox, 0, 2);
	}

	private void openImage() throws IOException {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setTitle("Open Image");
		File file = imageChooser.showOpenDialog(myStage);
		
		if (file != null) {
			Files.copy(file.toPath(), Paths.get(PATH+file.getName()), StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println(file.getName());
			mySpriteObject.setImageURL(file.getName());
//			setChanged();
//			System.out.print(file.getName());
//			notifyObservers(file.getName());
			System.out.println("image chosen");

		}
	}

}
