package authoring_UI.SpriteCreatorTab;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.AuthoringMapStackPane;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SpriteCreatorLayer extends MapLayer {

	public SpriteCreatorLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 3, SGH, Color.TRANSPARENT);
	}

		public SpriteCreatorLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("SpriteCreator View");
	}
		
}

