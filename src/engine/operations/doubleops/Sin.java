package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class Sin implements DoubleOperation {

	private DoubleOperation angle;

	public Sin(DoubleOperation angle) {
		this.angle = angle;
	}

	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return Math.sin(Math.toRadians(angle.evaluate(asking, world)));
	}

}
