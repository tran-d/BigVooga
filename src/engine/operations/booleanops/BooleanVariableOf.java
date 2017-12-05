package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

public class BooleanVariableOf implements BooleanOperation {

	private StringOperation varName;
	private GameObjectOperation object;

	public BooleanVariableOf(GameObjectOperation object, StringOperation varName) {
		this.object = object;
		this.varName = varName;
	}
	
	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return object.evaluate(asking, world).getBoolean(varName.evaluate(asking, world));
	}

}
