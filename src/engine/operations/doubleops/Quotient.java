package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Quotient implements DoubleOperation {

	private DoubleOperation denominator;
	private DoubleOperation numerator;

	public Quotient(DoubleOperation numerator, DoubleOperation denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return numerator.evaluate(asking, world)/denominator.evaluate(asking, world);
	}

}
