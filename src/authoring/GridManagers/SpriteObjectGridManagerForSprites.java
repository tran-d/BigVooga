package authoring.GridManagers;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.SpriteLayer;
import javafx.scene.paint.Color;

public class SpriteObjectGridManagerForSprites extends SpriteObjectGridManager{

	public SpriteObjectGridManagerForSprites(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	public SpriteObjectGridManagerForSprites(int rows, int columns) {
		super(rows, columns);
	}

	public SpriteObjectGridManagerForSprites(int myNumRows, int myNumCols, int layerNum, Color myColor) {
		super(myNumRows, myNumCols, layerNum, myColor);
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
