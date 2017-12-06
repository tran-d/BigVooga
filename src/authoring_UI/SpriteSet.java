package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import authoring.AbstractSpriteObject;
import authoring.SpriteObject;
import authoring.SpriteObjectI;
import authoring.SpriteParameterI;
import authoring.SpriteThumbnail;
import engine.utilities.data.GameDataHandler;
import javafx.scene.layout.Pane;

public abstract class SpriteSet {

	protected Map<String, List<AbstractSpriteObject>> categoryToSprites;
	protected SpriteSelectPanel mySSP;
	protected SpriteScrollView mySSV;
	protected GameDataHandler myGDH;
	protected String folderToLoad;
	protected boolean loaded = false;
	protected ArrayList<SpriteObject> toSave;

	protected SpriteSet(GameDataHandler GDH) {
		myGDH = GDH;
		categoryToSprites = new HashMap<String, List<AbstractSpriteObject>>();
		setFolderToLoad();
		loadSprites();
		toSave = new ArrayList<SpriteObject>();
	}

	protected Map<String, List<AbstractSpriteObject>> getCategoryToSprites() {
		return categoryToSprites;
	}
	
	public ArrayList<Pane> getAllSpritesAsThumbnails(){
		ArrayList<AbstractSpriteObject> ASOs = getAllSprites();
		ArrayList<Pane> ret = new ArrayList<Pane>();
		ASOs.forEach(sprite->{
			ret.add(new SpriteThumbnail(sprite));
		});
		return ret;
	}

	public ArrayList<AbstractSpriteObject> getAllSprites() {
		if (!loaded) {
			this.loadSprites();
		}
//		System.out.println("Getting all");
		ArrayList<AbstractSpriteObject> ret = new ArrayList<AbstractSpriteObject>();
		getCategoryToSprites().values().forEach(list -> {
			list.forEach(obj -> {
//				System.out.println(obj);
				ret.add(obj.newCopy());
			});
		});
		return ret;
	}

	protected Map<String, List<AbstractSpriteObject>> getAllSpritesAsMap() {
//		System.out.println("Getting them");
		if (!loaded) {
			this.loadSprites();
		}
		return this.getCategoryToSprites();
	}

	protected String getFolderToLoad() {
//		System.out.println("folderToLoad: " + folderToLoad);
		return folderToLoad;
	}

	protected void changeFolderPath() {
		// NOTHING MUST BE OVERRIDEN IF WANT FUNCTIONALITY
	}

	protected abstract void setFolderToLoad();

	protected void setFolderToLoad(String path) {
		folderToLoad = path;
	}

	protected void loadSprites() {
		if (categoryToSprites == null) {
			categoryToSprites = new HashMap<String, List<AbstractSpriteObject>>();
		}
		// if (getFolderToLoad()==null){
		// setFolderToLoad();
		// }
		loaded = true;
		categoryToSprites = myGDH.loadSpritesFromNestedDirectories(getFolderToLoad());
	}

	protected SpriteScrollView getSpriteScrollView() {
		if (mySSV == null) {
			makeSpriteScrollView();
		}
		return mySSV;
	}
	
	/*protected SpriteSamarthGrid getSamarthGrid() {
		mySG == null {
			make
		}
	}*/

	protected void makeSpriteScrollView() {
		mySSV = new SpriteScrollView();
		// mySSV.setupSprites()
	}

	protected abstract void makeSpritePanel(SpriteGridHandler SGH);

	protected SpriteSelectPanel getSpritePanel(SpriteGridHandler SGH) {
//		System.out.println("Getting sprite panel");
		if (mySSP == null) {
//			System.out.println("SSP is null");
			makeSpritePanel(SGH);
		}
		return mySSP;
	}

	protected Set<String> getAllCategoriesSet() {
		return getCategoryToSprites().keySet();
	}

	protected ArrayList<String> getAllCategoriesList() {
		return new ArrayList<String>(getAllCategoriesSet());
	}

	protected void addCategory(String newCategory) throws Exception {
		if (categoryExists(newCategory)) {
			throw new Exception("Category already exists.");
		}
		getCategoryToSprites().put(newCategory, new ArrayList<AbstractSpriteObject>());
	}

	protected boolean categoryExists(String category) {
		return getAllCategoriesSet().contains(category);
	}

	public void addNewSprite(AbstractSpriteObject SO) throws Exception {
		addNewSprite("General", SO);
	}

	protected void addNewSprite(String category, AbstractSpriteObject SO) throws Exception {
		if (!categoryExists(category)) {
				addCategory(category);
		}
			List<AbstractSpriteObject> val = getCategoryToSprites().get(category);
			val.add(SO);
			getCategoryToSprites().put(category, val);
//		}
		if (mySSP != null) {
			mySSP.addNewDefaultSprite(SO);
		}
		if (mySSV != null) {
			mySSV.addToVBox(new SpriteThumbnail(SO));
		}
		saveSprite(category, SO);
	}

	protected void saveSprite(String category, AbstractSpriteObject SO) throws Exception {
		String folderToSaveTo = getFolderToLoad() + category + "/"+SO.getName();
		myGDH.saveSprite(SO, folderToSaveTo);
	}

	protected void saveAllSprites() throws Exception {
		for (Entry<String, List<AbstractSpriteObject>> keyVal : getAllSpritesAsMap().entrySet()) {
			List<AbstractSpriteObject> SO_LIST = keyVal.getValue();
			for (AbstractSpriteObject SO : SO_LIST) {
				saveSprite(keyVal.getKey(), SO);
			}
		}
	}

}
