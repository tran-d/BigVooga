package authoring.GridManagers;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.PanelLayer;

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
