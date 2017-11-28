package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.VariableContainer;
import engine.World;

public class Destroy implements Action {

	private GameObject objectToDestroy;
	
	public Destroy(GameObject objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(VariableContainer asking, World world) {
		world.removeGameObject(objectToDestroy);
	}

}
