package authoring.ObjectManagers.SuperlayerManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetInventoryTemplate extends SuperlayerManager {

	public SpriteSetInventoryTemplate(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
		setFolderToLoad(myGDH.getInventoryTemplateSpriteDirectoryPath());
	}
	
	public void changeFolderPath(String newFolder){
		
	}
}
