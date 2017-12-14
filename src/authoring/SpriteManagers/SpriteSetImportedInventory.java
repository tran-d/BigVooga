package authoring.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetImportedInventory extends SpriteSet {

	public SpriteSetImportedInventory(GameDataHandler GDH) {
		super(GDH);
//		System.out.println(myGDH.getProjectPath());
	}

	@Override
	protected void setFolderToLoad() {
		String path = myGDH.getImportedInventorySpritesPath();
		setFolderToLoad(path);
	}
	
	public void changeFolderPath(String newFolder){
		this.categoryToSprites.clear();
		this.setFolderToLoad();
		this.loadSprites();
	}

}
