package authoring.GridManagers;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.PanelLayer;
import authoring_UI.Map.TerrainLayer;
import javafx.scene.paint.Color;

public class PanelObjectGridManager extends SpriteObjectGridManager{

	public PanelObjectGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
		myLayerNum = 3;
	}
	
	public PanelObjectGridManager(int rows, int columns) {
		super(rows, columns);
		myLayerNum = 3;
	}

	public PanelObjectGridManager(int myNumRows, int myNumCols, int layerNum, Color myColor) {
		super(myNumRows, myNumCols, layerNum, myColor);
		myLayerNum = 3;
	}

	@Override
	public void createMapLayer() {
		myMapLayer = new PanelLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
	}
	
	@Override
	public int getLayerNum() {
		return myLayerNum;
		//return myMapLayer.getLayerNumber();
	}

	public void createMapLayer(List<AbstractSpriteObject> activeSpriteObjects) {
		if (activeSpriteObjects.size() == 0) createMapLayer();
		else {
			System.out.println("POGM SPECIAL MAP LAYER CONSTRUCTOR IS CALLED");
			myMapLayer = new PanelLayer(getRowsForImport(), getColsForImport(), mySpriteGridHandler, activeSpriteObjects);
		}
		
	}
}
