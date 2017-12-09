package engine.Actions.changeObject;

import java.util.List;

import engine.Action;
import engine.GameObject;
import engine.Layer;

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
