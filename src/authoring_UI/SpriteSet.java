package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

	SpriteSet(GameDataHandler GDH) {
		myGDH = GDH;
		categoryToSprites = new HashMap<String, ArrayList<SpriteObject>>();
		setFolderToLoad();
		loadSprites();
	}

	protected Map<String, ArrayList<SpriteObject>> getCategoryToSprites() {
		return categoryToSprites;
	}
	
	protected ArrayList<SpriteObject> getAllSprites(){
		System.out.println("Getting all");
		ArrayList<SpriteObject> ret = new ArrayList<SpriteObject>();
		getCategoryToSprites().values().forEach(list->{
			list.forEach(obj->{System.out.println(obj);ret.add(obj);});
		});
		return ret;
	}
	
	protected String getFolderToLoad(){
		System.out.println("folderToLoad: "+folderToLoad);
		return folderToLoad;
	}
	
	protected abstract void setFolderToLoad();
	protected void setFolderToLoad(String path){
		folderToLoad = path;
	}

	protected void loadSprites(){
		if (categoryToSprites == null){
			categoryToSprites = new HashMap<String, ArrayList<SpriteObject>>();
		}
		if (getFolderToLoad()==null){
			setFolderToLoad();
		}
		categoryToSprites = myGDH.loadSpritesFromMultipleDirectories(getFolderToLoad());
	}

	protected SpriteScrollView getSpriteScrollView(){
		if (mySSV==null){
			makeSpriteScrollView();
		}
		return mySSV;
	}
	
	protected void makeSpriteScrollView() {
		mySSV = new SpriteScrollView();
//		mySSV.setupSprites()
	}
	
	protected abstract void makeSpritePanel(SpriteGridHandler SGH);
	

	protected SpriteSelectPanel getSpritePanel(SpriteGridHandler SGH) {
		System.out.println("Getting sprite panel");
		if (mySSP==null){
			System.out.println("SSP is null");
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

	protected void addNewSprite(String category, SpriteObject SO) throws Exception{
		if (!categoryExists(category)) {
			try {
				addCategory(category);
			} catch (Exception e) {
				// Never gets thrown
			}
			ArrayList<SpriteObject> val = getCategoryToSprites().get(category);
			val.add(SO);
			getCategoryToSprites().put(category, val);
		}
		if (mySSP!=null){
			mySSP.addNewSprite(SO);
		}
		if (mySSV!=null){
			mySSV.addNewSprite(SO);
		}
		saveSprite(category, SO);
	}
	
	protected void saveSprite(String category, SpriteObject SO) throws Exception{
		String folderToSaveTo  = getFolderToLoad()+category+"/";
		myGDH.saveSpriteToDirectory(SO, folderToSaveTo);
}
	
}
