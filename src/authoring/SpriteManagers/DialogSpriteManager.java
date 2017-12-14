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

	protected List<DialogSequence> dialogSequences;
	protected GameDataHandler myGDH;
	protected String folderToLoad;
	protected boolean loaded = false;
	protected List<SpriteObject> toSave;

	private final String FOLDER = "dialogue/";

	public DialogSpriteManager(GameDataHandler GDH) {
		myGDH = GDH;
		folderToLoad = FOLDER;
		loadSprites();
		toSave = new ArrayList<SpriteObject>();
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
		ret.addAll(dialogSequences);
		return ret;

	}

	protected String getFolderToLoad() {
		// ;
		return folderToLoad;
	}

	protected void setFolderToLoad(String path) {
		folderToLoad = path;
	}

	protected void loadSprites() {
		if (dialogSequences == null) {
			dialogSequences = new ArrayList<DialogSequence>();
		}

		loaded = true;
		if (!getFolderToLoad().equals("")) {
			dialogSequences = myGDH.loadDialogsFromDirectory(getFolderToLoad());
			;
		}
	}

	public void setBooleanLoaded(Boolean b) {
		loaded = b;
	}

	public void addNewDialogSequence(DialogSequence DS) throws Exception {

		dialogSequences.add(DS);
		// }
		// if (mySSP != null) {
		// mySSP.addNewDefaultSprite(SO);
		// }
		// if (mySSV != null) {
		// mySSV.addToVBox(new SpriteThumbnail(SO));
		// }
		saveDialogSequence(DS);
	}

	protected void saveDialogSequence(DialogSequence DS) throws Exception {
		String folderToSaveTo = getFolderToLoad() + "/" + DS.getName();
		myGDH.saveDialogSequence(DS, folderToSaveTo);
	}

}
