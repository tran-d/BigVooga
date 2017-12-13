package engine.testing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import engine.Action;
import engine.Condition;
import engine.GameLayer;
import engine.GameMaster;
import engine.GameObject;
import engine.GameWorld;
import engine.Actions.changeObject.RemoveFromWorld;
import engine.Actions.dialog.ClearTyped;
import engine.Actions.dialog.PlaceTextOn;
import engine.Actions.global.SaveGame;
import engine.Actions.movement.Bounce;
import engine.Actions.movement.Rotate;
import engine.Actions.movement.SetVelocity;
import engine.Actions.movement.StopObject;
import engine.operations.booleanops.BooleanValue;
import engine.operations.booleanops.CollisionByTag;
import engine.operations.booleanops.KeyHeld;
import engine.operations.booleanops.KeyReleased;
import engine.operations.doubleops.Value;
import engine.operations.gameobjectops.Get;
import engine.operations.gameobjectops.Self;
import engine.operations.stringops.SelfString;
import engine.operations.stringops.TypedString;
import engine.operations.vectorops.VectorHeadingOf;
import engine.operations.vectorops.VectorScale;
import engine.sprite.AnimationSequence;
import engine.sprite.BoundedImage;
import engine.sprite.DisplayableText;
import engine.sprite.Positionable;
import engine.sprite.PositionableObject;
import engine.sprite.Sprite;
import engine.utilities.data.GameDataHandler;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class TextTester extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage s) throws IOException {
		GameDataHandler.chooseFileForImageSave(s);
		generateGame(s, "Text test");
	}

	public void generateGame(Stage stage, String name) throws IOException {
		DisplayableText text = new DisplayableText(0,
				"A string of text",
				"Comic Sans", 12, "#008000");
		Positionable p = new PositionableObject();
		p.setPosition(-.5, 0);
		p.setSize(.2, .2);
		text.setRelativePosition(p);

		DisplayableText text2 = new DisplayableText(0,
				"another string of text",
				"Comic Sans", 12, "#008000");
		p = new PositionableObject();
		p.setPosition(.3, .3);
		p.setSize(.2, .2);
		text2.setRelativePosition(p);
		
		GameDataHandler gdh = new GameDataHandler(name);
		File f = gdh.chooseFile(stage);
		gdh.addFileToProject(f);
		GameObject object = makeObject("Box", new BoundedImage(f.getName()),
				100, 100, this::condAct);
		object.setSize(400, 400);
		object.setDerivative(1, new Point2D(3,1));
		//object.addTag("Player");
		List<DisplayableText> texts = new ArrayList<>();
		texts.add(text);
		texts.add(text2);
		object.setDialogue(texts);

		GameObject object2 = makeObject("Box", new BoundedImage(f.getName()),
				700, 100, this::condAct);
		object2.setSize(400, 400);
		object2.setDerivative(1, new Point2D(-2,1));
		
		GameLayer l = new GameLayer("Layer");
		l.addGameObject(object);
		l.addGameObject(object2);

		GameWorld w = new GameWorld("World");
		w.addLayer(l);

		GameMaster master = new GameMaster();
		master.addWorld(w);
		master.setNextWorld("World");
		gdh.saveGame(master);
	}

	private GameObject makeObject(String name, BoundedImage i, double x, double y, Consumer<GameObject> condActGen) {
		GameObject obj = new GameObject(name);
		obj.setLocation(x, y);
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

	private void deletable(GameObject object) {
		ArrayList<Action> actions1 = new ArrayList<Action>();
		actions1.add(new RemoveFromWorld(new Get(object)));
		object.addConditionAction(new Condition(100, new KeyHeld(new SelfString("L"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new ClearTyped());
		object.addConditionAction(new Condition(3, new KeyHeld(new SelfString(KeyCode.ESCAPE.getName()))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new PlaceTextOn(new Self(), new TypedString()));
		object.addConditionAction(new Condition(4, new BooleanValue(true)), actions1);
		
	}
	
	private void condAct(GameObject object) {
//		List<Action> actions1 = new ArrayList<Action>();
//		actions1.add(new SetAcceleration(new Self(), new UnitVector(new VectorDifference(new MouseLocation(),new LocationOf(new Self())))));
//		object.addConditionAction(new Condition(1, new BooleanValue(true)), actions1);
//		
		ArrayList<Action> actions1 = new ArrayList<Action>();
		actions1.add(new SetVelocity(new Self(), new VectorScale(new VectorHeadingOf(new Self()), new Value(10))));
		object.addConditionAction(new Condition(2, new KeyHeld(new SelfString("W"))), actions1);
		
		new ArrayList<Action>();
		actions1.add(new StopObject(new Self()));
		object.addConditionAction(new Condition(2, new KeyReleased(new SelfString("Space"))), actions1);

		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(-2)));
		object.addConditionAction(new Condition(2, new KeyHeld(new SelfString("A"))), actions1);
		actions1 = new ArrayList<Action>();

		actions1 = new ArrayList<Action>();
		actions1.add(new Rotate(new Self(), new Value(2)));
		object.addConditionAction(new Condition(2, new KeyHeld(new SelfString("D"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new SaveGame(new Value(1000)));
		object.addConditionAction(new Condition(2, new KeyHeld(new SelfString("Down"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new RemoveFromWorld(new Get(object)));
		object.addConditionAction(new Condition(5, new KeyHeld(new SelfString("R"))), actions1);
		
		actions1 = new ArrayList<Action>();
		actions1.add(new Bounce(new Value(.5)));
		object.addConditionAction(new Condition(6, new CollisionByTag(new SelfString("Box"))), actions1);
	}
}
