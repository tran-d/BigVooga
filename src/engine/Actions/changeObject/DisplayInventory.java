package engine.Actions.changeObject;

import java.util.List;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;

//TODO: DisplayInventory
public class DisplayInventory implements Action {

	private GameObjectOperation obj;
	
	public DisplayInventory(GameObjectOperation obj) {
		this.obj = obj;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		world.addElement(obj.evaluate(asking, world).getInventory());
	}

}
