package engine;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import controller.player.PlayerManager;
/**
 * 
 * @author Nikolas Bramblett, ...
 *
 */
public class GameLayer implements Layer {
	
	private final static String DEFAULT_NAME = "llayer";
	public final static String PLAYER_TAG = "Player";
	
	private String worldName;
	private List<Element> elements = new ArrayList<>();
	private List<GameObject> worldObjects = new ArrayList<>();
	private Map<Integer, List<GameObject>> conditionPriorities = new ConcurrentSkipListMap<>();
	private Map<Integer, GameObject> idToGameObject = new HashMap<>();
	private GlobalVariables globalVars;
	//private GameObjectFactory GameObjectFactory;
	private PlayerManager input;
	
	private GameObjectFactory blueprints;
	private String nextWorld;

	public GameLayer() {
		this(DEFAULT_NAME);
	}
	
	public GameLayer(String name) {
		worldName = name;
	}

	public void addElement(Element e) {
		elements.add(e);
	}
	
	@Override
	public void addGameObject(GameObject obj) {
		worldObjects.add(obj);
		elements.add(obj);
		idToGameObject.put(obj.getUniqueID(), obj);
		for(Integer i : obj.getPriorities()) {
			if(conditionPriorities.containsKey(i)) {
				conditionPriorities.get(i).add(obj);
			}
			else {
				List<GameObject> objects = new ArrayList<>();
				objects.add(obj);
				conditionPriorities.put(i, objects);
			}
		}
	}
	
	@Override
	public void addGameObjects(List<GameObject> obj) {
		for(GameObject o : obj) {
			addGameObject(o);
		}
	}

	@Override
	public void removeGameObject(GameObject obj) {
		worldObjects.remove(obj);
		idToGameObject.remove(obj.getUniqueID());
		for(Integer i : obj.getPriorities()) {
			conditionPriorities.get(i).remove(obj);
			if(conditionPriorities.get(i).isEmpty()) {
				conditionPriorities.remove(i);
			}
		}
	}
	
	@Override
	public void removeGameObjects(List<GameObject> obj) {
		for(GameObject o : obj) {
			removeGameObject(o);
		}
	}

	@Override
	public List<GameObject> getWithTag(String tag) {
		List<GameObject> tempList = new ArrayList<>();
		for (GameObject o : worldObjects) {
			for (String s : o.getTags()) {
				if (s.equals(tag)) {
					tempList.add(o);
					break;
				}
			}
		}
		return tempList;
	}
	
	public GameObject getByID(int id) {
		return idToGameObject.get(id);
	}
	
	public boolean isNamed(String tag) {
		return worldName.equals(tag);
	}
	
	public void step() {
		List<Runnable> runnables = new ArrayList<>();
		try {
			for(Integer i: conditionPriorities.keySet()) {
				for(GameObject obj : conditionPriorities.get(i)) {
					obj.step(this, i, runnables);
				}
				for(Runnable r : runnables) {
					r.run();
				}
				runnables.clear();
			}
		}
		catch (ConcurrentModificationException e) {
			// do nothing
		}
	}
	
	@Override
	public GlobalVariables getGlobalVars() {
		return globalVars;
	}

	@Override
	public void addGlobalVars(GlobalVariables gv) {
		// TODO Auto-generated method stub

		globalVars = gv;
		
	}
	
	@Override
	public PlayerManager getPlayerManager() {
		return input;
	}
	
	public List<Element> getAllElements() {
		return new ArrayList<>(elements);
	}
	
	public List<GameObject> getAllObjects()
	{
		return new ArrayList<>(worldObjects);
	}

	@Override
	public void setPlayerManager(PlayerManager input) {
		this.input = input;
	}

	@Override
	public GameObject getWithName(String name) {
		for(GameObject go : worldObjects) {
			if(go.getName().equals(name))
				return go;
		}
		throw new RuntimeException("None by name "+name);//TODO
	}

	@Override
	public void setBlueprints(GameObjectFactory f) {
		// TODO Auto-generated method stub
		blueprints = f;
	}

	@Override
	public void addGameObject(String name, double x, double y, double heading) {
		// TODO Auto-generated method stub
		GameObject temp = blueprints.getInstanceOf(name);
		temp.setCoords(x, y);
		temp.setHeading(heading);
		addGameObject(temp);
	}

	@Override
	public void setNextWorld(String nextWorld) {
		this.nextWorld = nextWorld;
	}

	public String getNextWorld() {
		return nextWorld;
	}
}
