package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.VariableContainer;
import engine.World;

public class Create implements Action {

	private GameObject objectToCreate;
	
	public Create(GameObject objectToCreate) {
		this.objectToCreate = objectToCreate;
	}
	
	@Override
	public void execute(VariableContainer asking, World world) {
		world.addGameObject(objectToCreate);
	}
	
}
