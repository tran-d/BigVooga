package authoring_data;

import engine.utilities.data.GameDataHandler;

public class AuthoringDataManager {
	
	SpriteObjectGridToEngineController mySOGTEC;
	GameDataHandler myGDH;
	AuthoringMapDataManager myAMDM;
	
	AuthoringDataManager(GameDataHandler GDH){
		myGDH = GDH;
		createGridToEngineController();
	}
	
	private void createGridToEngineController(){
		mySOGTEC = new SpriteObjectGridToEngineController(myGDH);
	}
	
	private void createMapDataManager(){
		myAMDM = new AuthoringMapDataManager();
	}

}
