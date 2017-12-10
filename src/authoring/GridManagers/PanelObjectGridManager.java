package authoring.GridManagers;

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

public class PanelObjectGridManager extends SpriteObjectGridManager{

	public PanelObjectGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	public PanelObjectGridManager(int rows, int columns) {
		super(rows, columns);
	}

	@Override
	public void createMapLayer() {
		myMapLayer = new PanelLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}
}
