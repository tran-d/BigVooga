package engine.operations.doubleops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;

public class HeadingOf implements DoubleOperation {

	private GameObjectOperation object;

	public HeadingOf(GameObjectOperation object) {
		this.object = object;
	}
	
	@Override
	public Double evaluate(GameObject asking, GameObjectEnvironment world) {
		return object.evaluate(asking, world).getHeading();
	}

}
