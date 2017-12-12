package authoring.GridManagers;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.TerrainLayer;
import javafx.scene.paint.Color;

public class TerrainObjectGridManager extends SpriteObjectGridManager{

	public TerrainObjectGridManager(int rows, int cols, SpriteGridHandler SGH){
		super(rows, cols, SGH);
		myLayerNum = 1;
	}
	
	public TerrainObjectGridManager(int rows, int cols) {
		super(rows, cols);
		myLayerNum = 1;
	}
	
	public TerrainObjectGridManager(int myNumRows, int myNumCols, int layerNum, Color myColor) {
		super(myNumRows, myNumCols, layerNum, myColor);
		myLayerNum = 1;
	}

	
	@Override
	public void createMapLayer() {
			if (hasStoredSprites()){
				loadedFromData = true;
				myMapLayer = new TerrainLayer(defaultRows, defaultColumns,mySpriteGridHandler,getStoredSpriteList());
			} else{
			myMapLayer = new TerrainLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
			}
			}
	
	@Override
	public int getLayerNum() {
		return myLayerNum;
		//return myMapLayer.getLayerNumber();
	}

}