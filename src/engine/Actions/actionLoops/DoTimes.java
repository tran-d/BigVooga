package engine.Actions.actionLoops;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.doubleops.DoubleOperation;

/**
 * @author aaronpaskin, Ian Eldridge-Allegra
 *
 */
public class DoTimes implements Action {

	private DoubleOperation times;
	private Action action;
	
	public DoTimes(DoubleOperation times, Action action) {
		this.times = times;
		this.action = action;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		int num = (int)Math.round(times.evaluate(asking, world));
		for(int i = 0; i < num; i++) { 
			action.execute(asking, world);
		}
	}

}
