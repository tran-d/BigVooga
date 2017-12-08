package engine.Actions.actionLoops;

import java.util.List;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;

public class DoTimes implements Action {

	private DoubleOperation times;
	private List<Action> actions;
	
	public DoTimes(DoubleOperation times, List<Action> actions) {
		this.times = times;
		this.actions = actions;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		for(int i = 0; i < times.evaluate(asking, world); i++) { //times.evaluate BEFORE
			actions.get(i).execute(asking, world);
		}
	}

}
