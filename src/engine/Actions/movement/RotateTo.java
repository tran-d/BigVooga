package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class RotateTo implements Action {

	private DoubleOperation newHeading;
	private GameObjectOperation object;
	
	public RotateTo(GameObjectOperation object, DoubleOperation newHeading) {
		this.object = object;
		this.newHeading = newHeading;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setHeading(newHeading.evaluate(asking, world));
	}

}
