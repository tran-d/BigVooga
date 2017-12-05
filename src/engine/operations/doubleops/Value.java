package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;

public class Value implements DoubleOperation {

	private double value;
	
	public Value(double value) {
		this.value = value;
	}
	
	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return value;
	}

}
