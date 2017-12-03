package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class StringEquals implements BooleanOperation {

	private StringOperation varName;
	private StringOperation check;
	
	public StringEquals(StringOperation varName, StringOperation check) {
		this.varName = varName;
		this.check = check;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		return asking.getString(varName.evaluate(asking, world)).equals(check.evaluate(asking, world));
	}

}
