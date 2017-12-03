package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Not implements BooleanOperation {

	private BooleanOperation boolOp;
	
	public Not(BooleanOperation condition) {
		this.boolOp = condition;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return !boolOp.evaluate(asking, world);
	}

}
