package authoring_UI.SpriteCreatorTab;

import java.util.ArrayList;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring_UI.SpriteGridHandler;
import javafx.scene.image.ImageView;

public class SpriteCreatorGridManager extends SpriteObjectGridManager {
	
	private static int ROWS = 10;
	private static int COLUMNS = 6;
	
	
	public SpriteCreatorGridManager(SpriteGridHandler SGH){
		super(ROWS, COLUMNS, SGH);
	}
	
	public SpriteCreatorGridManager(){
		super(ROWS, COLUMNS);
	}

	protected SpriteCreatorGridManager(int rows, int columns, SpriteGridHandler SGH) {
		super(rows, columns, SGH);
	}
	
	@Override
	public void createMapLayer() {
		myMapLayer = new SpriteCreatorLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
		
	}
	
	@Override
	public int getLayerNum() {
		return myMapLayer.getLayerNumber();
	}

}
