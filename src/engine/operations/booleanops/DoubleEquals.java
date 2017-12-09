package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class DoubleEquals implements BooleanOperation {

	private static final double ERROR = 0.001;
	private DoubleOperation first;
	private DoubleOperation second;

	public DoubleEquals(DoubleOperation first, DoubleOperation second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return Math.abs(first.evaluate(asking, world) - second.evaluate(asking, world)) <= ERROR;
	}

}
