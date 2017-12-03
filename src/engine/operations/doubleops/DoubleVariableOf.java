package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class DoubleVariableOf implements DoubleOperation {

	private StringOperation varName;
	private GameObjectOperation object;

	public DoubleVariableOf(GameObjectOperation object, StringOperation varName) {
		this.object = object;
		this.varName = varName;
	}
	
	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return object.evaluate(asking, world).getDouble(varName.evaluate(asking, world));
	}

}
