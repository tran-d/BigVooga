package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class TransferObjectToWorld implements Action {

	private GameObjectOperation gameObject;
	private StringOperation worldName;

	public TransferObjectToWorld(
			@VoogaAnnotation(name = "Object being transferred", type = VoogaType.GAMEOBJECT) GameObjectOperation gameObject,
			@VoogaAnnotation(name = "Name of New World", type = VoogaType.WORLDNAME) StringOperation worldName) {
		this.gameObject = gameObject;
		this.worldName = worldName;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.transfer(gameObject.evaluate(asking, world), worldName.evaluate(asking, world));
	}

}
