package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import player.PlayerManager;

public class GameWorld implements World {
	
	private final static String DEFAULT_NAME = "world";
	public final static String PLAYER_TAG = "Player";
	
	private String worldName;
	private List<GameObject> worldObjects;
	private Map<Integer, List<GameObject>> conditionPriorities;
	private VariableContainer globalVars;
	private GameObjectFactory GameObjectFactory;
	private PlayerManager input;
	private World nextWorld;

	public GameWorld() {
		// TODO Auto-generated constructor stub
		this(DEFAULT_NAME);
	}
	
	public GameWorld(String name) {
		worldName = name;
		worldObjects = new ArrayList<>();
		input = new PlayerManager();
		nextWorld = this;
	}

	// I don't know what to do with this.
	@Override
	public Iterator<GenericObject> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addGameObject(GameObject obj) {
		// TODO Auto-generated method stub
		worldObjects.add(obj);
		for(Integer i : obj.getPriorities()) {
			if(conditionPriorities.containsKey(i)) {
				conditionPriorities.get(i).add(obj);
			}
			else {
				List<GameObject> objects = new ArrayList();
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
		// TODO Auto-generated method stub
		worldObjects.remove(obj);
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
		// TODO Auto-generated method stub
		List<GameObject> tempList = new ArrayList<>();
		for (GameObject o : worldObjects) {
			for (String s : o.getTags()) {
				if (s.equals(tag)) {
					tempList.add(o);
					break;
				}
			}
		}
		return null;
	}
	
	public boolean isNamed(String tag) {
		return worldName.equals(tag);
	}
	
	public void step() {
		for(Integer i: conditionPriorities.keySet()) {
			for(GameObject obj : conditionPriorities.get(i)) {
				obj.step(this);
			}
		}
	}

	@Override
	public void addGlobalVars(VariableContainer gv) {
		// TODO Auto-generated method stub

		globalVars = gv;
		
	}
	
	@Override
	public PlayerManager getPlayerManager() {
		return input;
	}
	
	@Override
	public void setNextWorld(World w) {
		nextWorld = w;
	}
	
	@Override
	public World getNextWorld() {
		return nextWorld;
	}

}
