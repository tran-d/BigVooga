package engine;
import java.util.List;

import controller.player.PlayerManager;

public interface Layer {
	
	public void addElement(Element obj);
	public void addGameObject(String name, double x, double y, double heading);
	
	public void addElements(List<Element> obj);
	public void removeElement(Element obj);
	public void removeElements(List<Element> obj);
	public List<GameObject> getObjectsWithTag(String tag);
	public Element getWithName(String name);
	
	public boolean isNamed(String tag);
	
	/**
	 * Calls Step() on each Element. Should never be called directly.
	 */
	public void step();
	
	public void addGlobalVars(GlobalVariables gv);
	
	public GlobalVariables getGlobalVars();
	
	public PlayerManager getPlayerManager();
	
	public void setPlayerManager(PlayerManager p);
	
	public void setBlueprints(GameObjectFactory f);

	public void setNextWorld(String evaluate);

	public List<Element> getAllElements();
}
