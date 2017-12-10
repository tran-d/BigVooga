package authoring.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetDefault extends SpriteSet{
	
	public SpriteSetDefault(GameDataHandler GDH) {
		super(GDH);
//		System.out.println(myGDH.getProjectPath());
	}

	@Override
	protected void setFolderToLoad() {
//		System.out.println(myGDH.getProjectPath());
		setFolderToLoad(myGDH.getDefaultSpriteDirectoryPath());
	}

//	@Override
//	protected void makeSpritePanel(SpriteGridHandler SGH) {
//		mySSP = new SpriteSelectPanel("DEFAULTSPRITES", SGH);
//		mySSP.setupDefaultSprites(getAllSprites());
//	}
	
	public void changeFolderPath(String newFolder){
		
	}
	
	
}
