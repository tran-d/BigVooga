package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;

public class StopObject implements Action {

	private GameObjectOperation object;

	public StopObject(GameObjectOperation object) {
		this.object = object;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).stop();
	}
	
}
