package engine.Actions.changeObject;

import java.util.Map;

import engine.Action;
import engine.GameObject;
import engine.Layer;
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
	public void execute(GameObject asking, Layer world) {
		world.removeGameObject(objectToDestroy.evaluate(asking, world));
	}
}
