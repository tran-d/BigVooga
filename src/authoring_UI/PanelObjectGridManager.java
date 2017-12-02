package authoring_UI;

import authoring.SpriteObjectGridManager;

public class PanelObjectGridManager extends SpriteObjectGridManager{

	protected PanelObjectGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}

	@Override
	protected void createMapLayer() {
		myMapLayer = new PanelLayer(CURR_ROWS, CURR_COLS, mySpriteGridHandler);
		
	}

}
