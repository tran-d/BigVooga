package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements World {
	
	private final static String DEFAULT_NAME = "world";
	private String worldName;
	private List<GameObject> worldObjects;

	public GameWorld() {
		// TODO Auto-generated constructor stub
		this(DEFAULT_NAME);
	}
	
	public GameWorld(String name)
	{
		worldName = name;
		worldObjects = new ArrayList<GameObject>();
	}

	// I don't know what to do with this.
	@Override
	public Iterator<GameObject> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addGameObject(GameObject obj) {
		// TODO Auto-generated method stub
		worldObjects.add(obj);
	}

	@Override
	public void removeGameObject(GameObject obj) {
		// TODO Auto-generated method stub
		worldObjects.remove(obj);
	}

	@Override
	public List<GameObject> getWithTag(String tag) {
		// TODO Auto-generated method stub
		List<GameObject> tempList = new ArrayList<GameObject>();
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
	
	public boolean isNamed(String tag)
	{
		return worldName.equals(tag);
	}
	public void step() {
		for(GameObject obj: worldObjects)
		{
			//GameObjects should do something here
			
			System.out.println("World step placeholder");
		}
	}

}
