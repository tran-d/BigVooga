package engine.parameterGetters;

import engine.GameObject;
import engine.Layer;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public interface GameObjectGetter {	
	public GameObject get(GameObject asking, Layer world);
}
