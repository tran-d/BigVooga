package authoring.GridManagers;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.SpriteLayer;

public class SpriteObjectGridManagerForSprites extends SpriteObjectGridManager{

	public SpriteObjectGridManagerForSprites(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	public SpriteObjectGridManagerForSprites(int rows, int columns) {
		super(rows, columns);
	}

	@Override
	public void createMapLayer() {
		myMapLayer = new SpriteLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}

//	@Override
//	public ImageView[][] getGrid() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	

}
