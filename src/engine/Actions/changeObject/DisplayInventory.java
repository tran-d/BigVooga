package engine.Actions.changeObject;

import java.util.List;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.Actions.movement.MoveTo;
import engine.Actions.variableSetting.GiveInventory;
import engine.operations.doubleops.Difference;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.doubleops.Product;
import engine.operations.doubleops.Quotient;
import engine.operations.doubleops.Sum;
import engine.operations.doubleops.Value;
import engine.operations.gameobjectops.Get;

//TODO: DisplayInventory
public class DisplayInventory implements Action {

	private GameObject inventoryDisplay;
	private List<GameObject> scrollers;
	
	public DisplayInventory(GameObject inventoryDisplay, List<GameObject> scrollers) {
		this.inventoryDisplay = inventoryDisplay;
		this.scrollers = scrollers;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
	}

}
