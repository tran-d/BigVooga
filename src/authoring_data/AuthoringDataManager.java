package authoring_data;

import authoring_UI.MapManager;
import engine.utilities.data.GameDataHandler;

public class AuthoringDataManager {
	
	SpriteObjectGridToEngineController mySOGTEC;
	GameDataHandler myGDH;
	AuthoringMapDataManager myAMDM;
	MapManager myMM;
	
	AuthoringDataManager(GameDataHandler GDH){
		myGDH = GDH;
		createGridToEngineController();
		createMapDataManager();
	}
	
	private void createGridToEngineController(){
		mySOGTEC = new SpriteObjectGridToEngineController(myGDH);
	}
	
	private void createMapDataManager(){
		myAMDM = new AuthoringMapDataManager();
	}
	
	private void save(){
		System.out.println("Saving config");
	}
	
	private void load(){
		System.out.println("Loading config");
	}


}
