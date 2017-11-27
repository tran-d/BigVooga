package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import authoring.drawing.BoundingPolygonCreator;
import authoring.drawing.ImageCanvas;
import engine.Actions.Move;
import engine.Conditions.KeyPressed;
import engine.utilities.collisions.BoundingPolygon;
import engine.utilities.data.GameDataHandler;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class EngineTester extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//testCollisions(stage);
		//testData(stage);
		//testImageCanvas(stage);
		testDrawer(stage);
	}
	
	private void generateGame(BoundedImage i) {
		GameMaster master = new GameMaster();
		GameWorld w = new GameWorld("World");
		GameObject obj = new GameObject();
		obj.setCoords(200, 200);
		List<Action> actions = new ArrayList<Action>();
		actions.add(new Move(-1, 0));
		obj.addConditionAction(new KeyPressed(1,"LEFT"), actions);
		Sprite sprite = new Sprite();
		List<BoundedImage> images = new ArrayList<>();
		images.add(i);
		AnimationSequence animation = new AnimationSequence("Animation", images);
		sprite.addAnimationSequence(animation);
		obj.setSprite(sprite);
		
		w.addGameObject(obj);
		master.addWorld(w);
		try {
			new GameDataHandler("Test1").saveGame(master);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			new GameDataHandler("Test1").loadGame().setCurrentWorld("World");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void testImageCanvas(Stage stage) {
		Group g = new Group();
		stage.setScene(new Scene(g));
		ImageCanvas i = new ImageCanvas(()->GameDataHandler.chooseFileForImageSave(stage));
		g.getChildren().add(i);
		stage.show();
	}

	private void testDrawer(Stage stage) {
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		Pane bpd = new BoundingPolygonCreator(
				new Image(GameDataHandler.chooseFile(new Stage()).toURI().toString()),
				"Irrelevant, for now", i -> generateGame(i));
		g.getChildren().add(bpd);
		stage.show();
	}

	private void testData(Stage stage) throws IOException, FileNotFoundException, URISyntaxException {
		GameDataHandler data = new GameDataHandler("SaverTest3");
		data.addChosenFileToProject(stage);
		data.saveGame(new GameMaster());
		data.loadGame();
		data.getImage("HexGrid.PNG");
	}

	private static void testCollisions(Stage stage) {
		List<Point2D> vertices1 = new ArrayList<>();
		vertices1.add(new Point2D(20, 20));
		vertices1.add(new Point2D(120, 20));
		vertices1.add(new Point2D(120, 120));
		vertices1.add(new Point2D(20, 120));
		BoundingPolygon poly1 = new BoundingPolygon(vertices1);
		List<Point2D> vertices2 = new ArrayList<>();
		vertices2.add(new Point2D(70, 115));
		vertices2.add(new Point2D(120, 220));
		vertices2.add(new Point2D(70, 320));
		vertices2.add(new Point2D(20, 220));
		BoundingPolygon poly2 = new BoundingPolygon(vertices2);
		Point2D vec = poly2.checkCollision(poly1);
		System.out.println(vec);
		
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		Polygon p1 = makePolygon(vertices1);
		Polygon p2 = makePolygon(vertices2);
		g.getChildren().add(p1);
		g.getChildren().add(p2);
		stage.show();
		
//		long time = System.currentTimeMillis();
//		for (int i = 0; i < 1000000; i++)
//			poly1.checkCollision(poly2);
//		System.out.println(System.currentTimeMillis() - time);
		
		scene.setOnKeyPressed(e->{
		g.getChildren().remove(p2);
		g.getChildren().add(makePolygon(((BoundingPolygon) poly2.getTranslated(vec.getX(), vec.getY())).getVertices()));});
	}
	
	private static Polygon makePolygon(List<Point2D> points) {
		double[] locs = new double[points.size()*2];
		for(int i = 0; i < points.size(); i++) {
			locs[2*i] = points.get(i).getX();
			locs[2*i+1] = points.get(i).getY();
		}
		return new Polygon(locs);
	}
}
