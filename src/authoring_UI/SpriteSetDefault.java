package authoring_UI;

import engine.utilities.data.GameDataHandler;

public class SpriteSetDefault extends SpriteSet{
	
	SpriteSetDefault(GameDataHandler GDH) {
		super(GDH);
		System.out.println(myGDH.getProjectPath());
	}

	@Override
	protected void setFolderToLoad() {
		System.out.println(myGDH.getProjectPath());
		setFolderToLoad(myGDH.getDefaultSpriteDirectoryPath());
	}

	@Override
	protected void makeSpritePanel(SpriteGridHandler SGH) {
		mySSP = new SpriteSelectPanel("DEFAULTSPRITES", SGH);
		mySSP.setupSprites(getAllSprites());
	}
	
	public void changeDefaultSpriteFolder(String newFolder){
		
	}
	
	
}
