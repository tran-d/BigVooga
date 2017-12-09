package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeString implements Action {

	private StringOperation varName;
	private StringOperation newString;
	private GameObjectOperation object;
	
	public ChangeString(GameObjectOperation object, StringOperation varName, StringOperation newString) {
		this.object = object;
		this.varName = varName;
		this.newString = newString;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		object.evaluate(asking, world).setStringVariable(varName.evaluate(asking, world), newString.evaluate(asking, world));
	}
	
}
