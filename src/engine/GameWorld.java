package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import engine.sprite.DisplayableImage;
import player.PlayerManager;

public class GameWorld implements World {
	
	private List<GameLayer> worldLayers;
	private World nextWorld;

	private final static String DEFAULT_NAME = "layer";
	public final static String PLAYER_TAG = "Player";
	
	private String worldName;
	private Map<Integer, List<GameObject>> conditionPriorities = new HashMap<>();
	private GlobalVariables globalVars;
	//private GameObjectFactory GameObjectFactory;
	private PlayerManager input;
	public GameWorld() {
		// TODO Auto-generated constructor stub
		this(DEFAULT_NAME);
	}
	public GameWorld(String name) {
		nextWorld = this;
		worldName = name;
		worldLayers = new ArrayList<>();
	}


	@Override
	public void addGameObject(GameObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addGameObjects(List<GameObject> obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeGameObject(GameObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeGameObjects(List<GameObject> obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<GameObject> getWithTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNamed(String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		for(GameLayer l: worldLayers)
			l.step();
	}

	@Override
	public void addGlobalVars(GlobalVariables gv) {
		// TODO Auto-generated method stub
		globalVars = gv;
	}

	@Override
	public GlobalVariables getGlobalVars() {
		// TODO Auto-generated method stub
		return globalVars;
	}

	@Override
	public PlayerManager getPlayerManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNextWorld(World w) {
		nextWorld = w;
	}
	
	@Override
	public World getNextWorld() {
		return nextWorld;
	}

	@Override
	public List<GameObject> getAllObjects() {
		// TODO Auto-generated method stub
		List<GameObject> objects = new ArrayList<>();
		for(GameLayer l: worldLayers){
			objects.addAll(l.getAllObjects());
		}
		return objects;
	}
	@Override
	public void setPlayerManager(PlayerManager input) {
		// TODO Auto-generated method stub
		for(GameLayer l: worldLayers)
		{
			l.setPlayerManager(input);
		}
	}

}
