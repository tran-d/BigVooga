package authoring_UI;

import authoring_UI.SpriteGridHandler;
import authoring_UI.SpriteSet;
import engine.utilities.data.GameDataHandler;

public class SpriteSetInventory extends SpriteSet {

	public SpriteSetInventory(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
		setFolderToLoad(myGDH.getInventorySpriteDirectoryPath());

	}

	@Override
	protected void makeSpritePanel(SpriteGridHandler SGH) {
		mySSP = new SpriteSelectPanel("INVENTORYSPRITES", SGH);
		mySSP.setupDefaultSprites(getAllSprites());
	}

}
