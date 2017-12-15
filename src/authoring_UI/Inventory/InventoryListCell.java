package authoring_UI.Inventory;

import javafx.scene.control.ListCell;

public class InventoryListCell extends ListCell<String> {

	private Inventory inventory;
	
	public InventoryListCell(Inventory d) {
//		this.setText("Name: " + d.getName());
		this.setText("a");
		this.inventory = d;
//		this.setOnMouseEntered(e -> showPopup(new ListCell<String>()));
	}
	
	public Inventory getInventory() {
		return inventory;
	}
}
