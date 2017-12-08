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
public class Rotate implements Action {

	private DoubleOperation headingIncrement;
	
	public Rotate(DoubleOperation headingIncrement) {
		this.headingIncrement = headingIncrement;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		GameObject obj = (GameObject)asking;
		obj.setHeading(obj.getHeading() + headingIncrement.evaluate(asking, world));
	}

}
