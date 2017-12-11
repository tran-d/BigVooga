package engine;

import java.util.ArrayList;
import java.util.List;

import engine.sprite.Displayable;
import engine.utilites.camera.Camera;
import gui.welcomescreen.WelcomeScreen;
import javafx.geometry.Point2D;

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
	private List<GameLayer> worldLayers;
	private Camera camera;
	
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
	
	public List<Displayable> getAllDisplayables() {
		List<Displayable> ret = new ArrayList<>();
		GameObject player = getPlayerObject();
		camera = new Camera(player);
		camera.moveToPlayer();
		for(Element e : getAllElements()) {
			Displayable image = e.getDisplayable();
			Point2D relCoords = camera.makeCoordinatesRelative(e.getX(), e.getY());
			image.setPosition(relCoords.getX(), relCoords.getY());
			ret.add(image);
		}
		return ret;
	}
	
	private GameObject getPlayerObject() {
		for(GameLayer l : worldLayers) {
			List<GameObject> player = l.getObjectsWithTag("Player");			//TODO: Make constant
			if(player.size() > 0) return player.get(0);
		}
		return worldLayers.get(0).getAllObjects().get(0);
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
	
	public List<GameLayer> getLayers() {
		return worldLayers;
	}
	public Point2D makeScreenCoordinatesAbsolute(double x, double y)
	{
		return camera.makeCoordinatesAbsolute(x, y);
	}

}
