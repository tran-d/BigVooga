package authoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import authoring_UI.MapLayer;
import authoring_UI.SpriteGridHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class SpriteObjectGridManager {
	
	protected ArrayList<ArrayList<SpriteObject>> spriteGrid;
	private int MAX_ROWS = 15;
	private int MAX_COLS = 15;
//	protected int CURR_ROWS;
//	protected int CURR_COLS;
	private SpriteObject defaultEmptySprite;
	private Set<Integer []> activeCells;
	protected MapLayer myMapLayer;
	protected SpriteGridHandler mySpriteGridHandler;
	protected int myLayerNum;
	Color myColor;
	protected int temporaryRows;
	protected int temporaryColumns;
	
	protected ObjectProperty<Integer> numRowsProperty;
	protected ObjectProperty<Integer> numColumnsProperty;
	
	public SpriteObjectGridManager(int rows, int columns, int layerNum, Color c){
		this(rows, columns);
		myColor = c;
		createMapLayer();
	}
	
	public SpriteObjectGridManager(int rows, int cols) {
		setDefaultEmptySprite(new SpriteObject());
		numRowsProperty = new SimpleObjectProperty<Integer>();
		numColumnsProperty = new SimpleObjectProperty<Integer>();
		numRowsProperty.set(1);
		numColumnsProperty.set(1);
		spriteGrid = new ArrayList<ArrayList<SpriteObject>>();
		ArrayList<SpriteObject> row1 = new ArrayList<SpriteObject>();
		row1.add(defaultEmptySprite.newCopy());
		spriteGrid.add(row1);
		activeCells = new HashSet<Integer[]>();

		numRowsProperty.addListener((observable, oldNumRows, newNumRows)->{
			System.out.println("newNumRows: "+newNumRows);
			System.out.println("oldNumRows: "+oldNumRows);
			if (this.getMapLayer()!=null){
				this.getMapLayer().setNumRows(newNumRows);
			}
			Integer diff = newNumRows-oldNumRows;
			if (diff>0){
				for (int i=0; i<diff;i++){
					ArrayList<SpriteObject> newRow = new ArrayList<SpriteObject>();
					for (int j=0; j<numColumnsProperty.get(); j++){
						newRow.add(defaultEmptySprite.newCopy());
						}
					spriteGrid.add(newRow);
				}
			} else if (diff<0){
				for (int i=0; i>diff;i--){
					spriteGrid.remove(oldNumRows-i-1);
				}
			}
			System.out.println("spriteGridNuMRows: " +spriteGrid.size());
			System.out.println("spriteGridNumCols: "+spriteGrid.get(0).size());
		});
		
		numColumnsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			System.out.println("newNumCols: "+newNumColumns);
			System.out.println("oldNumCols: "+oldNumColumns);
			if (this.getMapLayer()!=null){
				this.getMapLayer().setNumCols(newNumColumns);
			}
			Integer diff = newNumColumns-oldNumColumns;
			
				for (ArrayList<SpriteObject> row: spriteGrid){
					if (diff>0){
						for (int i=0; i<diff;i++){
							row.add(defaultEmptySprite.newCopy());
					}
						} else if (diff<0){
						for (int i=0;i>diff;i--){
							row.remove(oldNumColumns-i-1);
						}
					}
				}
		});
		temporaryRows = rows;
		temporaryColumns = cols;
//		initializeGrid();

		
	}
	
	
	public SpriteObjectGridManager(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns);
		
		setSpriteGridHandler(SGH);
		createMapLayer();
//		myLayerNum = layerNum;
//		createMapLayer();
		
		this.numRowsProperty.set(rows);
		this.numColumnsProperty.set(columns);
	}
	
	public void setSpriteGridHandler(SpriteGridHandler SGH){
		mySpriteGridHandler = SGH;
	}
	
	
	public Color getColor() {
		return myColor;
	}
	
//	public int getNumRows() {
//		return CURR_ROWS;
//	}
//	
//	public int getNumCols() {
//		return CURR_COLS;
//	}
	
	public int getLayerNum() {
		return myLayerNum;
	}
	

	
	
	public abstract void createMapLayer();
	
	public MapLayer getMapLayer(){
		return this.myMapLayer;
	}
	
	public void setVisible(boolean visibility){
		this.getMapLayer().setVisible(visibility);
	}
	
	public String getName(){
		return getMapLayer().getName();
	}

//	private void initializeGrid() {
//		spriteGrid = new ArrayList<ArrayList<SpriteObject>>();
//		for (int i=0; i<CURR_ROWS;i++){
//			spriteGrid.add(new ArrayList<SpriteObject>());
//			for (int j=0; j<CURR_COLS;j++){
//				spriteGrid.get(i).add(defaultEmptySprite.newCopy());
//			}
//		}
//	}
	
