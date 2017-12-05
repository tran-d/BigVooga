package authoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import authoring_UI.DefaultSpriteObject;
import authoring_UI.DraggableGrid;
import authoring_UI.SpriteSet;
import authoring_UI.SpriteSetDefault;
import authoring_UI.SpriteSetInventory;
import authoring_UI.SpriteSetUserDefined;
import engine.utilities.data.GameDataHandler;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AuthoringEnvironmentManager {

	private AbstractSpriteObject defaultEmptySprite;
	private SpriteParameterSidebarManager SPSM;
	private SpriteObjectGridManagerI SOGM;
	private ArrayList<SpriteObject> defaultSprites;
	private ArrayList<SpriteObject> userSprites;
	private GameDataHandler myGDH;
	private SpriteSet myDefaultSprites;
	private SpriteSet myCustomSprites;

	private SpriteSet myInventorySprites;
	private DraggableGrid myGrid;

	public AuthoringEnvironmentManager(String projectName) {
		myGDH = new GameDataHandler(projectName);
		defaultEmptySprite = new DefaultSpriteObject();
		myGrid = new DraggableGrid();
		// SOGM = new SpriteObjectGridManager();
		SPSM = new SpriteParameterSidebarManager(myGrid);
		initializeDefaultSprites();
		initializeCustomSprites();
		initializeInventorySprites();
		// defaultSprites = new ArrayList<SpriteObject>();
		// userSprites = new ArrayList<SpriteObject>();
	}

	public DraggableGrid getDraggableGrid() {
		return myGrid;
	}

	public AuthoringEnvironmentManager() {
		this("TestProject");
	}

	public GameDataHandler getGameDataHandler() {
		return myGDH;
	}

	public SpriteParameterSidebarManager getSpriteParameterSidebarManager() {
		return SPSM;
	}

	private void initializeDefaultSprites() {
		myDefaultSprites = new SpriteSetDefault(myGDH);
	}

	private void initializeCustomSprites() {
		myCustomSprites = new SpriteSetUserDefined(myGDH);
	}

	private void initializeInventorySprites() {
		myInventorySprites = new SpriteSetInventory(myGDH);
	}

	public SpriteSet getDefaultSpriteController() {
		return myDefaultSprites;
	}

	public SpriteSet getCustomSpriteController() {
		return myCustomSprites;
	}

	public SpriteSet getInventoryController() {
		return myInventorySprites;
	}

	public Map<String, ArrayList<AbstractSpriteObject>> getEveryTypeOfSprite(){
		Map<String, ArrayList<AbstractSpriteObject>> ret = new HashMap<String, ArrayList<AbstractSpriteObject>>();
		ret.put("DefaultSprites", this.getDefaultGameSprites());
		ret.put("CustomSprites", this.getUserDefinedSprites());
		ret.put("InventorySprites", this.getInventorySprites());
		return ret;
	}
	
	public Map<String, ArrayList<Pane>> getEveryTypeOfSpriteAsThumbnails(){
		Map<String, ArrayList<Pane>> ret = new HashMap<String, ArrayList<Pane>>();
		ret.put("DefaultSprites", this.getDefaultGameSpritesAsThumbnail());
		ret.put("CustomSprites", this.getUserDefinedSpritesAsThumbnail());
		ret.put("InventorySprites", this.getInventorySpritesAsThumbnail());
		return ret;
	}

	private ArrayList<Pane> getDefaultGameSpritesAsThumbnail() {
		return myDefaultSprites.getAllSpritesAsThumbnails();
	}
	
	private ArrayList<Pane> getUserDefinedSpritesAsThumbnail() {
		return myCustomSprites.getAllSpritesAsThumbnails();
	}
	
	private ArrayList<Pane> getInventorySpritesAsThumbnail() {
		return myInventorySprites.getAllSpritesAsThumbnails();
	}

	public ArrayList<AbstractSpriteObject> getInventorySprites() {
		// return new ArrayList<SpriteObject>(defaultSprites);
		return myInventorySprites.getAllSprites();
	}

	public void addInventorySprite(AbstractSpriteObject SOI) throws Exception {

		myInventorySprites.addNewSprite(SOI);

		// defaultSprites.add(SOI);
	}

	public void addInventorySprite(ArrayList<AbstractSpriteObject> SOI_LIST) {
		SOI_LIST.forEach(sprite -> {
			try {
				addInventorySprite(sprite);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// defaultSprites.addAll(SOI_LIST);
	}

	public ArrayList<AbstractSpriteObject> getDefaultGameSprites() {
		// return new ArrayList<SpriteObject>(defaultSprites);
		return myDefaultSprites.getAllSprites();
	}

	public void addDefaultSprite(SpriteObject SOI) throws Exception {

		myDefaultSprites.addNewSprite(SOI);

		// defaultSprites.add(SOI);
	}

	public void addDefaultSprite(ArrayList<SpriteObject> SOI_LIST) {
		SOI_LIST.forEach(sprite -> {
			try {
				addDefaultSprite(sprite);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// defaultSprites.addAll(SOI_LIST);
	}

	public ArrayList<AbstractSpriteObject> getUserDefinedSprites() {
		return myCustomSprites.getAllSprites();
		// return new ArrayList<SpriteObject>(userSprites);

	}

	public void addUserSprite(SpriteObject SOI) throws Exception {

		myCustomSprites.addNewSprite(SOI);

	}

	public void addUserSprite(ArrayList<SpriteObject> SOI_LIST) {
		SOI_LIST.forEach(sprite -> {
			try {
				addUserSprite(sprite);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// defaultSprites.addAll(SOI_LIST);
	}

	// public void addUserSprite(SpriteObject SOI) {
	// userSprites.add(SOI);
	// }

	public AbstractSpriteObject getDefaultEmptySprite() {
		return defaultEmptySprite;
	}

	public SpriteObject getActiveCell() throws Exception {
		return SPSM.getActiveSprite();
	}

	public SpriteObjectGridManagerI getGridManager() {
		return SOGM;
	}

}
