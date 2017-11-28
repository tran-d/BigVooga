package authoring;

import java.util.ArrayList;

import engine.EngineController;
import engine.GameMaster;
import engine.GameObject;
import engine.GameLayer;
import engine.World;
import engine.utilities.data.GameDataHandler;

public class SpriteObjectGridToEngineController {
	
	EngineController myEC;
	GameDataHandler GDH;

	public SpriteObjectGridToEngineController(String projectName){
		GDH = new GameDataHandler(projectName);
	}
	
	public void createWorldAndAddToEngine(SpriteObjectGridManagerI SOGMI) {
		World thisWorld = createWorld();
		ArrayList<GameObject> GO_LIST = convertSpriteObjectGridToListOfGameObjects(SOGMI);
		addAllGameObjectsToWorld(GO_LIST, thisWorld);
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
				if (SPI instanceof DoubleSpriteParameter){
					gE.setDoubleVariable(SPI.getName(), (double) SPI.getValue());
				} else if(SPI instanceof StringSpriteParameter){
					gE.setStringVariable(SPI.getName(), (String) SPI.getValue());
				} else if (SPI instanceof BooleanSpriteParameter){
					gE.setBooleanVariable(SPI.getName(), (Boolean) SPI.getValue());
				}	
			}
			
		}
	}
	
	private void addConditionsAndActionsToGameObject(SpriteObjectI SOI, GameObject gE){
		// NEED TO DO
	}
	
	private void addNewGameObject(ArrayList<GameObject> GO_LIST, GameObject GO){
		GO_LIST.add(GO);
	}
	
	private ArrayList<GameObject> convertSpriteObjectGridToListOfGameObjects(SpriteObjectGridManagerI SOGM_IN) {
		ArrayList<GameObject> GO_LIST = new ArrayList<GameObject>();
		for (SpriteObjectI SOI: SOGM_IN.getEntireListOfSpriteObjects()) {
			GameObject convertedToGameObject = convertToGameObject(SOI);
			addNewGameObject(GO_LIST,convertedToGameObject);
		}
		return GO_LIST;
	}
	
	private World createWorld() {
		World thisWorld = new GameLayer();
		return thisWorld;
	}
	
	private void addAllGameObjectsToWorld(ArrayList<GameObject> GO_LIST, World world) {
		for (GameObject GO: GO_LIST) {
			world.addGameObject(GO);
		}
	}
	
	private void createEngine() {
		myEC = new GameMaster();
	}
	
	private void addWorldToEngine(World newWorld) {
		myEC.addWorld(newWorld);
	}
	
	

}
