package ActionConditionClasses;

import java.util.ResourceBundle;

import authoring.drawing.ImageCanvasPane;
import engine.utilities.data.GameDataHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

	public static void main(String[] args) {
		launch(args);
		// Main.main(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		testImageCanvas(stage);
		// new EngineTester().generateGame();
		// new EngineTester2().generateGame();
		// new ActionConditionDemo().generateGame();
		// new RPGDemo().generateGame();
	}

	public void testImageCanvas(Stage stage) {
		Group g = new Group();
		Scene scene = new Scene(g);

		stage.setScene(scene);
		ImageCanvasPane p = new ImageCanvasPane(ResourceBundle.getBundle("authoring.drawing.drawingTools.drawingTools"), 400, 400,
				() -> GameDataHandler.chooseFileForImageLoad(stage));
		g.getChildren().add(p);
		stage.show();
	}
}
