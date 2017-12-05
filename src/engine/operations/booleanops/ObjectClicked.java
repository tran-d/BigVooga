package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;

/**
 * Checks if an object is initially clicked (thus, can not return true
 * two steps in a row).
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClicked implements BooleanOperation {
	
	public ObjectClicked() {}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		BooleanOperation screenClicked = new ScreenClicked();
		BooleanOperation objectClickHeld = new ObjectClickHeld();
		BooleanOperation and = new And(0, screenClicked, objectClickHeld);
		return and.evaluate(asking, world);
	}

}
