package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

public class DisplayInventory implements Action {

	private GameObjectOperation inventoryDisplay;
	private DoubleOperation startIndex;
	private DoubleOperation rows;
	private DoubleOperation cols;
	
	public DisplayInventory(GameObjectOperation inventoryDisplay, DoubleOperation startIndex, DoubleOperation rows, DoubleOperation cols) {
		this.inventoryDisplay = inventoryDisplay;
		this.startIndex = startIndex;
		this.rows = rows;
		this.cols = cols;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		//Create display = new Create(inventoryDisplay);
		
	}

}
