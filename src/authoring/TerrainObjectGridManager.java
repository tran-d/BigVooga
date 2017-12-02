package authoring;

import authoring_UI.SpriteGridHandler;
import authoring_UI.TerrainLayer;

public class TerrainObjectGridManager extends SpriteObjectGridManager{

	public TerrainObjectGridManager(int rows, int cols, SpriteGridHandler SGH){
		super(rows, cols, SGH);
	}
	
	@Override
	protected void createMapLayer() {
		myMapLayer = new TerrainLayer(CURR_ROWS, CURR_COLS, mySpriteGridHandler);
	}


}
