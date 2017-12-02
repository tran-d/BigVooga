package authoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import authoring_UI.MapLayer;
import authoring_UI.SpriteGridHandler;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SpriteObjectGridManager implements SpriteObjectGridManagerI {
	
	private ArrayList<ArrayList<SpriteObject>> spriteGrid;
	private int MAX_ROWS = 15;
	private int MAX_COLS = 15;
	private int CURR_ROWS = 15;
	private int CURR_COLS = 15;
	private SpriteObject defaultEmptySprite;
	private Set<Integer []> activeCells;
	private MapLayer myMapLayer;
	private SpriteGridHandler spriteGridHandler;
	
	
	SpriteObjectGridManager(int rows, int columns, int layerNum){
		
	}
	
	
	SpriteObjectGridManager(int rows, int columns, int layerNum, Color c){
		
	}
	
	
	
	SpriteObjectGridManager() {
		setDefaultEmptySprite(new SpriteObject());
		initializeGrid();
		activeCells = new HashSet<Integer[]>();
	}

	private void initializeGrid() {
		spriteGrid = new ArrayList<ArrayList<SpriteObject>>();
		for (int i=0; i<CURR_ROWS;i++){
			spriteGrid.add(new ArrayList<SpriteObject>());
			for (int j=0; j<CURR_COLS;j++){
				spriteGrid.get(i).add(defaultEmptySprite.newCopy());
			}
		}
	}
	
	@Override
	public ImageView[][] getGrid() {
		ImageView [][] ret = new ImageView[CURR_ROWS][CURR_COLS];
		for (int i=0;i<CURR_ROWS;i++){
			for (int j=0; j<CURR_COLS;j++){
				ret[i][j] = spriteGrid.get(i).get(j).getImageView();
			}
		}
		return ret;
	}
	
	@Override 
	public ArrayList<SpriteObject> getEntireListOfSpriteObjects() {
		ArrayList<SpriteObject> ret = new ArrayList<SpriteObject>();
		for (ArrayList<SpriteObject> SOI_LIST: spriteGrid){
			for (SpriteObject SOI: SOI_LIST){
				ret.add(SOI);
			}
		}
		return ret;
	}
	
	@Override
	public ImageView[][] populateCell(SpriteObject spriteObject, Integer[] row_col) {
		
			setCell(spriteObject, row_col);
		
		return getGrid();
	}

	@Override
	public ImageView[][] populateCell(SpriteObject spriteObject, ArrayList<Integer[]> row_col) {
		for (Integer [] loc: row_col) {
			setCell(spriteObject, loc);
		}
		return getGrid();
	}
	
	private void setCell(SpriteObject SOI, Integer[] loc) {
		System.out.println("Setcellwithpos: "+loc[0] + "," + loc[1]);
		System.out.println("Setcellwithobject: "+SOI);
		spriteGrid.get(loc[0]).set(loc[1], SOI);
	}
	
	private void setCellAsDefault(Integer[] loc) {
		
		setCell(defaultEmptySprite.newCopy(), loc);
	}

	@Override
	public ArrayList<SpriteParameterI> getSpriteParameters(ArrayList<Integer[]> row_col) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setDefaultEmptySprite(SpriteObject SPI) {
		defaultEmptySprite  = SPI.newCopy();
	}
	
	@Override
	public boolean switchCellActiveStatus(Integer[] makeActive){
//		System.out.println(makeActive);
		boolean ret = changeCellActiveStatus(makeActive);
		return ret;
	}
	
	@Override
	public void switchCellActiveStatus(ArrayList<Integer[]> makeActive){
//		activeCells.addAll(makeActive);
		makeActive.forEach(pos -> {
			changeCellActiveStatus(pos);
		});
	}
	
	
	private boolean changeCellActiveStatus(Integer [] pos){
//		System.out.println("ChangingStatus");
//		Integer[] a = {3,4};
//		Integer[] b = {3,4};
//		Integer[] c = {3,5};
//		System.out.println(Arrays.equals(a, b));
//		System.out.println(a.equals(c));
//		Iterator it = activeCells.iterator();
//		while (it.hasNext()){
//			it.next();
//			if (Arrays.equals(,  pos)){
//				
//			}
//		}
		System.out.println("pos: "+pos[0]+" "+pos[1]);
		
		for (Integer[] currentActive : activeCells){
			System.out.println("curr active: " + currentActive[0]+" "+currentActive[1]);
			if (Arrays.equals(currentActive, pos)){
				this.getCell(pos).clearPossibleParameters();;
				activeCells.remove(currentActive);
				System.out.println("removed");
				System.out.println("activeCells: " + activeCells);
				return false;
			}
		}
		
		activeCells.add(pos);
		return true;	
	}
	
//	@Override 
//	public void switchCellActiveStatus(SpriteObjectI SOI){
//		activeCells.add(SOI.getPositionOnGrid());
//	}
	
	@Override
	public void removeActiveCells(ArrayList<Integer[]> makeInactive){
		activeCells.removeAll(makeInactive);
	}
	
	@Override
	public void resetActiveCells() {
		activeCells.clear();
	}
	
	@Override
	public ArrayList<SpriteObject> getActiveSpriteObjects(){
		ArrayList<SpriteObject> ret = new ArrayList<SpriteObject>();
		for (Integer[] loc: activeCells){
			System.out.println("Active cell: "+ loc[0]+"," + loc[1]);
			ret.add(getCell(loc));
		}
		return ret;
	}
	
	@Override
	public void clearCells(ArrayList<Integer[]> cellsToClear){
		System.out.println(cellsToClear);
		removeActiveCells(cellsToClear);
		
		for (Integer[] loc: cellsToClear){
			System.out.println("clearCells loc loop: "+loc);
			
			setCellAsDefault(loc);	
		}
	}
	

	
	private SpriteObject getCell(Integer [] loc){
		return spriteGrid.get(loc[0]).get(loc[1]);
	}

	@Override
	public void matchActiveCellsToSprite(SpriteObject firstSprite) {
		for (SpriteObject SOI: getActiveSpriteObjects()){
			SOI.applyParameterUpdate(firstSprite.getParameters());
		}
		
	}
	

}
