import java.util.ArrayList;
import java.util.List;

import engine.sprite.BoundedImage;
import engine.testing.EngineTester;
import engine.testing.EngineTester2;
import javafx.application.Application;
import javafx.stage.Stage;

public class Demo extends Application {

	public static void main(String[] args) {
		launch(args);
		//Main.main(args);
	}

	private List<BoundedImage> images = new ArrayList<>();
	
	@Override
	public void start(Stage stage) throws Exception {
		new EngineTester().generateGame();
		new EngineTester2().generateGame();
		new EngineTester().testImageCanvas(stage);
	}
}
