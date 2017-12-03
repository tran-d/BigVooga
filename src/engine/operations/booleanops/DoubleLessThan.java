package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class DoubleLessThan implements BooleanOperation {

	private StringOperation varName;
	private DoubleOperation check;
	
	public DoubleLessThan(int priorityNum, StringOperation varName, DoubleOperation check) {
		this.varName = varName;
		this.check = check;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return asking.getDouble(varName.evaluate(asking, world)) < check.evaluate(asking, world);
	}
	
}
