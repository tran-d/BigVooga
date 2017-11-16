package voogasalad_bigvooga;

import java.util.ArrayList;

import javafx.scene.control.ScrollPane;

public class AuthoringEnvironmentManager {
	
	private SpriteObjectI defaultEmptySprite;
	private SpriteParameterSidebarManager SPSM;
	private SpriteObjectGridManagerI SOGM;
	private ArrayList<SpriteObjectI> defaultSprites;
	private ArrayList<SpriteObjectI> userSprites;
	
	AuthoringEnvironmentManager() {
		defaultEmptySprite = new SpriteObject();
		SPSM = new SpriteParameterSidebarManager();
		SOGM = new SpriteObjectGridManager();
		defaultSprites = new ArrayList<SpriteObjectI>();
		userSprites = new ArrayList<SpriteObjectI>();
	}
	
	public ArrayList<SpriteObjectI> getDefaultGameSprites() {
		return defaultSprites;
	}
	
	public void addDefaultSprite(SpriteObjectI SOI){
		defaultSprites.add(SOI);
	}
	
	public ArrayList<SpriteObjectI> getUserDefinedSprites(){
		return userSprites;
		
	}
	
	public void addUserSprite(SpriteObjectI SOI){
		userSprites.add(SOI);
	}
	
	public SpriteObjectI getDefaultEmptySprite() {
		return defaultEmptySprite;
	}
	
	public ScrollPane getActiveCellParameters() throws Exception{
		return SPSM.getParameters(SOGM);
	}
	
	public SpriteObjectGridManagerI getGridManager(){
		return SOGM;
	}
	

}
