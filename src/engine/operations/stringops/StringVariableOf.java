package engine.operations.stringops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class StringVariableOf implements StringOperation {
	private GameObjectOperation object;
	private StringOperation varName;

	public StringVariableOf(GameObjectOperation object, StringOperation varName) {
		this.object = object;
		this.varName = varName;
	}

	@Override
	public String evaluate(GameObject asking, Layer world) {
		return object.evaluate(asking, world).getString(varName.evaluate(asking, world));
	}
}
