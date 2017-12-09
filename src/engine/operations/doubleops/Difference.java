package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Difference implements DoubleOperation {
	private DoubleOperation number;
	private DoubleOperation toSubtract;

	public Difference(DoubleOperation number, DoubleOperation toSubtract) {
		this.number = number;
		this.toSubtract = toSubtract;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return number.evaluate(asking, world) - toSubtract.evaluate(asking, world);
	}

}
