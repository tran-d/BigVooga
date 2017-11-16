package authoring;

import java.util.ArrayList;

import javafx.scene.control.ScrollPane;

public class AuthoringEnvironmentManager {
	
	private SpriteObjectI defaultEmptySprite;
	private SpriteParameterSidebarManager SPSM;
	private SpriteObjectGridManagerI SOGM;
	
	AuthoringEnvironmentManager() {
		defaultEmptySprite = new SpriteObject();
		SPSM = new SpriteParameterSidebarManager();
		SOGM = new SpriteObjectGridManager();
	}
	
	public ArrayList<SpriteObjectI> getDefaultGameSprites() {
		return null;
		
	}
	
	public ArrayList<SpriteObjectI> getUserDefinedSprites(){
		return null;
		
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
