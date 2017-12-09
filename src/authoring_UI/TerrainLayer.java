package authoring_UI;

import javafx.scene.paint.Color;

public class TerrainLayer extends MapLayer {
	
	public TerrainLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 0, SGH, Color.TRANSPARENT);
	}

	TerrainLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
//		setDefaultColor(Color.YELLOW);
		setName("Terrain");
	}

}
