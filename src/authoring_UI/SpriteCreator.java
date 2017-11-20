package authoring_UI;

import java.io.File;

import authoring.AuthoringEnvironmentManager;
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

public class SpriteCreator extends GridPane {

	private static final double GRID_WIDTH = 400;
	private static final double GRID_HEIGHT = 500;

	private Stage myStage;
	private AuthoringEnvironmentManager myAEM;

	protected SpriteCreator(Stage stage, AuthoringEnvironmentManager AEM) {
		myStage = stage;
		myAEM = AEM;
		setGrid();
	}

	private void setGrid() {
//		this.setPrefSize(GRID_WIDTH, GRID_HEIGHT);
//		this.setMaxSize(GRID_WIDTH, GRID_HEIGHT);
		// set row,col constraints
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(50);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(50);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(15);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(80);
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(5);

		this.getColumnConstraints().addAll(col1, col2);
		this.getRowConstraints().addAll(row1, row2, row3);
		this.setGridLinesVisible(true);

		addNameBox();
		addCreatebutton();

		// myGrid.add(test, 0, 1);

		// HBox creatorBox = new HBox();
		//
		// HBox nameBox = createNameBox(creatorBox);
		//
		// creatorBox.getChildren().add(nameBox);

	}
	


	private void addCreatebutton() {
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.BASELINE_RIGHT);

		Button createSprite = new Button("create sprite");
		buttonBox.getChildren().add(createSprite);

		this.add(buttonBox, 1, 2);
	}

	private void addNameBox() {

		HBox nameBox = new HBox(10);
		Text name = new Text("name: ");
		TextField nameInput = new TextField("Enter Sprite Name");
		nameBox.getChildren().addAll(name, nameInput);

		HBox imageChooseBox = new HBox(10);
		Text chooseImage = new Text("choose image file: ");
		Button chooseImageButton = new Button("choose image");
		chooseImageButton.setOnAction(e -> openImage());

		imageChooseBox.getChildren().addAll(chooseImage, chooseImageButton);

		this.add(nameBox, 0, 1);
		this.add(imageChooseBox, 0, 2);
	}

	private void openImage() {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setTitle("YES");
		File file = imageChooser.showOpenDialog(myStage);
		if (file != null) {
			System.out.println("image chosen");
			System.out.println(file.toURI());
		}
	}

}
