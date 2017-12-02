package authoring_UI;

import authoring.SpriteObjectI;
import engine.utilities.data.GameDataHandler;

public class SpriteSetUserDefined extends SpriteSet{

	public SpriteSetUserDefined(GameDataHandler GDH) {
		super(GDH);
	}

	@Override
	protected void setFolderToLoad() {
		setFolderToLoad(myGDH.getCustomSpriteDirectoryPath());
	}

	@Override
	protected void makeSpritePanel(SpriteGridHandler SGH) {
		mySSP = new SpriteSelectPanel("USERSPRITES", SGH);
		mySSP.setupDefaultSprites(getAllSprites());
	}


}
