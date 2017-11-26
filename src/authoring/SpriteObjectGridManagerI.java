package authoring;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public interface SpriteObjectGridManagerI {

	ImageView [][] getGrid();
	ImageView[][] populateCell(SpriteObject spriteObject, ArrayList<Integer []> row_col);
	public ArrayList<SpriteParameterI> getSpriteParameters(ArrayList<Integer []> row_col);
	ArrayList<SpriteObject> getActiveSpriteObjects();
	void clearCells(ArrayList<Integer[]> cellsToClear);
	void resetActiveCells();
	void removeActiveCells(ArrayList<Integer[]> makeInactive);
	void switchCellActiveStatus(ArrayList<Integer[]> makeActive);
	void matchActiveCellsToSprite(SpriteObject firstSprite);
	ArrayList<SpriteObject> getEntireListOfSpriteObjects();
//	void switchCellActiveStatus(SpriteObjectI SOI);
	boolean switchCellActiveStatus(Integer[] makeActive);
	ImageView[][] populateCell(SpriteObject spriteObject, Integer[] row_col);
	
}
