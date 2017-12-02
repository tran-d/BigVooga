package authoring_UI;

import authoring.SpriteObjectGridManager;

public class SpriteObjectGridManagerForSprites extends SpriteObjectGridManager{

	SpriteObjectGridManagerForSprites(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}

	@Override
	protected void createMapLayer() {
		myMapLayer = new SpriteLayer(CURR_ROWS, CURR_COLS, mySpriteGridHandler);
	}
	

}
