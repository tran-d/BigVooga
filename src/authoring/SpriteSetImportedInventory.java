package authoring;

import authoring_UI.SpriteGridHandler;
import authoring_UI.SpriteSelectPanel;
import authoring_UI.SpriteSet;
import engine.utilities.data.GameDataHandler;

public class SpriteSetImportedInventory extends SpriteSet {

	public SpriteSetImportedInventory(GameDataHandler GDH) {
		super(GDH);
//		System.out.println(myGDH.getProjectPath());
	}

	@Override
	protected void setFolderToLoad() {
//		System.out.println(myGDH.getProjectPath());
		setFolderToLoad("");
	}

	@Override
	protected void makeSpritePanel(SpriteGridHandler SGH) {
		mySSP = new SpriteSelectPanel("IMPORTEDINVENTORYSPRITES", SGH);
		mySSP.setupDefaultSprites(getAllSprites());
	}
	
	public void changeFolderPath(String newFolder){
		
	}

}
