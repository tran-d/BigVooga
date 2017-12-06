package authoring_UI;

import authoring.SpriteObjectGridManager;

public class PanelObjectGridManager extends SpriteObjectGridManager{

	protected PanelObjectGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	protected PanelObjectGridManager(int rows, int columns) {
		super(rows, columns);
	}

	@Override
	protected void createMapLayer() {
		myMapLayer = new PanelLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}
}
