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
	private List<Element> worldElements;
	private List<GameObject> objects = new ArrayList<>();
	private Map<Integer, List<Element>> conditionPriorities = new ConcurrentSkipListMap<>();
	private Map<Integer, GameObject> idToGameObject = new HashMap<>();
	private GlobalVariables globalVars;
	private PlayerManager input;
	
	private GameObjectFactory blueprints;
	private String nextWorld;

	public GameLayer() {
		this(DEFAULT_NAME);
	}
	
	public GameLayer(String name) {
		worldName = name;
		worldElements = new ArrayList<>();
	}
	
	public void addGameObject(GameObject obj) {
		addElement(obj);
		objects.add(obj);
		idToGameObject.put(obj.getUniqueID(), obj);
	}
	
	@Override
	public void addElement(Element obj) {
		worldElements.add(obj);
		for(Integer i : obj.getPriorities()) {
			if(conditionPriorities.containsKey(i)) {
				conditionPriorities.get(i).add(obj);
			}
			else {
				List<Element> objects = new ArrayList<>();
				objects.add(obj);
				conditionPriorities.put(i, objects);
			}
		}
	}
	
	@Override
	public void addElements(List<Element> obj) {
		for(Element o : obj) {
			addElement(o);
		}
	}

	public void removeGameObject(GameObject obj) {
		removeElement(obj);
		objects.remove(obj);
		idToGameObject.remove(obj.getUniqueID());
	}
	
	@Override
	public void removeElement(Element obj) {
		worldElements.remove(obj);
		for(Integer i : obj.getPriorities()) {
			conditionPriorities.get(i).remove(obj);
			if(conditionPriorities.get(i).isEmpty()) {
				conditionPriorities.remove(i);
			}
		}
	}
	
	@Override
	public void removeElements(List<Element> obj) {
		for(Element o : obj) {
			removeElement(o);
		}
	}
	
	@Override
	public List<GameObject> getObjectsWithTag(String tag) {
		List<GameObject> tempList = new ArrayList<>();
		for (GameObject o : objects) {
			for (String s : o.getTags()) {
				if (s.equals(tag)) {
					tempList.add(o);
					break;
				}
			}
		}
		return tempList;
	}
	
	public Element getByID(int id) {
		return idToGameObject.get(id);
	}
	
	public boolean isNamed(String tag) {
		return worldName.equals(tag);
	}
	
	public void step() {
		List<Runnable> runnables = new ArrayList<>();
		try {
			for(Integer i: conditionPriorities.keySet()) {
				for(Element obj : conditionPriorities.get(i)) {
					obj.step(i, this, runnables);
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
		globalVars = gv;
	}
	
	@Override
	public PlayerManager getPlayerManager() {
		return input;
	}
	
	@Override
	public List<Element> getAllElements() {
		return worldElements;
	}

	@Override
	public void setPlayerManager(PlayerManager input) {
		this.input = input;
	}

	@Override
	public Element getWithName(String name) {
		for(Element go : worldElements) {
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
		addElement(temp);
	}
	
	@Override
	public void setNextWorld(String nextWorld) {
		this.nextWorld = nextWorld;
	}

	public String getNextWorld() {
		return nextWorld;
	}

}
