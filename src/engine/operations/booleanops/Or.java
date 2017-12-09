package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Or implements BooleanOperation {

	private BooleanOperation boolOp1;
	private BooleanOperation boolOp2;
	
	public Or(BooleanOperation boolOp1, BooleanOperation boolOp2) {
		this.boolOp1 = boolOp1;
		this.boolOp2 =  boolOp2;
	}
	
	@Override
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return boolOp1.evaluate(asking, world) || boolOp2.evaluate(asking, world);
	}

}
