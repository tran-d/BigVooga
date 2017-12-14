package authoring.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetImported extends SpriteSet {
	
	public SpriteSetImported(GameDataHandler GDH){
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
		this.categoryToSprites.clear();
		this.setFolderToLoad(newFolder);
		this.loadSprites();
	}
}