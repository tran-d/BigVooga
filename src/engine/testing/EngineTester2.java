package engine.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import authoring.drawing.BoundingPolygonCreator;
import authoring.drawing.ImageCanvas;
import engine.Action;
import engine.GameLayer;
import engine.GameMaster;
import engine.GameObject;
import engine.GameObjectFactory;
import engine.GameWorld;
import engine.archived.Actions.ChangeDouble;
import engine.archived.Actions.Create;
import engine.archived.Actions.Destroy;
import engine.archived.Actions.Move;
import engine.archived.Actions.MoveByVariable;
import engine.archived.Actions.RemoveIntersection;
import engine.archived.Actions.Rotate;
import engine.archived.Conditions.And;
import engine.archived.Conditions.BeginStep;
import engine.archived.Conditions.Collision;
import engine.archived.Conditions.KeyHeld;
import engine.archived.Conditions.KeyPressed;
import engine.archived.Conditions.KeyReleased;
import engine.archived.Conditions.Not;
import engine.archived.Conditions.ObjectClickHeld;
import engine.archived.Conditions.Or;
import engine.archived.Conditions.ScreenClickHeld;
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

public class EngineTester2 extends Application {
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
		generateGame(new BoundedImage("Smiley.png"));
	}
	
	private void generateGame(BoundedImage i) {		
		
		GameObjectFactory blueprints = new GameObjectFactory();
		GameObject obj1 = makeObject("Birb", i, 400, 200, this::conditionAction1);
		obj1.addTag("Player");
		GameObject obj2 = makeObject("Pillar", new BoundedImage("trash.png"), 500, 200, this::conditionAction2);
		
		obj1.setDoubleVariable("xSpeed", 0);
		obj1.setDoubleVariable("ySpeed", 0);
		obj1.setSize(64, 64);
		System.out.println(obj1.getBounds().getWidth());
		
		blueprints.addBlueprint(obj1);
		obj2.addTag("Pillar");
		blueprints.addBlueprint(obj2);
		GameLayer layer = new GameLayer("Layer");

		layer.setBlueprints(blueprints);
		layer.addGameObject(obj1);
		
		for(int j = 0; j < 50; j++)
		{
			GameObject temp = blueprints.getInstanceOf("Pillar");
			temp.setSize(temp.getWidth(), temp.getHeight()*(Math.random()+.5)*2);
			temp.setCoords(1000+(400*j), 400*(j%2)+temp.getHeight()/2);
			temp.setHeading(0);
			layer.addGameObject(temp);
		}
		
		GameWorld w = new GameWorld("World");
		w.addLayer(layer);
		
		GameMaster master = new GameMaster();
		master.addWorld(w);
		master.setCurrentWorld("World");
		try {
			GameDataHandler saver = new GameDataHandler("Flappy_Birb");
			saver.saveGame(master);
		} catch (IOException e) {
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
		actions1.add(new SetDouble("ySpeed", .2, true));
		obj.addConditionAction(new BeginStep(), actions1);
		actions1 = new ArrayList<Action>();
		
		actions1.add(new SetDouble("ySpeed", -5, false));
		obj.addConditionAction(new KeyPressed(-1, "Up"), actions1);
		actions1 = new ArrayList<Action>();
	}
	
	private void conditionAction2(GameObject obj) {
		List<Action> actions1 = new ArrayList<Action>();
		actions1.add(new Destroy("Birb"));
		obj.addConditionAction(new Collision(3, "Player"), actions1);
		actions1 = new ArrayList<Action>();
		
		actions1.add(new Move(-2, 0));
		obj.addConditionAction(new BeginStep(), actions1);
		actions1 = new ArrayList<Action>();
		
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
