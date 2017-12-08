package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class RotateTo implements Action {

	private DoubleOperation newHeading;
	
	public RotateTo(DoubleOperation newHeading) {
		this.newHeading = newHeading;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setHeading(newHeading.evaluate(asking, world));
	}

}
