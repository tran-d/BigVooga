import java.io.IOException;
import java.util.function.Consumer;

import authoring.drawing.BoundingPolygonCreator;
import engine.sprite.BoundedImage;
import engine.utilities.data.GameDataHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BoundingBoxTester {
	
	public static void getBoundedImage(Stage stage, Consumer<BoundedImage> next) {
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		BoundingPolygonCreator bpd;
		try {
			String s = new GameDataHandler("Test1").addChosenFileToProject(new Stage()).toURI().toString();
			bpd = new BoundingPolygonCreator(new Image(s),
					s, next);
			g.getChildren().add(bpd);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.show();
	}
}
