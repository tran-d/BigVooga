package engine.Actions.global;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.doubleops.DoubleOperation;

public class SaveGame implements Action {

	private DoubleOperation currentPoints;

	public SaveGame(DoubleOperation currentPoints) {
		this.currentPoints = currentPoints;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.save(currentPoints);
	}

}
