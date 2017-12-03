package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import authoring.SpriteObject;
import authoring.SpriteObjectI;
import authoring.SpriteParameterI;
import engine.utilities.data.GameDataHandler;

public abstract class SpriteSet {

	protected Map<String, ArrayList<SpriteObject>> categoryToSprites;
	protected SpriteSelectPanel mySSP;
	protected SpriteScrollView mySSV;
	protected GameDataHandler myGDH;
	protected String folderToLoad;
	protected boolean loaded = false;
	protected ArrayList<SpriteObject> toSave;

	SpriteSet(GameDataHandler GDH) {
		myGDH = GDH;
		categoryToSprites = new HashMap<String, ArrayList<SpriteObject>>();
		setFolderToLoad();
		loadSprites();
		toSave = new ArrayList<SpriteObject>();
	}

	protected Map<String, ArrayList<SpriteObject>> getCategoryToSprites() {
		return categoryToSprites;
	}

	public ArrayList<SpriteObject> getAllSprites() {
		if (!loaded) {
			this.loadSprites();
		}
//		System.out.println("Getting all");
		ArrayList<SpriteObject> ret = new ArrayList<SpriteObject>();
		getCategoryToSprites().values().forEach(list -> {
			list.forEach(obj -> {
//				System.out.println(obj);
				ret.add(obj.newCopy());
			});
		});
		return ret;
	}

	protected Map<String, ArrayList<SpriteObject>> getAllSpritesAsMap() {
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
			categoryToSprites = new HashMap<String, ArrayList<SpriteObject>>();
		}
		// if (getFolderToLoad()==null){
		// setFolderToLoad();
		// }
		loaded = true;
		categoryToSprites = myGDH.loadSpritesFromNestedDirectories(getFolderToLoad());
		System.out.println("!!!!!!!!!!!!!!");
		System.out.println(categoryToSprites);
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
		getCategoryToSprites().put(newCategory, new ArrayList<SpriteObject>());
	}

	protected boolean categoryExists(String category) {
		return getAllCategoriesSet().contains(category);
	}

	public void addNewSprite(SpriteObject SO) throws Exception {
		addNewSprite("General", SO);
	}

	protected void addNewSprite(String category, SpriteObject SO) throws Exception {
		if (!categoryExists(category)) {
				addCategory(category);
		}
			ArrayList<SpriteObject> val = getCategoryToSprites().get(category);
			val.add(SO);
			getCategoryToSprites().put(category, val);
//		}
		if (mySSP != null) {
			mySSP.addNewDefaultSprite(SO);
		}
		if (mySSV != null) {
			mySSV.addNewSprite(SO);
		}
		saveSprite(category, SO);
	}

	protected void saveSprite(String category, SpriteObject SO) throws Exception {
		String folderToSaveTo = getFolderToLoad() + category + "/"+SO.getName();
		myGDH.saveSprite(SO, folderToSaveTo);
	}

	protected void saveAllSprites() throws Exception {
		for (Entry<String, ArrayList<SpriteObject>> keyVal : getAllSpritesAsMap().entrySet()) {
			ArrayList<SpriteObject> SO_LIST = keyVal.getValue();
			for (SpriteObject SO : SO_LIST) {
				saveSprite(keyVal.getKey(), SO);
			}
		}
	}

}
