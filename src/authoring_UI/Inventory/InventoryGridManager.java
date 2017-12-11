package authoring_UI.Inventory;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring_UI.SpriteGridHandler;

public class InventoryGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 5;
	private static int COLUMNS = 5;
	
	
	public InventoryGridManager(SpriteGridHandler SGH){
		super(ROWS, COLUMNS, SGH);
	}
	
	public InventoryGridManager(){
		super(ROWS, COLUMNS);
	}

	protected InventoryGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	@Override
	public void createMapLayer() {
		myMapLayer = new InventoryLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
		
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}

}
