package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class Inventory {

	private List<InventoryObject> objects;
	private InventoryObject selected;

	public Inventory() {
		// TODO Auto-generated constructor stub
		objects = new ArrayList<InventoryObject>();
	}

	public void addObject(InventoryObject newObject) {
		objects.add(newObject);

	}

	public List<InventoryObject> getFullInventory() {
		return new ArrayList<InventoryObject>(objects);

	}

	public void select(InventoryObject thatObject) {
		selected = thatObject;
	}

}
