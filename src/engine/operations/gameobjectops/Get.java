package engine.operations.gameobjectops;

import engine.GameObject;
import engine.Layer;

public class Get implements GameObjectOperation {

	private GameObject value;
	
	public Get(GameObject value) {
		this.value = value;
	}
	
	@Override
	public GameObject evaluate(GameObject asking, Layer world) {
		return value;
	}

}
