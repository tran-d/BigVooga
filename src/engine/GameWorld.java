package engine;

import java.util.ArrayList;
import java.util.List;

import controller.player.PlayerManager;

/**
 * Holds Layers, which hold GameObjects. An Example of a GameWorld would be a tavern room or a dark forest.
 * @author Nikolas Bramblett, ...
 *
 */
public class GameWorld {
	
	private List<GameLayer> worldLayers;
	private GameWorld nextWorld;

	private final static String DEFAULT_NAME = "layer";
	
	private String worldName;
	private GlobalVariables globalVars;
	//private GameObjectFactory GameObjectFactory;
	
	public GameWorld() {
		// TODO Auto-generated constructor stub
		this(DEFAULT_NAME);
	}
	
	public GameWorld(String name) {
		nextWorld = this;
		worldName = name;
		worldLayers = new ArrayList<>();
	}

	
	public boolean isNamed(String name) {
		return worldName.equals(name);
	}

	/**
	 * Calls step() on each layer
	 */
	public void step() {
		for(GameLayer l: worldLayers)
			l.step();
	}

	public void addGlobalVars(GlobalVariables gv) {
		
		globalVars = gv;
	}

	public GlobalVariables getGlobalVars() {
		return globalVars;
	}

	public void setNextWorld(GameWorld w) {
		nextWorld = w;

	}
	
	public GameWorld getNextWorld() {
		return nextWorld;
	}

	public List<GameObject> getAllObjects() {
		// TODO Auto-generated method stub
		List<GameObject> objects = new ArrayList<>();
		for(GameLayer l: worldLayers){
			objects.addAll(l.getAllObjects());
		}
		return objects;
	}
	
	public void setPlayerManager(PlayerManager input) {
		for(GameLayer l: worldLayers)
			l.setPlayerManager(input);
	}
	
	public void addLayer(GameLayer layer)
	{
		worldLayers.add(layer);
	}
	
	public void removeLayer(String layerName)
	{
		for(GameLayer l: worldLayers)
		{
			if(l.isNamed(layerName))
			{
				worldLayers.remove(l);
				return;
			}
		}
		//Placeholder for error I guess?
		System.out.println("No such world");
	}

}
