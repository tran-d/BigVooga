package authoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import authoring_UI.MapLayer;
import authoring_UI.SpriteGridHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class SpriteObjectGridManager {
	
	protected List<List<SpriteObject>> spriteGrid;
	private int MAX_ROWS = 15;
	private int MAX_COLS = 15;
	private SpriteObject defaultEmptySprite;
	private Set<Integer []> activeCells;
	protected MapLayer myMapLayer;
	protected SpriteGridHandler mySpriteGridHandler;
	protected int myLayerNum;
	Color myColor;
	
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
		spriteGrid = new ArrayList<List<SpriteObject>>();
		List<SpriteObject> row1 = new ArrayList<SpriteObject>();
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
					List<SpriteObject> newRow = new ArrayList<SpriteObject>();
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
			
				for (List<SpriteObject> row: spriteGrid){
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
	}
	
	protected SpriteObjectGridManager(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns);
		mySpriteGridHandler = SGH;
		createMapLayer();
		this.numRowsProperty.set(rows);
		this.numColumnsProperty.set(columns);
	}
	
	
	public Color getColor() {
		return myColor;
	}
	
	public int getLayerNum() {
		return myLayerNum;
	}
	
	protected abstract void createMapLayer();
	
	public MapLayer getMapLayer(){
		return this.myMapLayer;
	}
	
	public void setVisible(boolean visibility){
		this.getMapLayer().setVisible(visibility);
	}
	
	public String getName(){
		return getMapLayer().getName();
	}
	
	public List<SpriteObject> getEntireListOfSpriteObjects() {
		List<SpriteObject> ret = new ArrayList<SpriteObject>();
		for (List<SpriteObject> SOI_LIST: spriteGrid){
			for (SpriteObject SOI: SOI_LIST){
				ret.add(SOI);
			}
		}
		return ret;
	}
	
	public void populateCell(SpriteObject spriteObject, Integer[] row_col) {
		setCell(spriteObject, row_col);
	}

	public void populateCell(SpriteObject spriteObject, ArrayList<Integer[]> row_col) {
		for (Integer [] loc: row_col) {
			setCell(spriteObject, loc);
		}
	}
	
	private void setCell(SpriteObject SOI, Integer[] loc) {
		System.out.println("Setcellwithpos: "+loc[0] + "," + loc[1]);
		System.out.println("Setcellwithobject: "+SOI);
		spriteGrid.get(loc[0]).set(loc[1], SOI);
	}
	
	private void setCellAsDefault(Integer[] loc) {
		setCell(defaultEmptySprite.newCopy(), loc);
	}
	
	public void setDefaultEmptySprite(SpriteObject SPI) {
		defaultEmptySprite  = SPI.newCopy();
	}
	
	public boolean switchCellActiveStatus(Integer[] makeActive){
		boolean ret = changeCellActiveStatus(makeActive);
		return ret;
	}
	
	
	public void switchCellActiveStatus(ArrayList<Integer[]> makeActive){
		makeActive.forEach(pos -> {
			changeCellActiveStatus(pos);
		});
	}
	
	private boolean changeCellActiveStatus(Integer [] pos){
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
	
	public void removeActiveCells(List<Integer[]> dummy){
		dummy.forEach(a->{
			removeActiveCell(a);
		});
	}
	
	public void resetActiveCells() {
		List<Integer []>  dummy = new ArrayList<Integer[]>(activeCells);
		removeActiveCells(dummy);
	}
	
	public List<SpriteObject> getActiveSpriteObjects(){
		List<SpriteObject> ret = new ArrayList<SpriteObject>();
		for (Integer[] loc: activeCells){
			System.out.println("Active cell: "+ loc[0]+"," + loc[1]);
			ret.add(getCell(loc));
		}
		return ret;
	}
	
	public void clearCells(List<Integer[]> cellsToDelete){
		System.out.println("cellsToClear :" + cellsToDelete);
		removeActiveCells(cellsToDelete);
		getMapLayer().removeSpritesAtPositions(cellsToDelete);
		
		for (Integer[] loc: cellsToDelete){
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