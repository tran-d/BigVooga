package engine.operations.stringops;

import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class StringFromNumber implements StringOperation {

	private DoubleOperation number;
	
	public StringFromNumber(DoubleOperation number) {
		this.number = number;
	}
	
	@Override
	public String evaluate(GameObject asking, Layer world) {
		return ""+number.evaluate(asking, world);
	}

}
