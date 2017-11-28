package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import authoring.drawing.BoundingPolygonCreator;
import authoring.drawing.ImageCanvas;
import engine.Actions.Move;
import engine.Actions.RemoveIntersection;
import engine.Conditions.Collision;
import engine.Conditions.KeyHeld;
import engine.Conditions.ObjectClickHeld;
import engine.Conditions.ScreenClickHeld;
import engine.sprite.AnimationSequence;
import engine.sprite.BoundedImage;
import engine.sprite.Sprite;
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
		//testDrawer(stage);
		generateGame(new BoundedImage("testImage.png"));
	}
	
	private void generateGame(BoundedImage i) {		
		GameObject obj1 = makeObject("Ob1", i, 200, 200, this::conditionAction1);
		obj1.addTag("Ob1");
		GameObject obj2 = makeObject("Ob2", i.clone(), 500, 200, this::conditionAction2);
		
		GameLayer layer = new GameLayer("Layer");

		layer.addGameObject(obj1);
		layer.addGameObject(obj2);
		
		GameWorld w = new GameWorld("World");
		w.addLayer(layer);
		
		GameMaster master = new GameMaster();
		master.addWorld(w);
		master.setCurrentWorld("World");
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

	private GameObject makeObject(String name, BoundedImage i, double x, double y, Consumer<GameObject> condActGen) {
		GameObject obj = new GameObject(name);
		obj.setCoords(x, y);
		condActGen.accept(obj);
		Sprite sprite = new Sprite();
		List<BoundedImage> images = new ArrayList<>();
		images.add(i);
		AnimationSequence animation = new AnimationSequence("Animation", images);
		sprite.addAnimationSequence(animation);
		sprite.setAnimation("Animation");
		obj.setSprite(sprite);
		return obj;
	}

	private void conditionAction1(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new Move(-3, 0));
		obj.addConditionAction(new KeyHeld(1,"Left"), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(3, 0));
		obj.addConditionAction(new KeyHeld(1,"Right"), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(0, -3));
		obj.addConditionAction(new KeyHeld(1,"Up"), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(0, 3));
		obj.addConditionAction(new KeyHeld(1,"Down"), actions1);
	}
	
	private void conditionAction2(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new RemoveIntersection());
		obj.addConditionAction(new Collision(3, "Ob1"), actions1);
	}
	
	private void testImageCanvas(Stage stage) {
		Group g = new Group();
		stage.setScene(new Scene(g));
		ImageCanvas i = new ImageCanvas(()->GameDataHandler.chooseFileForImageSave(stage));
		g.getChildren().add(i);
		stage.show();
	}

	private void testDrawer(Stage stage) throws IOException {
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		Pane bpd = new BoundingPolygonCreator(
				new Image(new GameDataHandler("Test1").addChosenFileToProject(new Stage()).toURI().toString()),
				"testImage.png", i -> generateGame(i));
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
