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
import engine.Actions.changeObject.Create;
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
import engine.operations.booleanops.DoubleEquals;
import engine.operations.booleanops.KeyHeld;
import engine.operations.booleanops.KeyPressed;
import engine.operations.booleanops.Not;
import engine.operations.booleanops.ObjectClicked;
import engine.operations.booleanops.ObjectMouseHover;
import engine.operations.doubleops.Difference;
import engine.operations.doubleops.DoubleVariableOf;
import engine.operations.doubleops.Product;
import engine.operations.doubleops.RandomDouble;
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
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//testCollisions(stage);
		// testData(stage);
		//testImageCanvas(stage);
		//testDrawer(stage);
		generateGame(stage);
	}

	public void generateGame(Stage stage) {
		generateGame("???", stage);
	}

	public void generateGame(String name, Stage stage) {
		
		
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
