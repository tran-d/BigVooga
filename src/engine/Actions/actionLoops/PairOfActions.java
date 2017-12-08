package engine.Actions.actionLoops;

import engine.Action;
import engine.GameObject;
import engine.Layer;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class PairOfActions implements Action {

	private Action first;
	private Action second;

	public PairOfActions(Action first, Action second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		first.execute(asking, world);
		second.execute(asking, world);
	}

}
