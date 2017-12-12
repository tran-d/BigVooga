package authoring.GridManagers;

import java.util.List;

import authoring.Layers.BackgroundLayer;
import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import authoring_UI.Map.SpriteLayer;
import javafx.scene.paint.Color;

public class BackgroundGridManager extends SpriteObjectGridManager{

	public BackgroundGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
		myLayerNum = 0;
	}
	
	public BackgroundGridManager(int rows, int columns) {
		super(rows, columns);
		myLayerNum = 0;
	}

	public BackgroundGridManager(int myNumRows, int myNumCols, int layerNum, Color myColor) {
		super(myNumRows, myNumCols, layerNum, myColor);
		myLayerNum = 0;
	}

	@Override
	public void createMapLayer() {
		myMapLayer = new BackgroundLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		//this.setNumCols(defaultColumns); why? 
		//this.setNumRows(defaultRows);
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}
	
	@Override
	public void setCanFillBackground(){
		canFillBackground = true;
	}

	@Override
	public void createMapLayer(List<AbstractSpriteObject> activeSpriteObjects) {
		if (activeSpriteObjects.size() == 0) createMapLayer();
		else myMapLayer = new BackgroundLayer(getNumRows(), getNumCols(), mySpriteGridHandler, activeSpriteObjects);
	}
	
	@Override
	public MapLayer getMapLayer() { // IS THIS OKAY IDK TODO
		return myMapLayer;
	}
}