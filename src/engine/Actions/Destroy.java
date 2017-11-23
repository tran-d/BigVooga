package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.GenericObject;
import engine.VariableContainer;
import engine.World;

public class Destroy implements Action {

	private GameObject objectToDestroy;
	
	public Destroy(GameObject objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, World world) {
		world.removeGameObject(objectToDestroy);
	}

}
