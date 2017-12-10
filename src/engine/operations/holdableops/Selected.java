package engine.operations.holdableops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.Holdable;
import engine.operations.Operation;

public class Selected implements HoldableOperation {

	public Selected() {}
	
	@Override
	public Holdable evaluate(GameObject asking, GameObjectEnvironment world) {
		return asking.getInventory().getSelected();
	}

}
