package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.doubleops.XOf;
import engine.operations.doubleops.YOf;
import engine.operations.vectorops.VectorOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Move implements Action {

	private DoubleOperation xIncrement;
	private DoubleOperation yIncrement;
	private VectorOperation increment;
	public Move(VectorOperation increment) {

		this.increment = increment;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setCoords(asking.getX() + (new XOf(increment)).evaluate(asking,world), asking.getY() + (new YOf(increment)).evaluate(asking, world));
	}

}
