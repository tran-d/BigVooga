package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.stringops.StringOperation;

public class DebugPrint implements Action {

	private StringOperation string;

	public DebugPrint(StringOperation string) {
		this.string = string;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.addToDebugStream(string.evaluate(asking, world));
	}
	
}
