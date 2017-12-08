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
public class ChangeStringList implements Action {

	private StringOperation varName;
	private List<String> newStringList;
	
	public ChangeStringList(StringOperation varName, List<String> newStringList) {
		this.varName = varName;
		this.newStringList = newStringList;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setStringListVariable(varName.evaluate(asking, world), newStringList);
	}
	
}
