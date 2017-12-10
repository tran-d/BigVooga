package engine;

import java.util.List;

import controller.player.PlayerManager;
import engine.operations.doubleops.DoubleOperation;

public class ConcreteGameObjectEnvironment implements GameObjectEnvironment {

	private GameMaster master;
	private GameWorld world;
	private Layer layer;

	public void setGameMaster(GameMaster master) {
		this.master = master;
	}

	public void setGameWorld(GameWorld world) {
		this.world = world;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	@Override
	public PlayerManager getPlayerManager() {
		return master.getPlayerManager();
	}

	@Override
	public void addGameObject(GameObject obj) {
		layer.addGameObject(obj);
	}

	@Override
	public GameObject getWithName(String name) {
		return layer.getWithName(name);
	}

	@Override
	public void removeGameObject(GameObject withName) {
		layer.removeGameObject(withName);
	}

	@Override
	public void setNextWorld(String nextWorld) {
		master.setNextWorld(nextWorld);
	}

	@Override
	public List<GameObject> getObjectsWithTag(String tag) {
		return layer.getObjectsWithTag(tag);
	}

	@Override
	public void transfer(GameObject gameObject, String newWorld) {
		//TODO ?? master.getWorldWithName(newWorld).addToLayer(gameObject, layer.number()??) ??  
				//It's unclear how to resolve this in a reasonable way
	}
	
	@Override
	public void addElement(Element e) {
		layer.addElement(e);
	}

	@Override
	public GameObject getGameObject(String name) {
		return master.getBlueprints().getInstanceOf(name);
	}

	@Override
	public void save(DoubleOperation currentPoints) {
		master.save();
	}

}