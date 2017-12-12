package authoring_UI.Map;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import javafx.scene.paint.Color;

public class SpriteLayer extends MapLayer {
	
	public SpriteLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 1, SGH, Color.TRANSPARENT);
	}

	SpriteLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setName("Main View");
	}

	public SpriteLayer(int rows, int columns, SpriteGridHandler SGH,
			List<AbstractSpriteObject> activeSpriteObjects) {
		super(rows, columns, 2, SGH, Color.TRANSPARENT, activeSpriteObjects);
		setName("Main View");
	}

}
