package authoring.SpriteManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.Thumbnail;
import authoring.DialogSprite.DialogSequence;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.SpriteThumbnail;
import engine.utilities.data.GameDataHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DialogSpriteManager {
	
	protected Map<String, List<DialogSequence>> categoryToDialogSequences;
	protected GameDataHandler myGDH;
	protected String folderToLoad;
	protected boolean loaded = false;
	protected List<SpriteObject> toSave;

	public DialogSpriteManager(GameDataHandler GDH) {
		myGDH = GDH;
		setFolderToLoad();
		loadSprites();
		toSave = new ArrayList<SpriteObject>();
	}

	protected void setFolderToLoad() {
		setFolderToLoad("");
	}
	
	public List<Pane> getAllSpritesAsThumbnails() {
		List<DialogSequence> ASOs = getAllDialogSequences();
		List<Pane> ret = new ArrayList<Pane>();
		ASOs.forEach(sprite -> {
			Image im = new Image(sprite.getDialogSprites().get(0).getImageFileURL());
			ret.add(new Thumbnail(new ImageView(im), sprite.getName()));
		});
		return ret;
	}
	
	public List<DialogSequence> getAllDialogSequences() {
		if (!loaded) {
			this.loadSprites();
		}
		List<DialogSequence> ret = new ArrayList<DialogSequence>();
		categoryToDialogSequences.values().forEach(list -> {
			list.forEach(obj -> {
				// System.out.println(obj);
				ret.add(obj);
			});
		});
		return  ret;
	
	}
	
	protected String getFolderToLoad() {
		// System.out.println("folderToLoad: " + folderToLoad);
		return folderToLoad;
	}
	
	protected void setFolderToLoad(String path) {
		folderToLoad = path;
	}
	
	protected void loadSprites() {
		if (categoryToDialogSequences == null) {
			categoryToDialogSequences = new HashMap<String, List<DialogSequence>>();
		}
		
		
		loaded = true;
		if (!getFolderToLoad().equals("")) {
			categoryToDialogSequences = myGDH.loadDialogsFromNestedDirectories(getFolderToLoad());
			System.out.println(categoryToDialogSequences);
		}
	}
	public void addNewDialogSequence(DialogSequence DS) throws Exception {
		addNewDialogSequence("General", DS);
	}
	
	public void setBooleanLoaded(Boolean b) {
		loaded = b;
	}
	
	private boolean categoryExists(String category){
		return categoryToDialogSequences.containsKey(category);
	}
	
	private void addCategory(String newCategory){
		categoryToDialogSequences.put(newCategory, new ArrayList<DialogSequence>());
	}
	
	public void addNewDialogSequence(String category, DialogSequence DS) throws Exception {
		if (!categoryExists(category)) {
			addCategory(category);
		}
		List<DialogSequence> val = categoryToDialogSequences.get(category);
		val.add(DS);
		categoryToDialogSequences.put(category, val);
		// }
//		if (mySSP != null) {
//			mySSP.addNewDefaultSprite(SO);
//		}
//		if (mySSV != null) {
//			mySSV.addToVBox(new SpriteThumbnail(SO));
//		}
		saveDialogSequence(category, DS);
	}
	
	protected void saveDialogSequence(String category, DialogSequence DS) throws Exception {
		String folderToSaveTo = getFolderToLoad() + category + "/" + DS.getName();
		myGDH.saveDialogSequence(DS, folderToSaveTo);
	}

}