//	@Override
//	public ImageView[][] getGrid() {
//		ImageView [][] ret = new ImageView[CURR_ROWS][CURR_COLS];
//		for (int i=0;i<CURR_ROWS;i++){
//			for (int j=0; j<CURR_COLS;j++){
//				ret[i][j] = spriteGrid.get(i).get(j).getImageView();
//			}
//		}
//		return ret;
//	}
	
 
	public ArrayList<SpriteObject> getEntireListOfSpriteObjects() {
		ArrayList<SpriteObject> ret = new ArrayList<SpriteObject>();
		for (ArrayList<SpriteObject> SOI_LIST: spriteGrid){
			for (SpriteObject SOI: SOI_LIST){
				ret.add(SOI);
			}
		}
		return ret;
	}
	

	public void populateCell(SpriteObject spriteObject, Integer[] row_col) {
		
			setCell(spriteObject, row_col);
		
//		return getGrid();
	}


	public void populateCell(SpriteObject spriteObject, ArrayList<Integer[]> row_col) {
		for (Integer [] loc: row_col) {
			setCell(spriteObject, loc);
		}
//		return getGrid();
	}
	
	private void setCell(SpriteObject SOI, Integer[] loc) {
		System.out.println("Setcellwithpos: "+loc[0] + "," + loc[1]);
		System.out.println("Setcellwithobject: "+SOI);
		spriteGrid.get(loc[0]).set(loc[1], SOI);
	}
	
	private void setCellAsDefault(Integer[] loc) {
		
		setCell(defaultEmptySprite.newCopy(), loc);
	}

//	@Override
//	public ArrayList<SpriteParameterI> getSpriteParameters(ArrayList<Integer[]> row_col) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public void setDefaultEmptySprite(SpriteObject SPI) {
		defaultEmptySprite  = SPI.newCopy();
	}
	

	public boolean switchCellActiveStatus(Integer[] makeActive){
//		System.out.println(makeActive);
		boolean ret = changeCellActiveStatus(makeActive);
		return ret;
	}
	
	
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
				removeActiveCell(pos);
				return false;
			}
		}
		activeCells.add(pos);
		return true;	
	}
	
	public void removeActiveCell(Integer [] in){
		this.getCell(in).clearPossibleParameters();
		activeCells.remove(in);
	}
	
	public void addActiveCell(AbstractSpriteObject ASO){
		activeCells.add(ASO.getPositionOnGrid());
	}
	
	public void removeActiveCell(AbstractSpriteObject ASO){
		removeActiveCell(ASO.getPositionOnGrid());
	}
	
	
//	@Override 
//	public void switchCellActiveStatus(SpriteObjectI SOI){
//		activeCells.add(SOI.getPositionOnGrid());
//	}
	
	public void removeActiveCells(ArrayList<Integer[]> makeInactive){
//		activeCells.removeAll(makeInactive);
		makeInactive.forEach(a->{
			removeActiveCell(a);
		});
	}
	
	public void resetActiveCells() {
		ArrayList<Integer []>  dummy = new ArrayList<Integer[]>(activeCells);
		removeActiveCells(dummy);
	}
	
	public ArrayList<SpriteObject> getActiveSpriteObjects(){
		ArrayList<SpriteObject> ret = new ArrayList<SpriteObject>();
		for (Integer[] loc: activeCells){
			System.out.println("Active cell: "+ loc[0]+"," + loc[1]);
			ret.add(getCell(loc));
		}
		return ret;
	}
	
	public void clearCells(ArrayList<Integer[]> cellsToClear){
		
		System.out.println("cellsToClear :" + cellsToClear);
		removeActiveCells(cellsToClear);
		getMapLayer().removeSpritesAtPositions(cellsToClear);
		
		for (Integer[] loc: cellsToClear){
			System.out.println("clearCells loc loop: "+loc);
			setCellAsDefault(loc);	
		}
	}

	private SpriteObject getCell(Integer [] loc){
		return spriteGrid.get(loc[0]).get(loc[1]);
	}

	public void matchActiveCellsToSprite(SpriteObject firstSprite) {
		for (SpriteObject SOI: getActiveSpriteObjects()){
			SOI.applyParameterUpdate(firstSprite.getParameters());
		}
	}
	
	public void addRow(){
		setNumCols(numRowsProperty.get()+1);
	}
	
	public void setNumRows(Integer newRows){
		System.out.println("Setting num rows: "+newRows);
		this.numRowsProperty.set(newRows);
	}
	
	public Integer getNumRows(){
		return this.numRowsProperty.get();
	}
	
	public void addColumn(){
		setNumCols(numColumnsProperty.get()+1);
	}
	public Integer getNumCols(){
		return this.numColumnsProperty.get();
	}
	
	public void setNumCols(Integer newCols){
		System.out.println("Setting num cols: "+newCols);
		this.numColumnsProperty.set(newCols);
	}
	
}