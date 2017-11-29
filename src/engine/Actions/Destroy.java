package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

public class Destroy implements Action {

	private String objectToDestroy;
	
	public Destroy(String objectToDestroy) {
		this.objectToDestroy = objectToDestroy;
	}
	
	@Override
	public void execute(GameObject asking, World world) {
		world.removeGameObject(world.getWithName(objectToDestroy));
	}
}
