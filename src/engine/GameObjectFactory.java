package engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ian Eldridge-Allegra
 */
public class GameObjectFactory {
	Map<String, GameObject> originals;
	
	public GameObjectFactory() {
		originals = new HashMap<>();
	}
	
	public void addBlueprint(GameObject obj) {
		originals.put(obj.getName(), obj.clone());
	}
	
	public GameObject getInstanceOf(String name) {
		try {
			return originals.get(name).clone();
		} catch(NullPointerException e) {
			throw new VoogaException("NoObjectByName", name);
		}
	}
}
