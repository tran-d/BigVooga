package authoring;

import authoring_UI.SpriteGridHandler;
import authoring_UI.SpriteSelectPanel;
import authoring_UI.SpriteSet;
import engine.utilities.data.GameDataHandler;

public class SpriteSetInventoryTemplate extends SpriteSet {

	protected SpriteSetInventoryTemplate(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
//		System.out.println(myGDH.getProjectPath());
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
