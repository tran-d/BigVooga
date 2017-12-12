package authoring_UI.HUD;

import java.util.List;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;

public class HUDGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 2;
	private static int COLUMNS = 20;
	
	
	public HUDGridManager(SpriteGridHandler SGH){
		super(ROWS, COLUMNS, SGH);
	}
	
	public HUDGridManager(){
		super(ROWS, COLUMNS);
	}

	protected HUDGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	@Override
	public void createMapLayer() {
		myMapLayer = new HUDLayer(getNumRows(), getNumCols(), mySpriteGridHandler);		
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}

	@Override
	public void createMapLayer(List<AbstractSpriteObject> activeSpriteObjects) {
		// TODO Auto-generated method stub
		
	}

}
