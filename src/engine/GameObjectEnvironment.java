package engine;

import java.util.List;

import controller.player.PlayerManager;
import engine.operations.doubleops.DoubleOperation;

public interface GameObjectEnvironment {

	public PlayerManager getPlayerManager();
	public GameObject getWithName(String name);
	public void removeGameObject(GameObject withName);
	public void setNextWorld(String evaluate);
	public List<GameObject> getObjectsWithTag(String evaluate);
	public void addGameObject(GameObject obj);
	public GameObject getGameObject(String name);
	public void addElement(Element e);
	public void transfer(GameObject gameObject, String newWorld);
	public void save(DoubleOperation currentPoints);
	
}
