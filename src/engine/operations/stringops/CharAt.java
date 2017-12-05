package engine.operations.stringops;

import engine.GameObject;
/**
 * @author Nikolas Bramblett
 */
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;

public class CharAt implements StringOperation{

	private StringOperation string; 
	private DoubleOperation charAt;
	public CharAt(StringOperation string, DoubleOperation charAt) {
		// TODO Auto-generated constructor stub
		this.string = string;
		this.charAt = charAt;
	}

	@Override
	public String evaluate(GameObject asking, Layer world) {
		// TODO Auto-generated method stub
		return ""+string.evaluate(asking, world).charAt(charAt.evaluate(asking, world).intValue());
	}

}
