package authoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public AuthoringEnvironmentManager(GameDataHandler GDH) {
		myGDH = GDH;
		defaultEmptySprite = new DefaultSpriteObject();
		myGrid = new DraggableGrid();
		SPSM = new SpriteParameterSidebarManager(myGrid);
		initializeDefaultSprites();
		initializeCustomSprites();
		initializeInventorySprites();
	}

	public DraggableGrid getDraggableGrid() {
		return myGrid;
	}

	public void setOldDraggableGrid(DraggableGrid toSet) {
		String projectName = myGDH.getProjectName();
		myGrid = toSet;
		// do stuff to get the saved data
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

	public Map<String, List<AbstractSpriteObject>> getEveryTypeOfSprite() {
		Map<String, List<AbstractSpriteObject>> ret = new HashMap<String, List<AbstractSpriteObject>>();
		ret.put("DefaultSprites", this.getDefaultGameSprites());
		ret.put("CustomSprites", this.getUserDefinedSprites());
		ret.put("InventorySprites", this.getInventorySprites());
		return ret;
	}

	public Map<String, List<Pane>> getEveryTypeOfSpriteAsThumbnails() {
		Map<String, List<Pane>> ret = new HashMap<String, List<Pane>>();
		ret.put("DefaultSprites", this.getDefaultGameSpritesAsThumbnail());
		ret.put("CustomSprites", this.getUserDefinedSpritesAsThumbnail());
		ret.put("InventorySprites", this.getInventorySpritesAsThumbnail());
		return ret;
	}

	private List<Pane> getDefaultGameSpritesAsThumbnail() {
		return myDefaultSprites.getAllSpritesAsThumbnails();
	}

	private List<Pane> getUserDefinedSpritesAsThumbnail() {
		return myCustomSprites.getAllSpritesAsThumbnails();
	}

	private List<Pane> getInventorySpritesAsThumbnail() {
		return myInventorySprites.getAllSpritesAsThumbnails();
	}

	public List<AbstractSpriteObject> getInventorySprites() {
		return myInventorySprites.getAllSprites();
	}

	public void addInventorySprite(AbstractSpriteObject SOI) throws Exception {
		myInventorySprites.addNewSprite(SOI);
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
	}

	public List<AbstractSpriteObject> getDefaultGameSprites() {
		return myDefaultSprites.getAllSprites();
	}

	public void addDefaultSprite(SpriteObject SOI) throws Exception {
		myDefaultSprites.addNewSprite(SOI);
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
	}

	public List<AbstractSpriteObject> getUserDefinedSprites() {
		return myCustomSprites.getAllSprites();

	}

	public void addUserSprite(SpriteObject SOI) throws Exception {
		myCustomSprites.addNewSprite(SOI);
	}

	public void addUserSprite(String category, SpriteObject SOI) throws Exception {
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
	}

	public AbstractSpriteObject getDefaultEmptySprite() {
		return defaultEmptySprite;
	}

	public SpriteObject getActiveCell() throws Exception {
		return SPSM.getActiveSprite();
	}

	public boolean multipleActive() {
		return SPSM.multipleActive();
	}

	public SpriteObjectGridManagerI getGridManager() {
		return SOGM; // BTW THIS IS NEVER INITIALIZED.
	}
}