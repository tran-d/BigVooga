package authoring;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public interface SpriteObjectGridManager {

	ImageView [][] getGrid();
	ImageView[][] populateCell(String spriteObjectType, ArrayList<Double []> row_col);
	SpriteParameter addNewParameter(String name, String type);
	void removeFromGrid(double[] row_col);
	public ArrayList<SpriteParameter> getSpriteParameters(ArrayList<Double []> row_col);
	
}
