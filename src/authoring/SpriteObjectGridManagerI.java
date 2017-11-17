package authoring;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public interface SpriteObjectGridManagerI {

	ImageView [][] getGrid();
	ImageView[][] populateCell(SpriteObjectI spriteObject, ArrayList<Integer []> row_col);
	public ArrayList<SpriteParameterI> getSpriteParameters(ArrayList<Integer []> row_col);
	ArrayList<SpriteObjectI> getActiveSpriteObjects();
	void clearCells(ArrayList<Integer[]> cellsToClear);
	void resetActiveCells();
	void removeActiveCells(ArrayList<Integer[]> makeInactive);
	void addActiveCells(ArrayList<Integer[]> makeActive);
	
}
