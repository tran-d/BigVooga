package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.World;

public class Create implements Action {

	private GameObject objectToCreate;
	
	public Create(GameObject objectToCreate) {
		this.objectToCreate = objectToCreate;
	}
	
	@Override
	public void execute(GameObject asking, World world) {
		world.addGameObject(objectToCreate);
	}
	
}
