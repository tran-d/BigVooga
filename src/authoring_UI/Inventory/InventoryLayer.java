package authoring_UI.Inventory;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import javafx.scene.paint.Color;

public class InventoryLayer extends MapLayer {

	public InventoryLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, SGH, Color.TRANSPARENT);
	}

		public InventoryLayer(int rows, int columns, SpriteGridHandler SGH, Color c) {
		super(rows, columns, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("Inventory View");
	}

}
