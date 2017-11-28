package engine.parameterGetters;

import engine.GameObject;
import engine.World;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public interface GameObjectGetter {	
	public GameObject get(GameObject asking, World world);
}
