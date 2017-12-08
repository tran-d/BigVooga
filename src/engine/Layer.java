package engine;
import java.util.List;

import controller.player.PlayerManager;

public interface Layer{
	
	public void addGameObject(GameObject obj);
	public void addGameObject(String name, double x, double y, double heading);
	
	public void addGameObjects(List<GameObject> obj);
	public void removeGameObject(GameObject obj);
	public void removeGameObjects(List<GameObject> obj);
	public List<GameObject> getWithTag(String tag);
	public GameObject getWithName(String name);
	
	public boolean isNamed(String tag);
	
	/**
	 * Calls Step() on each GameObject. Should never be called directly.
	 */
	public void step();
	
	public void addGlobalVars(GlobalVariables gv);
	
	public GlobalVariables getGlobalVars();
	
	public PlayerManager getPlayerManager();
	
	public void setPlayerManager(PlayerManager p);
	
	public void setBlueprints(GameObjectFactory f);
	
	public List<GameObject> getAllObjects();
}
