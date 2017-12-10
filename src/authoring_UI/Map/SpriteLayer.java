package authoring_UI.Map;

import authoring_UI.SpriteGridHandler;
import javafx.scene.paint.Color;

public class SpriteLayer extends MapLayer {
	
	public SpriteLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 1, SGH, Color.TRANSPARENT);
	}

	SpriteLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("Main View");
	}

}
