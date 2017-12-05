package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Destroy implements Action {

	private StringOperation objectToDestroy;
	
	public Destroy(StringOperation objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		world.removeGameObject(world.getWithName(objectToDestroy.evaluate(asking, world)));
	}
}
