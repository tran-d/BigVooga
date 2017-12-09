package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;

public class GreaterThanOrEqualTo implements BooleanOperation {

	private DoubleOperation first;
	private DoubleOperation second;

	public GreaterThanOrEqualTo(DoubleOperation first, DoubleOperation second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return first.evaluate(asking, world) >= second.evaluate(asking, world);
	}
	
}
