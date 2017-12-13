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
import engine.Inventory;
import engine.Actions.changeObject.SetAnimationSequence;
import engine.Actions.global.ChangeWorld;
import engine.Actions.global.ExitToMenu;
import engine.Actions.movement.Move;
import engine.Actions.movement.RemoveIntersection;
import engine.Actions.variableSetting.ChangeBoolean;
import engine.Actions.variableSetting.ChangeDouble;
import engine.operations.booleanops.And;
import engine.operations.booleanops.BooleanValue;
import engine.operations.booleanops.BooleanVariableOf;
import engine.operations.booleanops.CollisionByTag;
import engine.operations.booleanops.KeyHeld;
import engine.operations.booleanops.KeyPressed;
import engine.operations.booleanops.Not;
import engine.operations.booleanops.ObjectClicked;
import engine.operations.booleanops.ObjectMouseHover;
import engine.operations.doubleops.DoubleVariableOf;
import engine.operations.doubleops.Sum;
import engine.operations.doubleops.Value;
import engine.operations.doubleops.XOf;
import engine.operations.gameobjectops.ByName;
import engine.operations.gameobjectops.Self;
import engine.operations.stringops.SelfString;
import engine.operations.vectorops.BasicVector;
import engine.operations.vectorops.LocationOf;
import engine.operations.vectorops.UnitVector;
import engine.operations.vectorops.VectorDifference;
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
		new GameDataHandler("aa").loadGame();
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		//testCollisions(stage);
		// testData(stage);
		//testImageCanvas(stage);
		//testDrawer(stage);
