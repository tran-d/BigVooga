package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds Layers, which hold GameObjects. An Example of a GameWorld would be a
 * tavern room or a dark forest.
 * 
 * @author Nikolas Bramblett, ...
 *
 */
public class GameWorld {

	private final static String DEFAULT_NAME = "layer";

	private String worldName;
	private List<Layer> worldLayers;
	
	public GameWorld() {
		this(DEFAULT_NAME);
	}

	public GameWorld(String name) {
		worldName = name;
		worldLayers = new ArrayList<>();
	}

	public boolean isNamed(String name) {
		return worldName.equals(name);
	}

	/**
	 * Calls step() on each layer
	 * @param environment 
	 */
	public void step(ConcreteGameObjectEnvironment environment) {
		environment.setGameWorld(this);
		for (Layer l : worldLayers)
			l.step(environment);
	}

	public List<Element> getAllElements() {
		List<Element> els = new ArrayList<>();
		for (Layer l : worldLayers) {
			els.addAll(l.getAllElements());
		}
		return els;
	}

	public void addLayer(GameLayer layer) {
		worldLayers.add(layer);
	}

	public void removeLayer(String layerName) {
		for (Layer l : worldLayers) {
			if (l.isNamed(layerName)) {
				worldLayers.remove(l);
				return;
			}
		}
		// Placeholder for error I guess?
		System.out.println("No such world");
	}
	public List<Layer> getLayers()
	{
		return worldLayers;
	}

}
