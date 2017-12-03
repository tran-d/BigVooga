package engine.operations.stringops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;

public class NameOf implements StringOperation {

	private GameObjectOperation object;

	public NameOf(GameObjectOperation object) {
		this.object = object;
	}
	
	@Override
	public String evaluate(GameObject asking, Layer world) {
		return object.evaluate(asking, world).getName();
	}

}
