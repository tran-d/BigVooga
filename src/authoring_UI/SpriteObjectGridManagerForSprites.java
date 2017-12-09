package authoring_UI;

import authoring.SpriteObjectGridManager;
import javafx.scene.image.ImageView;

public class SpriteObjectGridManagerForSprites extends SpriteObjectGridManager{

	SpriteObjectGridManagerForSprites(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	SpriteObjectGridManagerForSprites(int rows, int columns) {
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
