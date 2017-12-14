package authoring.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetMenuTemplate extends SpriteSet {

	public SpriteSetMenuTemplate(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
//		;
		setFolderToLoad("");
	}

//	@Override
//	protected void makeSpritePanel(SpriteGridHandler SGH) {
//		mySSP = new SpriteSelectPanel("InventoryTemplates", SGH);
//		mySSP.setupDefaultSprites(getAllSprites());
//	}
	
	public void changeFolderPath(String newFolder){
		
	}
}
