import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import engine.GameMaster;
import engine.utilities.collisions.BoundingPolygon;
import engine.utilities.data.GameDataHandler;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		testDrawer(stage);
	}
	
	private void testDrawer(Stage stage) {
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		Pane bpd = new BoundingPolygonDrawer(new Image(new GameDataHandler("Test").chooseFile(new Stage()).toURI().toString()));
		bpd.setLayoutX(50);
		bpd.setLayoutY(50);
		g.getChildren().add(bpd);
		stage.show();
	}

	private void testData(Stage stage) throws IOException, FileNotFoundException, URISyntaxException {
		GameDataHandler data = new GameDataHandler("SaverTest3");
		data.addChosenFileToProject(stage);
		data.saveGame(new GameMaster(50));
		data.loadGame();
		data.getImage("HexGrid.PNG");
	}
	
	private static void testCollisions() {
		List<Point2D> vertices1 = new ArrayList<>();
		vertices1.add(new Point2D(0,0));
		vertices1.add(new Point2D(0,1));
		vertices1.add(new Point2D(1,1));
		vertices1.add(new Point2D(1,0));
		BoundingPolygon poly1 = new BoundingPolygon(vertices1);
		List<Point2D> vertices2 = new ArrayList<>();
		vertices2.add(new Point2D(.5, .95));
		vertices2.add(new Point2D(0,2));
		vertices2.add(new Point2D(.5,3));
		vertices2.add(new Point2D(1,2));
		BoundingPolygon poly2 = new BoundingPolygon(vertices2);
		long time = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++)
			poly1.checkCollision(poly2);
		System.out.println(System.currentTimeMillis()-time);
	}
}
