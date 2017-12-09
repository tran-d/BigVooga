package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 *
 * 
 * @author aaronpaskin
 *
 */
//TODO: GiveInventory
public class GiveInventory implements Action {

	private GameObjectOperation obj;
	
	public GiveInventory(GameObjectOperation obj) {
		this.obj = obj;
	}
	
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		//for(String key : asking.getInventory().keySet())
		//	obj.evaluate(asking, world).setGameObjectVariable(key, asking.getGameObject(key));
	}

}
