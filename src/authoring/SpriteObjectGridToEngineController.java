package authoring;

import java.util.ArrayList;
import java.util.List;

import engine.EngineController;
import engine.GameMaster;
import engine.GameObject;
import engine.GameWorld;
import engine.GameLayer;
import engine.Layer;
import engine.utilities.data.GameDataHandler;

public class SpriteObjectGridToEngineController {
	
	private GameMaster myEC;
	private GameDataHandler GDH;

	public SpriteObjectGridToEngineController(String projectName){
		GDH = new GameDataHandler(projectName);
	}
	
	public void createWorldAndAddToEngine(SpriteObjectGridManagerI SOGMI) {
		List<GameObject> GO_LIST = convertSpriteObjectGridToListOfGameObjects(SOGMI);
		GameWorld thisWorld = createWorld(GO_LIST);
		addWorldToEngine(thisWorld);
	}
	
	public void saveEngine() {
		try{
		GDH.saveGame(myEC);
		} catch (Exception e){
			throw new RuntimeException("Cant save game....");
		}
	}
	
	private GameObject convertToGameObject(SpriteObjectI SOI){
		GameObject GE = new GameObject();
		addParametersToGameObject(SOI, GE);
		addConditionsAndActionsToGameObject(SOI, GE);
		return GE;
		
	}

	private void addParametersToGameObject(SpriteObjectI SOI, GameObject gE) {
		for (ArrayList<SpriteParameterI> SPI_LIST: SOI.getParameters().values()){
			for (SpriteParameterI SPI: SPI_LIST){
				gE.addParameter(SPI.getName(), SPI.getValue());
			}
		}
	}
	
	private void addConditionsAndActionsToGameObject(SpriteObjectI SOI, GameObject gE){
		// NEED TO DO
		
	}
	
	private void addNewGameObject(ArrayList<GameObject> GO_LIST, GameObject GO){
		GO_LIST.add(GO);
	}
	
	private List<GameObject> convertSpriteObjectGridToListOfGameObjects(SpriteObjectGridManagerI SOGM_IN) {
		ArrayList<GameObject> GO_LIST = new ArrayList<GameObject>();
		for (SpriteObjectI SOI: SOGM_IN.getEntireListOfSpriteObjects()) {
			GameObject convertedToGameObject = convertToGameObject(SOI);
			addNewGameObject(GO_LIST,convertedToGameObject);
		}
		return GO_LIST;
	}
	
	private GameWorld createWorld(List<GameObject> GO_LIST) {
		GameLayer thisLayer = new GameLayer();
		addAllGameObjectsToLayer(GO_LIST, thisLayer);
		GameWorld thisWorld = new GameWorld();
		thisWorld.addLayer(thisLayer);
		return thisWorld;
	}
	
	private void addAllGameObjectsToLayer(List<GameObject> GO_LIST, GameLayer layer) {
		for (GameObject GO: GO_LIST) {
			layer.addGameObject(GO);
		}
	}
	
	private void createEngine() {
		myEC = new GameMaster();
	}
	
	private void addWorldToEngine(GameWorld newWorld) {
		myEC.addWorld(newWorld);
	}
}
