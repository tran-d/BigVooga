package engine.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import authoring.drawing.BoundingPolygonCreator;
import engine.Action;
import engine.Condition;
import engine.GameLayer;
import engine.GameMaster;
import engine.GameObject;
import engine.GameObjectFactory;
import engine.GameWorld;
import engine.Holdable;
import engine.Inventory;
import engine.Actions.changeObject.DisplayInventory;
import engine.Actions.movement.Move;
import engine.Actions.movement.Rotate;
import engine.operations.booleanops.KeyHeld;
import engine.operations.booleanops.KeyPressed;
import engine.operations.booleanops.ObjectClickHeld;
import engine.operations.doubleops.Value;
import engine.operations.gameobjectops.Self;
import engine.operations.stringops.SelfString;
import engine.operations.vectorops.VectorHeadingOf;
import engine.operations.vectorops.VectorScale;
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
		// testCollisions(stage);
		// testData(stage);
		// testImageCanvas(stage);
		// testDrawer(stage);
		generateGame(stage);
	}

	public void generateGame(Stage stage) {
		generateGame("Test1", stage);
	}

	public void generateGame(String name, Stage stage) {
		GameObjectFactory blueprints = new GameObjectFactory();
		BoundedImage i = new BoundedImage("skeptical.jpg");
		GameObject obj1 = makeObject("Ob1", i, 120, 150, this::conditionAction1);
		obj1.addTag("Ob1");
		obj1.addTag("Player");
		obj1.setSize(200, 100);
		
		Inventory inv = obj1.getInventory();
		inv.setX(300);
		inv.setY(300);
		BoundedImage b = new BoundedImage("pane.png");
		b.setSize(400, 200);
		inv.setPane(b);
		for(int j = 0; j < 10; j++)
		{
			BoundedImage bi = new BoundedImage("skeptical.jpg");
			AnimationSequence as = new AnimationSequence("Hi", Arrays.asList(bi));
			Sprite s = new Sprite();
			s.addAnimationSequence(as);
			s.setAnimation("Hi");
			
			Holdable invObj = new Holdable(s);
			invObj.setSelectActions(Arrays.asList(new Rotate(new Self(), new Value(45))));
			inv.addObject(invObj);
		}
		
		i = new BoundedImage("testImage.gif");
		GameObject obj2 = makeObject("Ob1", i, 200, 150, this::conditionAction2);
		obj1.addTag("Ob1");
		obj1.addTag("Player");
		obj1.setSize(200, 100);
		
		
			

		GameLayer la = new GameLayer("Layer");
		la.addGameObject(obj1);
		la.addGameObject(obj2);

		GameWorld w = new GameWorld("World");
		w.addLayer(la);
		
		GameMaster master = new GameMaster();
		master.addWorld(w);
		master.setNextWorld("World");
		//try {
			new GameDataHandler(name, stage).saveGame(master);
		//} //catch (IOException e) {
			//e.printStackTrace();
		//}

		try {
			System.out.println("Trying to load game");
			new GameDataHandler(name, stage).loadGame().setNextWorld("World");
		} catch (FileNotFoundException e) {
			System.out.println("Error");
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
	
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new VectorScale(new VectorHeadingOf(new Self()), new Value(3))));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("W"))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(5)));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("D"))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(-5)));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("A"))), actions1);

		actions1 = new ArrayList<Action>();
		actions1.add(new DisplayInventory(new Self()));
		obj.addConditionAction(new Condition(2, new KeyPressed(new SelfString("I"))), actions1);
	}

	private void conditionAction2(GameObject obj) {

		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(5)));
		obj.addConditionAction(new Condition(2, new ObjectClickHeld(new Self())), actions1);
		
	}

	private void conditionAction3(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
	}

	private void testDrawer(Stage stage) throws IOException {
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		File f = new GameDataHandler("Bounds Test", stage).addChosenFileToProject(new Stage());
		System.out.println(f.getName());
		Pane bpd = new BoundingPolygonCreator(new Image(f.toURI().toString()), f.getName(),
				i -> generateGame("Bounds Test", stage));
		g.getChildren().add(bpd);
		stage.show();
	}

	private void testData(Stage stage) throws IOException, FileNotFoundException, URISyntaxException {
		GameDataHandler data = new GameDataHandler("SaverTest3", stage);
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

		scene.setOnKeyPressed(e -> {
			g.getChildren().remove(p2);
			g.getChildren()
					.add(makePolygon(((BoundingPolygon) poly2.getTranslated(vec.getX(), vec.getY())).getVertices()));
		});
	}

	private static Polygon makePolygon(List<Point2D> points) {
		double[] locs = new double[points.size() * 2];
		for (int i = 0; i < points.size(); i++) {
			locs[2 * i] = points.get(i).getX();
			locs[2 * i + 1] = points.get(i).getY();
		}
		return new Polygon(locs);
	}
}
