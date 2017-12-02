package authoring;

import java.util.ArrayList;

import authoring_UI.DraggableGrid;
import authoring_UI.SpriteSet;
import authoring_UI.SpriteSetDefault;
import authoring_UI.SpriteSetUserDefined;
import engine.utilities.data.GameDataHandler;
import javafx.scene.layout.VBox;

public class AuthoringEnvironmentManager {

	private SpriteObject defaultEmptySprite;
	private SpriteParameterSidebarManager SPSM;
	private SpriteObjectGridManagerI SOGM;
	private ArrayList<SpriteObject> defaultSprites;
	private ArrayList<SpriteObject> userSprites;
	private GameDataHandler myGDH;
	private SpriteSet myDefaultSprites;
	private SpriteSet myCustomSprites;
	private DraggableGrid myGrid;

	public AuthoringEnvironmentManager(String projectName) {
		myGDH = new GameDataHandler(projectName);
		defaultEmptySprite = new SpriteObject();
		myGrid = new DraggableGrid();
//		SOGM = new SpriteObjectGridManager();
		SPSM = new SpriteParameterSidebarManager(myGrid);
		initializeDefaultSprites();
		initializeCustomSprites();
//		defaultSprites = new ArrayList<SpriteObject>();
//		userSprites = new ArrayList<SpriteObject>();
	}
	
	public DraggableGrid getDraggableGrid(){
		return myGrid;
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
	
	private void initializeDefaultSprites(){
		myDefaultSprites = new SpriteSetDefault(myGDH);
	}
	
	private void initializeCustomSprites(){
		myCustomSprites = new SpriteSetUserDefined(myGDH);
	}
	
	public SpriteSet getDefaultSpriteController(){
		return myDefaultSprites;
	}
	
	public SpriteSet getCustomSpriteController(){
		return myCustomSprites;
	}

	
	public ArrayList<SpriteObject> getDefaultGameSprites() {
//		return new ArrayList<SpriteObject>(defaultSprites);
		return myDefaultSprites.getAllSprites();
	}
	
	public void addDefaultSprite(SpriteObject SOI) throws Exception{

		myDefaultSprites.addNewSprite(SOI);

//		defaultSprites.add(SOI);
	}
	
	public void addDefaultSprite(ArrayList<SpriteObject> SOI_LIST){
		SOI_LIST.forEach(sprite->{
			try {
				addDefaultSprite(sprite);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
//		defaultSprites.addAll(SOI_LIST);
	}

	public ArrayList<SpriteObject> getUserDefinedSprites() {
		return myCustomSprites.getAllSprites();
//		return new ArrayList<SpriteObject>(userSprites);

	}
	
	public void addUserSprite(SpriteObject SOI) throws Exception{

		myCustomSprites.addNewSprite(SOI);

	}
	
	public void addUserSprite(ArrayList<SpriteObject> SOI_LIST){
		SOI_LIST.forEach(sprite->{
			try {
				addUserSprite(sprite);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
//		defaultSprites.addAll(SOI_LIST);
	}

//	public void addUserSprite(SpriteObject SOI) {
//		userSprites.add(SOI);
//	}

	public SpriteObject getDefaultEmptySprite() {
		return defaultEmptySprite;
	}

	
	public SpriteObject getActiveCell() throws Exception{
		return SPSM.getActiveSprite();
	}

	public SpriteObjectGridManagerI getGridManager() {
		return SOGM;
	}	

}
