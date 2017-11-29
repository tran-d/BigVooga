package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

public class Destroy implements Action {

	private GameObject objectToDestroy;
	
	public Destroy(GameObject objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		world.removeGameObject(objectToDestroy);
	}

}
