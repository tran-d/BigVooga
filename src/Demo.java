import authoring.drawing.ImageCanvasPane;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Demo extends Application {

	public static void main(String[] args) {
		launch(args);
		//Main.main(args);
	}

	public void generateGame() {
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		testImageCanvas(stage);
//		new EngineTester().generateGame();
//		new EngineTester2().generateGame();
//		new ActionConditionDemo().generateGame();
//		new RPGDemo().generateGame();
	}
	
	public void testImageCanvas(Stage stage) {
		Group g = new Group();
		stage.setScene(new Scene(g));
		ImageCanvasPane p = new ImageCanvasPane(400, 400);
		g.getChildren().add(p);
		stage.show();
	}
}
