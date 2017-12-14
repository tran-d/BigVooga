package authoring.GridManagers;

import java.io.File;
import java.util.List;

import authoring.Layers.BackgroundLayer;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.SpriteGridHandler;
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
		myMapLayer = new BackgroundLayer(getNumRows(), getNumCols(), mySpriteGridHandler);
		System.out.println("tempCols: "+defaultColumns);
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
	}
	
	
	@Override
	public void setCanFillBackground(){
		canFillBackground = true;
	}
	
	@Override
	public void getOnBackgroundChangeFunctionality(File f){
		Image image = new Image(GDH.getImageURIAndCopyToResources(f));
		AbstractSpriteObject ASO = getMapLayer().setBackgroundImage(()->new SpriteObject(image, f.getName()));
		this.populateCell(ASO, new Integer[]{0,0});
	}

	
}
