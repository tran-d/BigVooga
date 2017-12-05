package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyHeld implements BooleanOperation {

	private StringOperation check;
	
	public KeyHeld(StringOperation check) {
		this.check = check;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return world.getPlayerManager().getKeysDown().contains(check.evaluate(asking, world));
	}
	
}
