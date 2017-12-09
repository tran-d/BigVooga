package authoring_data;

import java.util.ArrayList;
import java.util.List;

import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import authoring.SpriteObjectI;
import authoring.SpriteParameterI;
import authoring_UI.DraggableGrid;
import authoring_UI.MapLayer;
import engine.GameLayer;
import engine.GameMaster;
import engine.GameObject;
import engine.GameWorld;
import engine.utilities.data.GameDataHandler;

public class SpriteObjectGridToEngineController {
	private GameMaster myEC;
	private GameDataHandler myGDH;
	private GameWorld currentWorld;

	public SpriteObjectGridToEngineController(GameDataHandler GDH){
		myGDH = GDH;
	}
	
	// called every time a grid is processed (new world is added to engine)
	public void createLayerAndAddToEngine(DraggableGrid currentGrid) { //SpriteObjectGridManagerI SOGMI
		List<MapLayer> allLayers = currentGrid.getLayers();
		createWorld();
		for (MapLayer thisLayer : allLayers) {
			createEngineLayerAndAddToWorld(thisLayer);
		}
		addWorldToEngine(currentWorld);
	}
	
	private void createWorld() {
		currentWorld = new GameWorld(); 
	}

	private void createEngineLayerAndAddToWorld(MapLayer thisLayer) {
		SpriteObjectGridManagerI SOGMI = thisLayer.getSpriteManager();
		List<GameObject> GO_LIST = convertSpriteObjectGridToListOfGameObjects(SOGMI);
		GameLayer engineLayer = createLayer(GO_LIST);
		addLayerToWorld(engineLayer);
	}

	private GameLayer createLayer(List<GameObject> gO_LIST) {
		GameLayer thisLayer = new GameLayer();
		addAllGameObjectsToLayer(gO_LIST, thisLayer);
		return thisLayer;
	}

	public void saveEngine() {
		try{
		myGDH.saveGame(myEC);
		} catch (Exception e){
			throw new RuntimeException("Cant save game....");
		}
	}
	
	private GameObject convertToGameObject(SpriteObject SOI){
		//added null as input to rid error
		GameObject GE = new GameObject(null);
		setPositionOfGameObject(SOI, GE);
		addParametersToGameObject(SOI, GE);
		addConditionsAndActionsToGameObject(SOI, GE);
		return GE;
	}
	
	private void setPositionOfGameObject(SpriteObject SOI, GameObject GO){
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
		// TODO 
		
	}
	
	private List<GameObject> convertSpriteObjectGridToListOfGameObjects(SpriteObjectGridManagerI SOGM_IN) {
		List<GameObject> GO_LIST = new ArrayList<GameObject>();
		for (SpriteObject SOI: SOGM_IN.getEntireListOfSpriteObjects()) {
			GameObject convertedToGameObject = convertToGameObject(SOI);
			GO_LIST.add(convertedToGameObject);
		}
		return GO_LIST;
	}
	
	private void addLayerToWorld(GameLayer layerToAdd) {
		currentWorld.addLayer(layerToAdd);
	}
	
	private void addAllGameObjectsToLayer(List<GameObject> GO_LIST, GameLayer layer) {
		for (GameObject GO: GO_LIST) {
			layer.addElement(GO);
		}
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
