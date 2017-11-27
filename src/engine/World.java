package engine;
import java.util.List;

import player.PlayerManager;

public interface World extends Iterable<GenericObject>{
	
	public void addGameObject(GameObject obj);
	public void addGameObjects(List<GameObject> obj);
	public void removeGameObject(GameObject obj);
	public void removeGameObjects(List<GameObject> obj);
	public List<GameObject> getWithTag(String tag);
	
	//The World should have its own name to make things easily distinguishable.
	public boolean isNamed(String tag);
	
	public void step();
	
	public void addGlobalVars(GlobalVariables gv);
	
	public GlobalVariables getGlobalVars();
	
	public PlayerManager getPlayerManager();
	
	public void setNextWorld(World w);
	
	public World getNextWorld();
}
