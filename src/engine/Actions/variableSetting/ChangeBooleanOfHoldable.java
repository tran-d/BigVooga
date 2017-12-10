package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.booleanops.BooleanOperation;
import engine.operations.holdableops.HoldableOperation;
import engine.operations.stringops.StringOperation;

public class ChangeBooleanOfHoldable implements Action {
	
	private HoldableOperation holdable;
	private StringOperation varName;
	private BooleanOperation newBooleanOperation;

	public ChangeBooleanOfHoldable(HoldableOperation holdable, StringOperation varName, BooleanOperation newBooleanOperation) {
		this.holdable = holdable;
		this.varName = varName;
		this.newBooleanOperation = newBooleanOperation;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		holdable.evaluate(asking, world).setBooleanVariable(varName.evaluate(asking, world), newBooleanOperation.evaluate(asking, world));
	}

}
