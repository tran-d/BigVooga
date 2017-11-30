package authoring_data;

import java.util.ArrayList;
import java.util.List;

import authoring.SpriteObjectGridManagerI;
import authoring.SpriteObjectI;
import authoring.SpriteParameterI;
import engine.GameLayer;
import engine.GameMaster;
import engine.GameObject;
import engine.GameWorld;
import engine.utilities.data.GameDataHandler;

public class SpriteObjectGridToEngineController {
	private GameMaster myEC;
	private GameDataHandler myGDH;

	public SpriteObjectGridToEngineController(GameDataHandler GDH){
		myGDH = GDH;
	}
	
	public void createWorldAndAddToEngine(SpriteObjectGridManagerI SOGMI) {
		List<GameObject> GO_LIST = convertSpriteObjectGridToListOfGameObjects(SOGMI);
		GameWorld thisWorld = createWorld(GO_LIST);
		addWorldToEngine(thisWorld);
	}
	
	public void saveEngine() {
		try{
		myGDH.saveGame(myEC);
		} catch (Exception e){
			throw new RuntimeException("Cant save game....");
		}
	}
	
	private GameObject convertToGameObject(SpriteObjectI SOI){
		//added null as input to rid error
		GameObject GE = new GameObject(null);
		setPositionOfGameObject(SOI, GE);
		addParametersToGameObject(SOI, GE);
		addConditionsAndActionsToGameObject(SOI, GE);
		return GE;
		
	}
	
	private void setPositionOfGameObject(SpriteObjectI SOI, GameObject GO){
		GO.setCoords(SOI.getXCenterCoordinate(), SOI.getYCenterCoordinate());
	}

	private void addParametersToGameObject(SpriteObjectI SOI, GameObject GE) {
		for (ArrayList<SpriteParameterI> SPI_LIST: SOI.getParameters().values()){
			for (SpriteParameterI SPI: SPI_LIST){
				GE.addParameter(SPI.getName(), SPI.getValue());
			}
		}
	}
	
	private void addConditionsAndActionsToGameObject(SpriteObjectI SOI, GameObject GE){
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
	
	private GameLayer createGameLayer() {
		return new GameLayer();
	}
	
	private void createEngine() {
		myEC = new GameMaster();
	}
	
	private void addWorldToEngine(GameWorld newWorld) {
		if (myEC==null){
			createEngine();
		}
		myEC.addWorld(newWorld);
	}
}
