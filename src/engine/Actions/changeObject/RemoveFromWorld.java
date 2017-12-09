package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class RemoveFromWorld implements Action {

	private GameObjectOperation objectToDestroy;
	
	public RemoveFromWorld(GameObjectOperation objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.removeGameObject(world.getWithName(objectToDestroy.evaluate(asking, world).getName()));
	}
}
