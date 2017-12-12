package authoring.GridManagers;

import java.util.List;

import authoring.Layers.BackgroundLayer;
import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;

public class BackgroundGridManager extends SpriteObjectGridManager{
	
//	public BackgroundGridManager(){
//		super(, COLUMNS);
//	}

	public BackgroundGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	public BackgroundGridManager(int rows, int columns) {
		super(rows, columns);
	}

	@Override
	public void createMapLayer() {
		myMapLayer = new BackgroundLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		System.out.println("tempCols: "+defaultColumns);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
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
		// TODO Auto-generated method stub
		
	}
	
}
