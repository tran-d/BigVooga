package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * Checks if an object is initially clicked (thus, can not return true
 * two steps in a row).
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClicked implements BooleanOperation {
	
	private BooleanOperation operation;

	public ObjectClicked(GameObjectOperation object) {
		BooleanOperation screenClicked = new ScreenClicked();
		BooleanOperation objectClickHeld = new ObjectClickHeld(object);
		operation = new And(screenClicked, objectClickHeld);
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return operation.evaluate(asking, world);
	}

}
