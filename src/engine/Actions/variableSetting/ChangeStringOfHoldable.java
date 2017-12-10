package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.stringops.StringOperation;
import engine.operations.holdableops.HoldableOperation;

public class ChangeStringOfHoldable implements Action {
	
	private HoldableOperation holdable;
	private StringOperation varName;
	private StringOperation newStringOperation;

	public ChangeStringOfHoldable(HoldableOperation holdable, StringOperation varName, StringOperation newStringOperation) {
		this.holdable = holdable;
		this.varName = varName;
		this.newStringOperation = newStringOperation;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		holdable.evaluate(asking, world).setStringVariable(varName.evaluate(asking, world), newStringOperation.evaluate(asking, world));
	}

}
