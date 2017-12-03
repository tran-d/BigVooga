package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClickReleased implements BooleanOperation {
	
	public ScreenClickReleased() {}
	
	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return world.getPlayerManager().isPrevPrimaryButtonDown() && !world.getPlayerManager().isPrimaryButtonDown();
	}
	
}
