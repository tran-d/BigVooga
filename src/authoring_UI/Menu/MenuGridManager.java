package authoring_UI.Menu;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring_UI.SpriteGridHandler;

public class MenuGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 10;
	private static int COLUMNS = 6;
	
	
	public MenuGridManager(SpriteGridHandler SGH){
		super(ROWS, COLUMNS, SGH);
	}
	
	public MenuGridManager(){
		super(ROWS, COLUMNS);
	}

	protected MenuGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	@Override
	public void createMapLayer() {
		myMapLayer = new MenuLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(temporaryColumns);
		this.setNumRows(temporaryRows);
		
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}

}
