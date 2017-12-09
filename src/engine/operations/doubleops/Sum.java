package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Sum implements DoubleOperation {

	private DoubleOperation first;
	private DoubleOperation second;

	public Sum(DoubleOperation first, DoubleOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world) + second.evaluate(asking, world);
	}

}
