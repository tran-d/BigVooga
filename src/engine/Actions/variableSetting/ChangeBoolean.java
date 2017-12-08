package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.booleanops.BooleanOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeBoolean implements Action {

	private StringOperation varName;
	private BooleanOperation newBooleanOperation;
	
	public ChangeBoolean(StringOperation varName, BooleanOperation newBooleanOperation) {
		this.varName = varName;
		this.newBooleanOperation = newBooleanOperation;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setBooleanVariable(varName.evaluate(asking, world), newBooleanOperation.evaluate(asking, world));
	}
	
}
