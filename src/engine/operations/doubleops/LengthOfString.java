package engine.operations.doubleops;

import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class LengthOfString implements DoubleOperation{
	
	private StringOperation string;

	public LengthOfString(StringOperation string) {
		this.string = string;
	}

	@Override
	public Double evaluate(GameObject asking, Layer world) {
		return (double) string.evaluate(asking, world).length();
	}
}
