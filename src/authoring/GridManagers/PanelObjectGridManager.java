package authoring.GridManagers;

import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.PanelLayer;
import javafx.scene.paint.Color;

public class PanelObjectGridManager extends SpriteObjectGridManager{

	public PanelObjectGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	public PanelObjectGridManager(int rows, int columns) {
		super(rows, columns);
	}

	public PanelObjectGridManager(int myNumRows, int myNumCols, int layerNum, Color myColor) {
		super(myNumRows, myNumCols, layerNum, myColor);
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
