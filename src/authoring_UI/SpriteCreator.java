package authoring_UI;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SpriteCreator {

	private static final double SCENE_WIDTH = 500;
	private static final double SCENE_HEIGHT = 770;

	protected SpriteCreator() {
		Stage stage = new Stage();
		Scene scene = createScene();
		stage.setScene(scene);
		stage.show();
	}

	private Scene createScene() {
		HBox creatorBox = new HBox();

		HBox nameBox = createNameBox(creatorBox);
		
		creatorBox.getChildren().add(nameBox);
		
		Scene scene = new Scene(creatorBox, SCENE_WIDTH, SCENE_HEIGHT);
		return scene;
	}

	private HBox createNameBox(HBox root) {
		HBox nameBox = new HBox();
		Text name = new Text("name");
		TextField nameInput = new TextField("Enter Sprite Name");
		nameBox.getChildren().addAll(name, nameInput);
		return nameBox;
	}

}
