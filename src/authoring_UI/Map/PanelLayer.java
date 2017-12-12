package authoring_UI.Map;

import java.util.List;

import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteGridHandler;
import javafx.scene.paint.Color;

public class PanelLayer extends MapLayer {
	
	public PanelLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 2, SGH, Color.TRANSPARENT);
	}

	PanelLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setName("Panels");
	}

	public PanelLayer(int rows, int columns, SpriteGridHandler SGH,
			List<AbstractSpriteObject> activeSpriteObjects) {
		super(rows, columns, 0, SGH, Color.TRANSPARENT, activeSpriteObjects);
	}

}
