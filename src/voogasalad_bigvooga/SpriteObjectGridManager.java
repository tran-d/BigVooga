package voogasalad_bigvooga;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public interface SpriteObjectGridManager {

	ImageView [][] getGrid();
	ImageView[][] populateCell(String spriteObjectType, ArrayList<Double []> row_col);
	SpriteParameterI addNewParameter(String name, String type);
	void removeFromGrid(double[] row_col);
	public ArrayList<SpriteParameterI> getSpriteParameters(ArrayList<Double []> row_col);
	
}
