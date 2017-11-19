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
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		testCollisions();
		//launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		testData(stage);
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
