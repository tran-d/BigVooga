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
public class Move implements Action {

	private DoubleOperation xIncrement;
	private DoubleOperation yIncrement;
	
	public Move(DoubleOperation xIncrement, DoubleOperation yIncrement) {
		this.xIncrement = xIncrement;
		this.yIncrement = yIncrement;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setCoords(asking.getX() + xIncrement.evaluate(asking, world), asking.getY() + yIncrement.evaluate(asking, world));
	}

}
