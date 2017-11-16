package authoring;

import java.util.ArrayList;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class AuthoringEnvironmentManager {
	
	private SpriteObjectI defaultEmptySprite;
	private SpriteParameterSidebarManager SPSM;
	private SpriteObjectGridManagerI SOGM;
	private ArrayList<SpriteObjectI> defaultSprites;
	private ArrayList<SpriteObjectI> userSprites;
	
	public AuthoringEnvironmentManager() {
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
		System.out.println("DefSpriteSize: " + defaultSprites.size());
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
	
	public VBox getActiveCellParameters() throws Exception{
		return SPSM.getParameters(SOGM);
	}
	
	public SpriteObjectGridManagerI getGridManager(){
		return SOGM;
	}
	

}