//		generateGame(stage);
//		slackTest(stage);
	}
	
	public void slackTest(Stage stage){
		try {
			System.out.println("Trying to load game");
			new GameDataHandler("aa").loadGame().setNextWorld("Map1");
		} catch (FileNotFoundException e) {
			System.out.println("Error");
		}
	}

	public void generateGame(Stage stage) {
		generateGame("Terraria-ish", stage);
	}

	public void generateGame(String name, Stage stage) {
		GameObjectFactory blueprints = new GameObjectFactory();
		
		BoundedImage i = new BoundedImage("character.png");
		GameObject obj1 = makeObject("Player", i, 0, 0, this::conditionAction1);
		obj1.addTag(GameObject.CAMERA_TAG);
		obj1.addTag("Player");
		obj1.setSize(64, 64);
		i = new BoundedImage("character_left.png");
		AnimationSequence as = new AnimationSequence("Left", Arrays.asList(i));
		obj1.getSprite().addAnimationSequence(as);
		Inventory inv = obj1.getInventory();
		blueprints.addBlueprint(obj1);
		
		i = new BoundedImage("Background.png");		
	
		GameObject bg = makeObject("background", i, 100, 0, this::conditionAction2);		
		bg.setSize(2000, 1400);
		
		GameObject obj2 = makeObject("Brick", new BoundedImage("rock.png"), 0, 0, this::conditionAction2);
		obj2.addTag("Wall");
		obj2.setSize(64, 64);
		blueprints.addBlueprint(obj2);
		
		GameObject zombo = makeObject("Zombo", new BoundedImage("Zombie.png"), -5500, 400-65, this::conditionAction3);
		zombo.setSize(39, 65);
		
		GameObject button = new GameObject("Button");
		BoundedImage unpr = new BoundedImage("button-unpressed.png");
		as = new AnimationSequence("Unpressed", Arrays.asList(unpr));
		Sprite s = new Sprite();
		s.addAnimationSequence(as);
		BoundedImage pr = new BoundedImage("button-pressed.png");
		as = new AnimationSequence("Pressed", Arrays.asList(pr));
		s.addAnimationSequence(as);
		s.setAnimation("Unpressed");
		button.setSprite(s);
		
		button.setCoords(500, 400);
		button.setSize(200, 100);
		buttonConditionAction(button);
		
		i = new BoundedImage("start.png");		
		
		GameObject menu = makeObject("background", i, 1000, 350, this::conditionAction2);		
		menu.setSize(1000, 700);
		menu.setLocation(new Point2D(500, 350));
			


		
		GameLayer background = new GameLayer("Background");
		background.addGameObject(bg);
		GameLayer la = new GameLayer("Layer");
		la.addGameObject(zombo);
		la.addGameObject(obj1);
		
		for (int j = -40; j <15; j++)
		{
			GameObject temp = blueprints.getInstanceOf("Brick");
			temp.setCoords(j*64, 400);
			la.addGameObject(temp);
			temp = blueprints.getInstanceOf("Brick");
			temp.setCoords(-40*64, 400-64*j);
			la.addGameObject(temp);
			temp = blueprints.getInstanceOf("Brick");
			temp.setCoords(15*64, 400-64*j);
			la.addGameObject(temp);
		}

		GameWorld w = new GameWorld("World");
		w.addLayer(background);
		w.addLayer(la);
		
		GameLayer startL = new GameLayer();
		startL.addGameObject(button);
		GameWorld start = new GameWorld("Start");
		start.addLayer(startL);
		start.addLayer(new GameLayer("Background"));
		start.getLayers().get(1).addGameObject(menu);
		
		GameMaster master = new GameMaster();
		master.addWorld(w);
		master.addWorld(start);
		master.setNextWorld("Start");
		//try {
			new GameDataHandler(name).saveGame(master);
		//} //catch (IOException e) {
			//e.printStackTrace();
		//}

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
		AnimationSequence animation = new AnimationSequence("Right", images);
		sprite.addAnimationSequence(animation);
		sprite.setAnimation("Right");
		obj.setSprite(sprite);
		return obj;
	}

	private void conditionAction1(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1 = new ArrayList<Action>();
		actions1.add(new ChangeDouble(new Self(), new SelfString("Gravity"), new Value(-5)));
		actions1.add(new ChangeBoolean(new Self(), new SelfString("OnGround"), new BooleanValue(false)));
		obj.addConditionAction(new Condition(7, new And(new BooleanVariableOf(new Self(), 
				new SelfString("OnGround")), new KeyPressed(new SelfString("W")))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new BasicVector(new Value(-5), new Value(0))));
		actions1.add(new SetAnimationSequence(new Self(), new SelfString("Left")));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("A"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new BasicVector(new Value(5), new Value(0))));
		actions1.add(new SetAnimationSequence(new Self(), new SelfString("Right")));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("D"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new RemoveIntersection());
		actions1.add(new ChangeDouble(new Self(), new SelfString("Gravity"), new Value(0)));
		actions1.add(new ChangeBoolean(new Self(), new SelfString("OnGround"), new BooleanValue(true)));
		obj.addConditionAction(new Condition(3, new CollisionByTag(new SelfString("Wall"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new RemoveIntersection());
		obj.addConditionAction(new Condition(4, new CollisionByTag(new SelfString("Wall"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new ChangeDouble(new Self(), new SelfString("Gravity"), 
				new Sum(new DoubleVariableOf(new Self(), new SelfString("Gravity")), new Value(.15))));
		obj.addConditionAction(new Condition(1, new BooleanValue(true)), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new BasicVector(new Value(0), new DoubleVariableOf(new Self(), new SelfString("Gravity")))));
		obj.addConditionAction(new Condition(1, new BooleanValue(true)), actions1);
		
		
	}

	private void conditionAction2(GameObject obj) {

		List<Action> actions1 = new ArrayList<Action>();
		
	}

	private void conditionAction3(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new BasicVector(
				new XOf(new VectorScale(new UnitVector(new VectorDifference(new LocationOf(new ByName(new SelfString("Player"))), 
						new LocationOf( new Self()))), new Value(4))), new Value(0))));
		obj.addConditionAction(new Condition(1, new BooleanValue(true)), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new ExitToMenu());
		obj.addConditionAction(new Condition(3, new CollisionByTag(new SelfString("Player"))), actions1);
	}
	
	private void buttonConditionAction(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new SetAnimationSequence(new Self(), new SelfString("Pressed")));
		obj.addConditionAction(new Condition(2, new ObjectMouseHover(new Self())), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new SetAnimationSequence(new Self(), new SelfString("Unpressed")));
		obj.addConditionAction(new Condition(2, new Not( new ObjectMouseHover(new Self()))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new ChangeWorld(new SelfString("World")));
		obj.addConditionAction(new Condition(2, new ObjectClicked(new Self())), actions1);
	}

	private void testDrawer(Stage stage) throws IOException {
		Group g = new Group();
		Scene scene = new Scene(g);
		stage.setScene(scene);
		File f = new GameDataHandler("Terraria-ish").addChosenFileToProject(new Stage());
		System.out.println(f.getName());
		Pane bpd = new BoundingPolygonCreator(new Image(f.toURI().toString()), f.getName(),
				i -> generateGame("Terraria-ish", stage));
		g.getChildren().add(bpd);
		stage.show();
	}

	private void testData(Stage stage) throws IOException, FileNotFoundException, URISyntaxException {
		GameDataHandler data = new GameDataHandler("SaverTest3");
		data.addChosenFileToProject(stage);
		data.saveGame(new GameMaster());
		data.loadGame();
		data.getImage("skeptical.PNG");
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
