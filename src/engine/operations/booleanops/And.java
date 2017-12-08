package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class And implements BooleanOperation {

	private BooleanOperation boolOp1;
	private BooleanOperation boolOp2;
	
	public And(int priorityNum, BooleanOperation boolOp1, BooleanOperation boolOp2) {
		this.boolOp1 = boolOp1;
		this.boolOp2 = boolOp2;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return boolOp1.evaluate(asking, world) && boolOp2.evaluate(asking, world);
	}

}
