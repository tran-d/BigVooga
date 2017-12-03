package engine.operations.gameobjectops;

import engine.GameObject;
import engine.Layer;

public class Self implements GameObjectOperation {

	public Self() {}
	
	@Override
	public GameObject evaluate(GameObject asking, Layer world) {
		return asking;
	}

	
}
