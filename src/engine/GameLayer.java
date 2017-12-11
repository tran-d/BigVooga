package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

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
	private Map<Integer, List<GameObject>> conditionPriorities = new ConcurrentSkipListMap<>();
	private Map<Integer, GameObject> idToGameObject = new HashMap<>();

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
		for (Integer i : obj.getPriorities()) {
			if (conditionPriorities.containsKey(i)) {
				conditionPriorities.get(i).add(obj);
			} else {
				List<GameObject> objects = new ArrayList<>();
				objects.add(obj);
				conditionPriorities.put(i, objects);
			}
		}
	}

	@Override
	public void addElement(Element obj) {
		worldElements.add(obj);
	}

	@Override
	public void addElements(List<Element> obj) {
		for (Element o : obj) {
			addElement(o);
		}
	}

	@Override
	public void removeGameObject(GameObject obj) {
		removeElement(obj);
		objects.remove(obj);
		idToGameObject.remove(obj.getUniqueID());
		for (Integer i : obj.getPriorities()) {
			conditionPriorities.get(i).remove(obj);
			if (conditionPriorities.get(i).isEmpty()) {
				conditionPriorities.remove(i);
			}
		}
	}

	@Override
	public void removeElement(Element obj) {
		worldElements.remove(obj);
	}

	@Override
	public void removeElements(List<Element> obj) {
		for (Element o : obj) {
			removeElement(o);
		}
	}

	@Override
	public List<GameObject> getObjectsWithTag(String tag) {
		List<GameObject> tempList = new ArrayList<>();
		for (GameObject o : objects) {
			if (o.is(tag))
				tempList.add(o);
		}
		return tempList;
	}

	public Element getByID(int id) {
		return idToGameObject.get(id);
	}

	public boolean isNamed(String tag) {
		return worldName.equals(tag);
	}

	public void step(ConcreteGameObjectEnvironment environment) {
		List<Runnable> runnables = new ArrayList<>();
		environment.setLayer(this);
		for (Element obj : worldElements) {
			obj.step(environment);
		}
		for (Integer i : conditionPriorities.keySet()) {
			for (GameObject obj : conditionPriorities.get(i)) {
				obj.step(i, environment, runnables);
			}
			for (Runnable r : runnables) {
				r.run();
			}
			runnables.clear();
		}
	}

	@Override
	public List<Element> getAllElements() {
		return worldElements;
	}

	// TODO: Get all displayables

	@Override
	public GameObject getWithName(String name) {
		for (GameObject go : objects) {
			if (go.getName().equals(name))
				return go;
		}
		throw new RuntimeException("None by name " + name);// TODO
	}

	public List<GameObject> getAllObjects() {
		return objects;
	}

}
