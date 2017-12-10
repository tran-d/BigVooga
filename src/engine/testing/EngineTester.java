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
import engine.Holdable;
import engine.Actions.changeObject.DisplayInventory;
import engine.Actions.movement.Move;
import engine.Actions.movement.MoveTo;
import engine.Actions.movement.Rotate;
import engine.operations.booleanops.KeyHeld;
import engine.operations.booleanops.KeyPressed;
import engine.operations.doubleops.Value;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.gameobjectops.Self;
import engine.operations.stringops.SelfString;
import engine.operations.vectorops.BasicVector;
import engine.operations.vectorops.VectorHeadingOf;
import engine.operations.vectorops.VectorOperation;
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
import javafx.scene.input.KeyCode;
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
		generateGame("Test1", new BoundedImage(
				"/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/Link.png"));
	}

	public void generateGame(String name, BoundedImage i) {
		GameObjectFactory blueprints = new GameObjectFactory();
		GameObject obj1 = makeObject("Ob1", i, 120, 150, this::conditionAction1);
		obj1.addTag("Ob1");
		obj1.addTag("Player");
		obj1.setSize(200, 100);
		
		GameObject obj2 = makeObject("Ob2", new BoundedImage(
				"/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/Link.png"), 200, 150, this::conditionAction2);
		obj2.addTag("Ob2");
		obj2.setSize(200, 100);
		
		GameObject obj3 = makeObject("Ob3", new BoundedImage(
				"/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/Link.png"), 100, 300, this::conditionAction2);
		obj3.addTag("Ob3");
		obj3.setSize(200, 100);
		
		GameObject obj4 = makeObject("Ob4", new BoundedImage(
				"/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/Link.png"), 75, 275, this::conditionAction2);
		obj4.addTag("Ob4");
		obj4.setSize(200, 100);
	
		blueprints.addBlueprint(obj1);
		blueprints.addBlueprint(obj2);
		blueprints.addBlueprint(obj3);
		blueprints.addBlueprint(obj4);
		
		BoundedImage t = new BoundedImage("/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/Link.png");
		List<BoundedImage> l = new ArrayList<BoundedImage>();
		l.add(t);
		AnimationSequence a = new AnimationSequence("hi", l);
		Sprite s = new Sprite();
		s.addAnimationSequence(a);
		s.setAnimation("hi");
		Holdable o = new Holdable(s);
		
		BoundedImage k = new BoundedImage("/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/brick.png");
		k.setPosition(400, 200);
		k.setSize(400, 400);
		obj1.getInventory().setPane(k);
		obj1.setInventoryPosition(400, 200);
		obj1.addToInventory(o);
		
		for(int z = 0; z < 18; z++) {
			if(z % 2 == 0) t = new BoundedImage("/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/Link.png");
			else t = new BoundedImage("/Users/aaronpaskin/Documents/CompSci308/voogasalad_bigvooga/resources/ActiveTurtle.png");
			l = new ArrayList<BoundedImage>();
			l.add(t);
			a = new AnimationSequence("hi", l);
			s = new Sprite();
			s.addAnimationSequence(a);
			s.setAnimation("hi");
			o = new Holdable(s);
			GameObjectOperation self = new Self();
			VectorOperation loc = new BasicVector(new Value(500), new Value(500));
			Action moveTo;
			if(z % 2 == 0) {
				loc = new BasicVector(new Value(500), new Value(500));
				moveTo = new MoveTo(self, loc);
			}
			else {
				loc = new BasicVector(new Value(650), new Value(500));
				moveTo = new MoveTo(self, loc);
			}
			List<Action> selectActions = new ArrayList<>();
			selectActions.add(moveTo);
			o.setSelectActions(selectActions);
			obj1.addToInventory(o);
		}
			

		GameLayer la = new GameLayer("Layer");
		la.addGameObject(obj1);
		la.addGameObject(obj2);
		la.addGameObject(obj3);
		la.addGameObject(obj4);

		GameWorld w = new GameWorld("World");
		w.addLayer(la);
		
		GameMaster master = new GameMaster();
		master.addWorld(w);
		master.setNextWorld("World");
		//try {
			new GameDataHandler(name).saveGame(master);
		//} //catch (IOException e) {
			//e.printStackTrace();
		//}

		try {
			new GameDataHandler(name).loadGame().setNextWorld("World");
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
		actions1.add(new DisplayInventory(new Self()));
		obj.addConditionAction(new Condition(2, new KeyPressed(new SelfString("I"))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Move(new Self(), new VectorHeadingOf(new Self())));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("W"))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(5)));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("D"))), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(-5)));
		obj.addConditionAction(new Condition(2, new KeyHeld(new SelfString("A"))), actions1);
	}

	private void conditionAction2(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
	}

	private void conditionAction3(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
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
