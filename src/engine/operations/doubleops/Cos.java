package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class Cos implements DoubleOperation {

	private DoubleOperation angle;

	public Cos(DoubleOperation angle) {
		this.angle = angle;
	}

	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return Math.cos(Math.toRadians(angle.evaluate(asking, world)));
	}

}
