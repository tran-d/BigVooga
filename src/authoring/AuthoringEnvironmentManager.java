package authoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import authoring_UI.DefaultSpriteObject;
import authoring_UI.SpriteSet;
import authoring_UI.SpriteSetDefault;
import authoring_UI.SpriteSetInventory;
import authoring_UI.SpriteSetUserDefined;
import engine.utilities.data.GameDataHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AuthoringEnvironmentManager {

	private AbstractSpriteObject defaultEmptySprite;
	private SpriteObjectGridManagerI SOGM;
	private List<SpriteObject> userSprites;
	private GameDataHandler myGDH;
	private SpriteSet myDefaultSprites;
	private SpriteSet myCustomSprites;
	private SpriteSet myInventorySprites;

	public AuthoringEnvironmentManager(GameDataHandler GDH, Stage stage) {
		myGDH = GDH;
		initializeDefaultSprites();
		initializeCustomSprites();
		initializeInventorySprites();

		defaultEmptySprite = new DefaultSpriteObject();
		
		System.out.println("init MAPMAN in AEM");
		
		if (myDefaultSprites == null) System.out.println("this was def initialized");
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
		if (myDefaultSprites == null) System.out.println("THIS IS SO WEIRD");
		ret.put("DefaultSprites", this.getDefaultGameSpritesAsThumbnail());
		ret.put("CustomSprites", this.getUserDefinedSpritesAsThumbnail());
		ret.put("InventorySprites", this.getInventorySpritesAsThumbnail());
		return ret;
	}

	private List<Pane> getDefaultGameSpritesAsThumbnail() {
		if (myDefaultSprites == null) System.out.println("wtf" );
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

		myCustomSprites.addNewSprite(category, SOI);

	}

	public void addUserSprite(List<SpriteObject> SOI_LIST) {
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


	public GameDataHandler getGameDataHandler() {
		return myGDH;
	}
}

