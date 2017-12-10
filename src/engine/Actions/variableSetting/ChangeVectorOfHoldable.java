package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.vectorops.VectorOperation;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.holdableops.HoldableOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author Aaron Paskin
 *
 */
public class ChangeVectorOfHoldable implements Action {
	
	private HoldableOperation holdable;
	private StringOperation varName;
	private VectorOperation newVectorOperation;

	public ChangeVectorOfHoldable(@VoogaAnnotation(name = "Holdable", type = VoogaType.HOLDABLE)HoldableOperation holdable, 
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.STRING)StringOperation varName, 
			@VoogaAnnotation(name = "Variable Name", type = VoogaType.VECTOR)VectorOperation newVectorOperation) {
		this.holdable = holdable;
		this.varName = varName;
		this.newVectorOperation = newVectorOperation;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		holdable.evaluate(asking, world).setVectorVariable(varName.evaluate(asking, world), newVectorOperation.evaluate(asking, world));
	}

}
