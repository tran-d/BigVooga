package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class RandomDouble implements DoubleOperation{

	public RandomDouble() {
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Returns a random value between 0 (inclusive) and 1 (noninclusive).
	 */
	public Double evaluate(GameObject asking, Layer world) {
		// TODO Auto-generated method stub
		return Math.random();
	}

}
