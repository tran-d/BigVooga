package authoring;

import authoring_UI.SpriteGridHandler;
import authoring_UI.SpriteSelectPanel;
import authoring_UI.SpriteSet;
import engine.utilities.data.GameDataHandler;

public class SpriteSetImported extends SpriteSet {
	
	SpriteSetImported(GameDataHandler GDH){
		super(GDH);
	}

	
	@Override
	protected void setFolderToLoad() {
//		System.out.println(myGDH.getProjectPath());
		setFolderToLoad("");
	}

//	@Override
//	protected void makeSpritePanel(SpriteGridHandler SGH) {
//		mySSP = new SpriteSelectPanel("IMPORTEDSPRITES", SGH);
//		mySSP.setupDefaultSprites(getAllSprites());
//	}
	
	public void changeFolderPath(String newFolder){
		
	}

}
