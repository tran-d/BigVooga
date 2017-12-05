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
		// TODO Auto-generated constructor stub
		this.angle = angle;
	}

	@Override
	public Double evaluate(GameObject asking, Layer world) {
		// TODO Auto-generated method stub
		return angle.evaluate(asking, world);
	}

}
