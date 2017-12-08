package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Product implements DoubleOperation {

	private DoubleOperation first;
	private DoubleOperation second;

	public Product(DoubleOperation first, DoubleOperation second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return first.evaluate(asking, world)*second.evaluate(asking, world);
	}

}
