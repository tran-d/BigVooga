package authoring_UI;

import javafx.scene.paint.Color;

public class PanelLayer extends MapLayer {
	
	PanelLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 2, SGH, Color.TRANSPARENT);
	}

	PanelLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
		setDefaultColor(Color.TRANSPARENT);
		setName("Panels");
	}

}
