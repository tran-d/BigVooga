package engine;

import java.util.List;

import controller.player.PlayerManager;

public interface GameObjectEnvironment {

	public PlayerManager getPlayerManager();
	public GameObject getWithName(String name);
	public void removeGameObject(GameObject withName);
	public void setNextWorld(String evaluate);
	public List<GameObject> getObjectsWithTag(String evaluate);
	void addGameObject(String name, double x, double y, double heading);
	public void transfer(GameObject gameObject, String newWorld);
	
}
