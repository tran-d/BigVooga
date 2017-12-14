package authoring.SpriteManagers;

import engine.utilities.data.GameDataHandler;

public class SpriteSetMenuTemplate extends SpriteSet {

	public SpriteSetMenuTemplate(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
//		System.out.println(myGDH.getProjectPath());
		setFolderToLoad("");
	}
	
	public void changeFolderPath(String newFolder){
		
	}
}
