package authoring_UI.HUD;

import authoring.SpriteObjectGridManager;
import authoring_UI.PanelLayer;
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
		this.setNumCols(temporaryColumns);
		this.setNumRows(temporaryRows);
		
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}

}
