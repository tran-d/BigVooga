package engine.Actions.changeObject;

import java.util.Map;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class RemoveFromWorld implements Action {

	private StringOperation objectToDestroy;
	
	public RemoveFromWorld(StringOperation objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		world.removeGameObject(world.getWithName(objectToDestroy.evaluate(asking, world)));
		Map<String, GameObject> inventory = world.getWithName(objectToDestroy.evaluate(asking, world)).getInventory();
		for(String o : inventory.keySet()) {
			world.removeGameObject(inventory.get(o));
		}
	}
}
