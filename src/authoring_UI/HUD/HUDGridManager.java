package authoring_UI.HUD;

import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;

public class HUDGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 2;
	private static int COLUMNS = 20;
	
	
	public HUDGridManager(SpriteGridHandler SGH){
		super(ROWS, COLUMNS, SGH);
	}
	
	public HUDGridManager(){
		super(ROWS, COLUMNS);
	}

	protected HUDGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	@Override
	public void createMapLayer() {
		myMapLayer = new HUDLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(temporaryColumns);
		this.setNumRows(temporaryRows);
		
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}

}
