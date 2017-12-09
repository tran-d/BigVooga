package authoring_UI.HUD;

import authoring_UI.MapLayer;
import authoring_UI.SpriteGridHandler;
import javafx.scene.paint.Color;

public class HUDLayer extends MapLayer {

	public HUDLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 3, SGH, Color.TRANSPARENT);
	}

		public HUDLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("HUD View");
	}

}
