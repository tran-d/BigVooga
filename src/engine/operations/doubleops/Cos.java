package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;

public class Cos implements DoubleOperation {

	private DoubleOperation angle;

	public Cos(DoubleOperation angle) {
		this.angle = angle;
	}

	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return Math.cos(Math.toRadians(angle.evaluate(asking, world)));
	}

}
