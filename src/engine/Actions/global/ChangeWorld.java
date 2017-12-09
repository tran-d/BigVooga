package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;


/**
 * Currently non-functional.
 * 
 * @author aaronpaskin
 * 
 */
public class ChangeWorld implements Action {

	private StringOperation nameOfWorld;

	public ChangeWorld(StringOperation nameOfNewWorld) {
		this.nameOfWorld = nameOfNewWorld;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		world.setNextWorld(nameOfWorld.evaluate(asking, world));
	}

}
