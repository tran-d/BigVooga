package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import authoring.drawing.BoundingPolygonCreator;
import authoring.drawing.ImageCanvas;
import engine.Actions.ChangeDouble;
import engine.Actions.Create;
import engine.Actions.Move;
import engine.Actions.MoveByVariable;
import engine.Actions.RemoveIntersection;
import engine.Actions.Rotate;
import engine.Actions.RotateTo;
import engine.Conditions.And;
import engine.Conditions.BeginStep;
import engine.Conditions.Collision;
import engine.Conditions.DoubleGreaterThan;
import engine.Conditions.KeyHeld;
import engine.Conditions.KeyPressed;
import engine.Conditions.KeyReleased;
import engine.Conditions.Not;
import engine.Conditions.ObjectClickHeld;
import engine.Conditions.Or;
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
		generateGame();
	}
	
	public void generateGame() {	
		generateGame(new BoundedImage("testImage.png"));
	}
	
	private void generateGame(BoundedImage i) {		
		GameObjectFactory blueprints = new GameObjectFactory();
		GameObject obj1 = makeObject("Ob1", i, 200, 200, this::conditionAction1);
		obj1.addTag("Ob1");
		GameObject obj2 = makeObject("Ob2", i.clone(), 500, 200, this::conditionAction2);
		
		obj1.setDoubleVariable("xSpeed", -3);
		obj1.setDoubleVariable("ySpeed", 0);
		blueprints.addBlueprint(obj1);
		obj2.addTag("Ob2");
		blueprints.addBlueprint(obj2);
		GameLayer layer = new GameLayer("Layer");
		GameObject obj = new GameObject();
		obj.setDoubleVariable("speed", 50);
		obj.setCoords(200, 200);
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new Move(-1, 0));
		obj.addConditionAction(new ObjectClickHeld(1), actions1);
		List<Action> actions2 = new ArrayList<Action>();
		actions2.add(new Move(1, 0));
		obj.addConditionAction(new KeyHeld(2,"Right"), actions2);
		Sprite sprite = new Sprite();
		List<BoundedImage> images = new ArrayList<>();
		images.add(i);
		AnimationSequence animation = new AnimationSequence("Animation", images);
		sprite.addAnimationSequence(animation);
		sprite.setAnimation("Animation");
		obj.setSprite(sprite);

		layer.addGameObject(obj1);
		layer.addGameObject(obj2);
		layer.setBlueprints(blueprints);
		
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
		actions1.add(new MoveByVariable("xSpeed", "ySpeed"));
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
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(1));
		obj.addConditionAction(new ObjectClickHeld(1), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(-1));
		obj.addConditionAction(new KeyHeld(1,"Z"), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(1));
		obj.addConditionAction(new KeyHeld(1,"X"), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new ChangeDouble("xSpeed", -10, false));
		obj.addConditionAction(new And(1, new KeyHeld(1, "Q"), new KeyHeld(1, "Space")), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new ChangeDouble("xSpeed", -3, false));
		obj.addConditionAction(new Or(1, new KeyReleased(1, "Q"), new KeyReleased(1, "Space")), actions1);
//		actions1 = new ArrayList<Action>();
//		actions1.add(new RotateTo(45.0));
//		obj.addConditionAction(new Not(1, new ScreenClickHeld(1)), actions1);
//		actions1 = new ArrayList<Action>();
//		actions1.add(new RotateTo(0));
//		obj.addConditionAction(new ScreenClickHeld(1), actions1);
//		actions1 = new ArrayList<Action>();
//		actions1.add(new RotateTo(25));
//		obj.addConditionAction(new DoubleGreaterThan(1, GameObject.X_COR, 300), actions1);
		actions1 = new ArrayList<Action>();
		actions1.add(new Create("Ob2", 500, 500, 20));
		obj.addConditionAction(new KeyPressed(1,"C"), actions1);

	}
	
	private void conditionAction2(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new RemoveIntersection());
		obj.addConditionAction(new Collision(3, "Ob1"), actions1);
		obj.addConditionAction(new Collision(4, "Ob2"), actions1);
	}
	
	public void testImageCanvas(Stage stage) {
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
