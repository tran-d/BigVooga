package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;

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
	public Double evaluate(GameObject asking, Layer world) {
		return number.evaluate(asking, world) - toSubtract.evaluate(asking, world);
	}

}
