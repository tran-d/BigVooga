package engine.Actions.variableSetting;

import java.util.List;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class ChangeDoubleList implements Action {

	private StringOperation varName;
	private List<Double> newDoubleList;
	
	public ChangeDoubleList(StringOperation varName, List<Double> newDoubleList) {
		this.varName = varName;
		this.newDoubleList = newDoubleList;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setDoubleListVariable(varName.evaluate(asking, world), newDoubleList);
	}
	
}
