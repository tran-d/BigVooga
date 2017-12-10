package authoring_UI.Inventory;

import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.Sprite.Parameters.*;
import authoring.Sprite.AnimationSequences.*;
import authoring.Sprite.UtilityTab.*;
import authoring.Sprite.InventoryTab.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;
import authoring_UI.SpriteGridHandler;
import javafx.scene.paint.Color;

public class InventoryLayer extends MapLayer {

	public InventoryLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 3, SGH, Color.TRANSPARENT);
	}

		public InventoryLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("Inventory View");
	}

}
