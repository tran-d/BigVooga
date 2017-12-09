package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Rotate implements Action {

	private DoubleOperation headingIncrement;
	private GameObjectOperation object;
	
	public Rotate(GameObjectOperation object, DoubleOperation headingIncrement) {
		this.object = object;
		this.headingIncrement = headingIncrement;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		GameObject obj = object.evaluate(asking, world);
		obj.setHeading(obj.getHeading() + headingIncrement.evaluate(asking, world));
	}

}
