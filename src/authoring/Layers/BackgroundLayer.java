package authoring.Layers;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.AuthoringMapStackPane;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BackgroundLayer extends MapLayer {
	
	public BackgroundLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, SGH, Color.TRANSPARENT);
	}

	BackgroundLayer(int rows, int columns, SpriteGridHandler SGH, Color c) {
		super(rows, columns, SGH, c);
		this.setGridLinesVisible(false);
//		setDefaultColor(Color.YELLOW);
		setName("Background");
	}
	
	public BackgroundLayer(int rows, int columns, SpriteGridHandler SGH,
			List<AbstractSpriteObject> activeSpriteObjects) {
		super(rows, columns, 0, SGH, Color.TRANSPARENT, activeSpriteObjects);
	}

	@Override 
	public void setBackgroundImage(Image image, String path){
		AuthoringMapStackPane AMSP = this.getChildAtPosition(0, 0);
		if (AMSP.hasChild()){
			AMSP.removeChild();
		}
		this.getChildren().forEach(cell->{
			((AuthoringMapStackPane) cell).setInactiveBackground(Color.TRANSPARENT);
		});

		AbstractSpriteObject ASO = new SpriteObject(image, path);
		AMSP.addChild(ASO);
		AMSP.setRowSpan(this.numRowsProperty.get());
		AMSP.setColSpan(this.numColumnsProperty.get());
		numColumnsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			AMSP.setColSpan(newNumColumns);
		});
		numRowsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			AMSP.setRowSpan(newNumColumns);
		});
	}
	
	

}
