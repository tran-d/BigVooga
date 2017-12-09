package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class SetDouble implements Action {

	private StringOperation varName;
	private DoubleOperation newDouble;
	private GameObjectOperation object;
	
	public SetDouble(GameObjectOperation object, StringOperation varName, DoubleOperation newDouble) {
		this.object = object;
		this.varName = varName;
		this.newDouble = newDouble;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setDoubleVariable(varName.evaluate(asking, world), newDouble.evaluate(asking, world));
	}
	
}
