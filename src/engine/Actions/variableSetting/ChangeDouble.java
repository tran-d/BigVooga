package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeDouble implements Action {

	private StringOperation varName;
	private DoubleOperation newDouble;
	
	public ChangeDouble(StringOperation varName, DoubleOperation newDouble) {
		this.varName = varName;
		this.newDouble = newDouble;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setDoubleVariable(varName.evaluate(asking, world), newDouble.evaluate(asking, world));
	}
	
}
