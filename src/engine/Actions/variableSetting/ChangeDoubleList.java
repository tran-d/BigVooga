package engine.Actions.variableSetting;

import java.util.List;

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
public class ChangeDoubleList implements Action {

	private StringOperation varName;
	private List<Double> newDoubleList;
	private GameObjectOperation object;
	
	public ChangeDoubleList(GameObjectOperation object, StringOperation varName, List<Double> newDoubleList) {
		this.object = object;
		this.varName = varName;
		this.newDoubleList = newDoubleList;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		object.evaluate(asking, world).setDoubleListVariable(varName.evaluate(asking, world), newDoubleList);
	}
	
}
