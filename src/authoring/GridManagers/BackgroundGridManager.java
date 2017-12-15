package authoring.GridManagers;

import java.io.File;
import java.util.List;

import authoring.Layers.BackgroundLayer;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.PanelLayer;
import engine.utilities.data.GameDataHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BackgroundGridManager extends SpriteObjectGridManager{
	
	private GameDataHandler GDH;

	public BackgroundGridManager(){
		super();
	}

	public BackgroundGridManager(int rows, int columns, SpriteGridHandler SGH, GameDataHandler currentGDH) {
		super(rows, columns, SGH, currentGDH);
		GDH = currentGDH;
	}

	public BackgroundGridManager(int rows, int columns) {
		super(rows, columns);
	}

	@Override
	public void createMapLayer() {
		if (hasStoredSprites()) {
			loadedFromData = true;
			myMapLayer = new BackgroundLayer(defaultRows, defaultColumns, mySpriteGridHandler,
					getStoredSpriteList().get(0));
		} else {
			myMapLayer = new BackgroundLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		}
	}

	@Override
	public void setCanFillBackground() {
		canFillBackground = true;
	}

	@Override

	public void getOnBackgroundChangeFunctionality(File f){
		Image image = GDH.getImage(f);
		AbstractSpriteObject ASO = getMapLayer().setBackgroundImage(()->new SpriteObject(f.getName(), GDH));
		this.populateCell(ASO, new Integer[]{0,0});

	}

}
