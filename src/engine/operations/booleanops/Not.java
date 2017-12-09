package engine.operations.booleanops;

import engine.GameObject;
import engine.GameObjectEnvironment;

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
	public Boolean evaluate(GameObject asking, GameObjectEnvironment world) {
		return !boolOp.evaluate(asking, world);
	}

}
