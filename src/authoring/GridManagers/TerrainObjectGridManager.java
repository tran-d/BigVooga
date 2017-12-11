package authoring.GridManagers;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.TerrainLayer;
import javafx.scene.paint.Color;

public class TerrainObjectGridManager extends SpriteObjectGridManager{

	public TerrainObjectGridManager(int rows, int cols, SpriteGridHandler SGH){
		super(rows, cols, SGH);
		myLayerNum = 0;
	}
	
	public TerrainObjectGridManager(int rows, int cols) {
		super(rows, cols);
	}
	
	public TerrainObjectGridManager(int myNumRows, int myNumCols, int layerNum, Color myColor) {
		super(myNumRows, myNumCols, layerNum, myColor);
	}

	@Override
	public void createMapLayer() {
		myMapLayer = new TerrainLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}
}