package authoring.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetImportedInventory extends SpriteSet {

	public SpriteSetImportedInventory(GameDataHandler GDH) {
		super(GDH);
//		;
	}

	@Override
	protected void setFolderToLoad() {
//		;
		setFolderToLoad("");
	}

//	@Override
//	protected void makeSpritePanel(SpriteGridHandler SGH) {
//		mySSP = new SpriteSelectPanel("IMPORTEDINVENTORYSPRITES", SGH);
//		mySSP.setupDefaultSprites(getAllSprites());
//	}
	
	public void changeFolderPath(String newFolder){
		
	}

}
