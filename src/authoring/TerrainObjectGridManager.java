package authoring;

import authoring_UI.SpriteGridHandler;
import authoring_UI.TerrainLayer;

public class TerrainObjectGridManager extends SpriteObjectGridManager{

	public TerrainObjectGridManager(int rows, int cols, SpriteGridHandler SGH){
		super(rows, cols, SGH);
	}
	
	public TerrainObjectGridManager(int rows, int cols) {
		super(rows, cols);
	}
	
	@Override
	protected void createMapLayer() {
		myMapLayer = new TerrainLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}


}
