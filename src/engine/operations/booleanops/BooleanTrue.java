package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class BooleanTrue implements BooleanOperation {

	private StringOperation varName;
	
	public BooleanTrue(StringOperation varName) {
		this.varName = varName;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return asking.getBoolean(varName.evaluate(asking, world));
	}
	
}
