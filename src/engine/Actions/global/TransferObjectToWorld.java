package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class TransferObjectToWorld implements Action {

	private GameObjectOperation gameObject;
	private StringOperation worldName;

	public TransferObjectToWorld(GameObjectOperation gameObject, StringOperation worldName) {
		this.gameObject = gameObject;
		this.worldName = worldName;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.transfer(gameObject.evaluate(asking, world), worldName.evaluate(asking, world));
	}
	
}
