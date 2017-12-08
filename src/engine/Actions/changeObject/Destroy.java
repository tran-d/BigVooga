package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Destroy implements Action {

	private GameObjectOperation objectToDestroy;
	
	public Destroy(GameObjectOperation objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		world.removeGameObject(objectToDestroy.evaluate(asking, world));
	}
}
