package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ScreenClickHeld implements BooleanOperation {
	
	public ScreenClickHeld() {}
	
	/**
	 * Returns true if the primary mouse button is down, regardless of whether or not it was down in the previous step
	 */
	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return world.getPlayerManager().isPrimaryButtonDown();
	}
	
}
