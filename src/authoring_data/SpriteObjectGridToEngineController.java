package authoring_data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import authoring.*;
import engine.EngineController;
import engine.GameLayer;
import engine.GameMaster;
import engine.GameObject;
import engine.GameWorld;
import engine.GameLayer;
import engine.Layer;

import engine.utilities.data.GameDataHandler;
import player.PlayerManager;

public class SpriteObjectGridToEngineController {
	

	EngineController myEC;
	GameDataHandler myGDH;
	PlayerManager myPM;

	public SpriteObjectGridToEngineController(GameDataHandler GDH){
		myGDH = GDH;
	}
	
	public void createWorldAndAddToEngine(SpriteObjectGridManagerI SOGMI) {

		GameWorld thisWorld = createWorld();
		List<GameObject> GO_LIST = convertSpriteObjectGridToListOfGameObjects(SOGMI);
		addAllGameObjectsToWorld(GO_LIST, thisWorld);
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
		addParametersToGameObject(SOI, GE);
		addConditionsAndActionsToGameObject(SOI, GE);
		return GE;
		
	}

	private void addParametersToGameObject(SpriteObjectI SOI, GameObject GE) {
		for (ArrayList<SpriteParameterI> SPI_LIST: SOI.getParameters().values()){
			for (SpriteParameterI SPI: SPI_LIST){

				if (SPI instanceof DoubleSpriteParameter){
					GE.setDoubleVariable(SPI.getName(), (double) SPI.getValue());
				} else if(SPI instanceof StringSpriteParameter){
					GE.setStringVariable(SPI.getName(), (String) SPI.getValue());
				} else if (SPI instanceof BooleanSpriteParameter){
					GE.setBooleanVariable(SPI.getName(), (Boolean) SPI.getValue());
				}	
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
	

	private GameWorld createWorld() {
		GameWorld thisWorld = new GameWorld();
		return thisWorld;
	}
	
	private void addAllGameObjectsToWorld(List<GameObject> gO_LIST, GameWorld world) {
		GameLayer GL = createGameLayer();
		for (GameObject GO: gO_LIST) {
			GL.addGameObject(GO);
		}
		world.addLayer(GL);
	}
	
	private GameLayer createGameLayer() {
		return new GameLayer();
	}
	
	private void createEngine() {
		if (myPM == null){
			createPlayerManager();
		}
		myEC = new GameMaster();
		myEC.setPlayerManager(myPM);
	}
	
	private void createPlayerManager(){
		myPM = new PlayerManager();
	}
	
	private void addWorldToEngine(GameWorld newWorld) {

		if (myEC==null){
			createEngine();
		}
		myEC.addWorld(newWorld);
	}
}
