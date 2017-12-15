package authoring_UI.Inventory;

import java.util.List;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import engine.utilities.data.GameDataHandler;

public class InventoryGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 5;
	private static int COLUMNS = 5;
	
	
	public InventoryGridManager(SpriteGridHandler SGH, GameDataHandler GDH){
		super(ROWS, COLUMNS, SGH, GDH);
	}
	
	public InventoryGridManager(){
		super(ROWS, COLUMNS);
	}

	protected InventoryGridManager(int rows, int columns, SpriteGridHandler SGH, GameDataHandler GDH) {
		super(rows, columns, SGH, GDH);
	}
	
	@Override
	public void createMapLayer() {
		
		myMapLayer = new InventoryLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
		
	}
	
}
