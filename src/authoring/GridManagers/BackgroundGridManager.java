package authoring.GridManagers;

import java.util.List;

import authoring.Layers.BackgroundLayer;
import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import javafx.scene.paint.Color;

public class BackgroundGridManager extends SpriteObjectGridManager{
	
	public BackgroundGridManager(){
		super();
	}

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
	public void setCanFillBackground(){
		canFillBackground = true;
	}

	
	@Override
	public MapLayer getMapLayer() { // IS THIS OKAY IDK TODO
		return myMapLayer;
	}
}
