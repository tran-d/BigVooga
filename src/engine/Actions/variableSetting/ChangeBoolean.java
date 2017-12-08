package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.booleanops.BooleanOperation;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeBoolean implements Action {

	private StringOperation varName;
	private BooleanOperation newBooleanOperation;
	private GameObjectOperation object;
	
	public ChangeBoolean(GameObjectOperation object, StringOperation varName, BooleanOperation newBooleanOperation) {
		this.object = object;
		this.varName = varName;
		this.newBooleanOperation = newBooleanOperation;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		object.evaluate(asking, world).setBooleanVariable(varName.evaluate(asking, world), newBooleanOperation.evaluate(asking, world));
	}
	
}
