package authoring;

import java.util.ArrayList;
import javafx.scene.layout.VBox;

public class AuthoringEnvironmentManager {

	private SpriteObjectI defaultEmptySprite;
	private SpriteParameterSidebarManager SPSM;
	private SpriteObjectGridManagerI SOGM;
	private ArrayList<SpriteObject> defaultSprites;
	private ArrayList<SpriteObjectI> userSprites;

	public AuthoringEnvironmentManager() {
		defaultEmptySprite = new SpriteObject();
		SOGM = new SpriteObjectGridManager();
		SPSM = new SpriteParameterSidebarManager(SOGM);
		defaultSprites = new ArrayList<SpriteObject>();
		userSprites = new ArrayList<SpriteObjectI>();
	}

	
	public ArrayList<SpriteObject> getDefaultGameSprites() {
		return new ArrayList<SpriteObject>(defaultSprites);
	}
	
	public void addDefaultSprite(SpriteObject SOI){
		defaultSprites.add(SOI);
	}

	public ArrayList<SpriteObjectI> getUserDefinedSprites() {
		return userSprites;

	}

	public void addUserSprite(SpriteObjectI SOI) {
		userSprites.add(SOI);
	}

	public SpriteObjectI getDefaultEmptySprite() {
		return defaultEmptySprite;
	}

	
	public SpriteObjectI getActiveCell() throws Exception{
		return SPSM.getActiveSprite();
	}

	public SpriteObjectGridManagerI getGridManager() {
		return SOGM;
	}

}
