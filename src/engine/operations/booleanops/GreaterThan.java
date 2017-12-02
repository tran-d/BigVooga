package engine.operations.booleanops;

import engine.operations.doubleops.DoubleOperation;

public class GreaterThan implements BooleanOperation {

	private DoubleOperation first;
	private DoubleOperation second;

	public GreaterThan(DoubleOperation first, DoubleOperation second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Boolean evaluate() {
		return first.evaluate()>second.evaluate();
	}

}
