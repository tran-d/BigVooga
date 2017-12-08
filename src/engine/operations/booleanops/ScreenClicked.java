package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClicked implements BooleanOperation {
	
	public ScreenClicked() {}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return !world.getPlayerManager().isPrevPrimaryButtonDown() && world.getPlayerManager().isPrimaryButtonDown();
	}

}
