package authoring;

import java.util.ArrayList;

import engine.utilities.data.GameDataHandler;
import javafx.scene.layout.VBox;

public class AuthoringEnvironmentManager {

	private SpriteObjectI defaultEmptySprite;
	private SpriteParameterSidebarManager SPSM;
	private SpriteObjectGridManagerI SOGM;
	private ArrayList<SpriteObject> defaultSprites;
	private ArrayList<SpriteObject> userSprites;
	private GameDataHandler myGDH;

	public AuthoringEnvironmentManager(String projectName) {
		myGDH = new GameDataHandler(projectName);
		defaultEmptySprite = new SpriteObject();
		SOGM = new SpriteObjectGridManager();
		SPSM = new SpriteParameterSidebarManager(SOGM);
		defaultSprites = new ArrayList<SpriteObject>();
		userSprites = new ArrayList<SpriteObject>();
	}
	
	public AuthoringEnvironmentManager(){
		this("TestProject");
	}
	
	public GameDataHandler getGameDataHandler(){
		return myGDH;
	}
	
	public SpriteParameterSidebarManager getSpriteParameterSidebarManager() {
		return SPSM;
	}

	
	public ArrayList<SpriteObject> getDefaultGameSprites() {
		return new ArrayList<SpriteObject>(defaultSprites);
	}
	
	public void addDefaultSprite(SpriteObject SOI){
		defaultSprites.add(SOI);
	}

	public ArrayList<SpriteObject> getUserDefinedSprites() {
		return new ArrayList<SpriteObject>(userSprites);

	}

	public void addUserSprite(SpriteObject SOI) {
		userSprites.add(SOI);
	}

	public SpriteObjectI getDefaultEmptySprite() {
		return defaultEmptySprite;
	}

	
	public SpriteObject getActiveCell() throws Exception{
		return SPSM.getActiveSprite();
	}

	public SpriteObjectGridManagerI getGridManager() {
		return SOGM;
	}
	
	
	

}
