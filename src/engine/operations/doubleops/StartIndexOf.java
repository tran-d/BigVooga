package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;

public class StartIndexOf implements DoubleOperation {

	GameObjectOperation gameObject;
	
	public StartIndexOf(GameObjectOperation gameObject) {
		this.gameObject = gameObject;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return new Double(gameObject.evaluate(asking, world).getInventory().getStartIndex());
	}

}
