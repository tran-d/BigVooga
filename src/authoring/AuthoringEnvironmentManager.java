package authoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.GridManagers.SpriteObjectGridManagerI;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.DefaultSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.SpriteManagers.SpriteSet;
import authoring.SpriteManagers.SpriteSetDefault;
import authoring.SpriteManagers.SpriteSetImported;
import authoring.SpriteManagers.SpriteSetImportedInventory;
import authoring.SpriteManagers.SpriteSetInventory;
import authoring.SpriteManagers.SpriteSetInventoryTemplate;
import authoring.SpriteManagers.SpriteSetMenuTemplate;
import authoring.SpriteManagers.SpriteSetUserDefined;
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
	private SpriteSet myImportedSprites;
	private SpriteSet myImportedInventorySprites;
	private SpriteSet myInventoryTemplates;
	private SpriteSet myMenuTemplates;

	public AuthoringEnvironmentManager(GameDataHandler GDH, Stage stage) {
		myGDH = GDH;
		initializeDefaultSprites();
		initializeCustomSprites();
		initializeInventorySprites();
		initializeImportedSprites();
		initializeInventoryTemplates();
		initializeMenuTemplates();
		initializeImportedInventorySprites();
		
		defaultEmptySprite = new DefaultSpriteObject();
		
		System.out.println("init MAPMAN in AEM");
		
		if (myDefaultSprites == null) System.out.println("this was def initialized");
	}
	
	private void initializeInventoryTemplates() {
		myInventoryTemplates = new SpriteSetInventoryTemplate(myGDH);
	}
	
	private void initializeMenuTemplates() {
		myMenuTemplates = new SpriteSetMenuTemplate(myGDH);
	}

	private void initializeDefaultSprites() {
		myDefaultSprites = new SpriteSetDefault(myGDH);
	}
	
	private void initializeImportedSprites() {
		
		myImportedSprites = new SpriteSetImported(myGDH);
		System.out.println("Made imported sprites");
	}
	
	private void initializeImportedInventorySprites() {
		myImportedInventorySprites = new SpriteSetImportedInventory(myGDH);
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
	
	public SpriteSet getImportedSpriteController() {
		return myImportedSprites;
	}
	
	public SpriteSet getImportedInventorySpriteController() {
		return myImportedInventorySprites;
	}


	public SpriteSet getInventoryController() {
		return myInventorySprites;
	}
	
	public SpriteSet getInventoryTemplateController(){
		return myInventoryTemplates;
	}
	
	public SpriteSet getMenuTemplateController(){
		return myMenuTemplates;
	}

	public Map<String, List<AbstractSpriteObject>> getEveryTypeOfSprite() {
		Map<String, List<AbstractSpriteObject>> ret = new HashMap<String, List<AbstractSpriteObject>>();
		ret.put("DefaultSprites", this.getDefaultGameSprites());
		ret.put("CustomSprites", this.getUserDefinedSprites());
		ret.put("InventorySprites", this.getInventorySprites());
		ret.put("ImportedSprites", this.getImportedSprites());
		ret.put("ImportedInventorySprites", this.getImportedInventorySprites());
//		ret.put("InventoryTemplates", this.getInventoryTemplates());
//		ret.put("MenuTemplates", this.getMenuTemplates());
		return ret;
	}

	public Map<String, List<Pane>> getEveryTypeOfSpriteAsThumbnails() {
		Map<String, List<Pane>> ret = new HashMap<String, List<Pane>>();
		if (myDefaultSprites == null) System.out.println("THIS IS SO WEIRD");
		ret.put("DefaultSprites", this.getDefaultGameSpritesAsThumbnail());
		ret.put("CustomSprites", this.getUserDefinedSpritesAsThumbnail());
		ret.put("InventorySprites", this.getInventorySpritesAsThumbnail());
		ret.put("ImportedSprites", this.getImportedSpritesAsThumbnail());
		ret.put("ImportedInventorySprites", this.getImportedInventorySpritesAsThumbnail());
		return ret;
	}

	private List<Pane> getDefaultGameSpritesAsThumbnail() {
		if (myDefaultSprites == null) System.out.println("wtf" );
		return myDefaultSprites.getAllSpritesAsThumbnails();
	}

	private List<Pane> getUserDefinedSpritesAsThumbnail() {
		return myCustomSprites.getAllSpritesAsThumbnails();
	}
	
	private List<Pane> getImportedSpritesAsThumbnail() {
		return myImportedSprites.getAllSpritesAsThumbnails();
	}
	
	private List<Pane> getImportedInventorySpritesAsThumbnail() {
		return myImportedInventorySprites.getAllSpritesAsThumbnails();
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
	
	public List<AbstractSpriteObject> getImportedSprites() {
		return myImportedSprites.getAllSprites();

	}
	
	public List<AbstractSpriteObject> getImportedInventorySprites() {
		return myImportedInventorySprites.getAllSprites();

	}

	public AbstractSpriteObject getDefaultEmptySprite() {
		return defaultEmptySprite;
	}


	public GameDataHandler getGameDataHandler() {
		return myGDH;
	}
}

