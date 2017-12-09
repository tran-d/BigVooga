package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.vectorops.VectorOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class MoveTo implements Action {

	private VectorOperation newLocation;
	private GameObjectOperation object;
	
	public MoveTo(GameObjectOperation object, VectorOperation newLocation) {
		this.object = object;
		this.newLocation = newLocation;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		object.evaluate(asking, world).setLocation(newLocation.evaluate(asking, world));
	}

}
