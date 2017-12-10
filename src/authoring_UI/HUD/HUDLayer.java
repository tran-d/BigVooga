package authoring_UI.HUD;

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
import javafx.scene.paint.Color;

public class HUDLayer extends MapLayer {

	public HUDLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 3, SGH, Color.TRANSPARENT);
	}

		public HUDLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("HUD View");
	}

}
