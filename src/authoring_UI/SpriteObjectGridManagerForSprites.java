package authoring_UI;

import authoring.SpriteObjectGridManager;

public class SpriteObjectGridManagerForSprites extends SpriteObjectGridManager{

	SpriteObjectGridManagerForSprites(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	SpriteObjectGridManagerForSprites(int rows, int columns) {
		super(rows, columns);
	}

	@Override
	protected void createMapLayer() {
		myMapLayer = new SpriteLayer(CURR_ROWS, CURR_COLS, mySpriteGridHandler);
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}
	

}
