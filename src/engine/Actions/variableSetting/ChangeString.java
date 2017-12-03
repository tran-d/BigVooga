package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeString implements Action {

	private StringOperation varName;
	private StringOperation newString;
	
	public ChangeString(StringOperation varName, StringOperation newString) {
		this.varName = varName;
		this.newString = newString;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setStringVariable(varName.evaluate(asking, world), newString.evaluate(asking, world));
	}
	
}
