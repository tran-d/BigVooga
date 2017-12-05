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
public class MoveTo implements Action {

	private DoubleOperation newX;
	private DoubleOperation newY;
	
	public MoveTo(DoubleOperation newX, DoubleOperation newY) {
		this.newX = newX;
		this.newY = newY;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setCoords(newX.evaluate(asking, world), newY.evaluate(asking, world));
	}

}
