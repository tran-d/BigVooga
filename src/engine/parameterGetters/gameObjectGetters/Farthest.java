package engine.parameterGetters.gameObjectGetters;

import engine.GameObject;
import engine.World;
import engine.parameterGetters.GameObjectGetter;

public class Farthest implements GameObjectGetter {

	private String tag;
	
	public Farthest(String tag) {
		this.tag = tag;
	}
	
	@Override
	public GameObject get(GameObject asking, World world) {
		GameObject farthest = null;
		double maxDistanceSquared = Double.NEGATIVE_INFINITY;
		for(GameObject go : world.getWithTag(tag)) {
			double distanceSquared = Math.pow(asking.getX()-go.getX(), 2)+Math.pow(asking.getY()-go.getY(), 2);
			if (distanceSquared > maxDistanceSquared) {
				maxDistanceSquared = distanceSquared;
				farthest = go;
			}
		}	
		return farthest;
	}
}