package engine.parameterGetters.gameObjectGetters;

import engine.GameObject;
import engine.World;
import engine.parameterGetters.GameObjectGetter;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Nearest implements GameObjectGetter {

	private String tag;
	
	public Nearest(String tag) {
		this.tag = tag;
	}
	
	@Override
	public GameObject get(GameObject asking, World world) {
		double minDistanceSquared = Double.POSITIVE_INFINITY;
		GameObject nearest = null;
		for(GameObject go : world.getWithTag(tag)) {
			double distanceSquared = Math.pow(asking.getX()-go.getX(), 2)+Math.pow(asking.getY()-go.getY(), 2);
			if(distanceSquared < minDistanceSquared) {
				minDistanceSquared = distanceSquared;
				nearest = go;
			}
		}
		return nearest;
	}
}
