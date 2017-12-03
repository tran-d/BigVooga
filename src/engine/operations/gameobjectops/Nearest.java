package engine.operations.gameobjectops;

import java.util.List;

import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;

public class Nearest implements GameObjectOperation{

	private StringOperation tag;
	
	public Nearest(StringOperation tag) {
		this.tag = tag;
	}
	
	@Override
	public GameObject evaluate(GameObject asking, Layer world) {
		List<GameObject> allObjects = world.getWithTag(tag.evaluate(asking, world));
		//Magnitude of VectorDifference of Location
		double distance = Double.MAX_VALUE;
		GameObject nearest = asking;
		for(GameObject obj : allObjects) {
			
		}
	}

}
