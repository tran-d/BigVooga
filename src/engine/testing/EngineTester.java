package engine.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import engine.Actions.changeObject.RemoveFromWorld;
import engine.Actions.global.TransferObjectToWorld;
import engine.Actions.movement.Move;
import engine.Actions.movement.Rotate;
import engine.operations.booleanops.And;
import engine.operations.booleanops.BooleanValue;
import engine.operations.booleanops.CollisionByTag;
import engine.operations.booleanops.KeyHeld;
import engine.operations.booleanops.KeyPressed;
import engine.operations.booleanops.Not;
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
		generateGame();
	}

	public void generateGame() {
		generateGame("WorldSwitchingTest", new BoundedImage(
				"C:\\Users\\nikbr\\Desktop\\eclipse\\My_Workspace\\voogasalad_bigvooga\\data\\UserCreatedGames\\WorldSwitchingTest\\testImage.gif"));
	}

	public void generateGame(String name, BoundedImage i) {
		GameObjectFactory blueprints = new GameObjectFactory();
		GameObject obj1 = makeObject("Ob1", i, 120, 150, this::conditionAction1);
		obj1.addTag("Player");
		obj1.setSize(50, 50);

		blueprints.addBlueprint(obj1);

		BoundedImage image = new BoundedImage("C:\\Users\\nikbr\\Desktop\\eclipse\\My_Workspace\\voogasalad_bigvooga\\data\\UserCreatedGames\\WorldSwitchingTest\\skeptical.jpg");
		GameObject obj2= makeObject("Ob2", image, 120, 150, this::conditionAction2);
		obj2.addTag("Killer");
		obj2.setSize(100, 100);
		
		String wallpath = "C:\\Users\\nikbr\\Desktop\\eclipse\\My_Workspace\\voogasalad_bigvooga"
				+ "\\data\\UserCreatedGames\\WorldSwitchingTest\\Smiley.png";
		
		GameLayer la = new GameLayer("Layer");
		la.addGameObject(obj1);
		for(int j = 0; j < 10; j++)
		{
			image = new BoundedImage(wallpath);
			GameObject wall = makeObject("Wall", image, 800, j*100, this::conditionAction3);
			wall.addTag("Block");
			wall.setSize(100, 100);
			la.addGameObject(wall);
		}
		
		GameWorld w = new GameWorld("World");
		w.addLayer(la);
		
		
		GameWorld x = new GameWorld("Second World");
		la = new GameLayer("Layer");
		la.addGameObject(obj2);
		x.addLayer(la);
		
		GameMaster master = new GameMaster();
		master.addWorld(w);
		master.addWorld(x);
		master.setNextWorld("World");
		
		new GameDataHandler(name).saveGame(master);

		try {
			System.out.println("Trying to load game");
			new GameDataHandler(name).loadGame().setNextWorld("World");
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
		actions1.add(new TransferObjectToWorld(new Self(), new SelfString("Second World"), new SelfString("Layer"), new BooleanValue(true)));
		obj.addConditionAction(new Condition(2, new KeyPressed(new SelfString("T"))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new VectorScale(new VectorHeadingOf(new Self()), new Value(2))));
		obj.addConditionAction(new Condition(2, new And( new Not(new CollisionByTag(new SelfString("Block"))), new KeyHeld(new SelfString("W")))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(2)));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("D"))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(-2)));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("A"))), actions1);

		actions1 = new ArrayList<Action>();
		actions1.add(new TransferObjectToWorld(new Self(), new SelfString("World"), new SelfString("Layer"), new BooleanValue(true)));
		obj.addConditionAction(new Condition(2, new KeyPressed(new SelfString("R"))), actions1);

	}

	private void conditionAction2(GameObject obj) {

		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new VectorScale(new VectorHeadingOf(new Self()), new Value(2))));
		obj.addConditionAction(new Condition(2, new CollisionByTag(new SelfString("Player"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new TransferObjectToWorld(new Self(), new SelfString("World"), new SelfString("Layer"), new BooleanValue(false)));
		obj.addConditionAction(new Condition(2, new KeyPressed(new SelfString("J"))), actions1);
		
	}

	private void conditionAction3(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new RemoveFromWorld(new Self()));
		obj.addConditionAction(new Condition(2, new CollisionByTag(new SelfString("Killer"))), actions1);
	}

	private void testDrawer(Stage stage) throws IOException {
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		File f = new GameDataHandler("Bounds Test").addChosenFileToProject(new Stage());
		System.out.println(f.getName());
		Pane bpd = new BoundingPolygonCreator(new Image(f.toURI().toString()), f.getName(),
				i -> generateGame("Bounds Test", i));
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
