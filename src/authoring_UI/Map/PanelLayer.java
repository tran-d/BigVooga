package authoring_UI.Map;

import javafx.scene.paint.Color;
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

public class PanelLayer extends MapLayer {
	
	public PanelLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 2, SGH, Color.TRANSPARENT);
	}

	PanelLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("Panels");
	}

}
