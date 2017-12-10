package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.holdableops.HoldableOperation;
import engine.operations.stringops.StringOperation;

public class ChangeDoubleOfHoldable implements Action {
	
	private HoldableOperation holdable;
	private StringOperation varName;
	private DoubleOperation newDoubleOperation;

	public ChangeDoubleOfHoldable(HoldableOperation holdable, StringOperation varName, DoubleOperation newDoubleOperation) {
		this.holdable = holdable;
		this.varName = varName;
		this.newDoubleOperation = newDoubleOperation;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		holdable.evaluate(asking, world).setDoubleVariable(varName.evaluate(asking, world), newDoubleOperation.evaluate(asking, world));
	}

}
