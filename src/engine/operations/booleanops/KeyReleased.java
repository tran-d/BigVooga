package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class KeyReleased implements BooleanOperation {
	
	private StringOperation check;
	
	public KeyReleased(StringOperation check) {
		this.check = check;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return !world.getPlayerManager().getKeysDown().contains(check.evaluate(asking, world)) && world.getPlayerManager().getPrevKeysDown().contains(check.evaluate(asking, world));	
	}

}
