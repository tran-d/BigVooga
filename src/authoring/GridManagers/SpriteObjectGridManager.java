package authoring.GridManagers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.DefaultSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.SpriteGridHandler;
import authoring_UI.Map.MapLayer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public abstract class SpriteObjectGridManager {

	protected List<List<AbstractSpriteObject>> spriteGrid;
	private AbstractSpriteObject defaultEmptySprite;
	private Set<Integer[]> activeCells;
	protected MapLayer myMapLayer;
	protected SpriteGridHandler mySpriteGridHandler;
	protected int myLayerNum;
	protected Color myColor;
	protected int defaultRows;
	protected int defaultColumns;
	protected boolean canFillBackground;
	protected List<AbstractSpriteObject> storedSpriteList;
	protected int numRowsForImport;
	protected int numColsForImport;
	protected boolean loadedFromData;

	protected ObjectProperty<Integer> numRowsProperty;
	protected ObjectProperty<Integer> numColumnsProperty;

	public SpriteObjectGridManager() {
	}

	public SpriteObjectGridManager(int rows, int columns, Color c) {
		this(rows, columns);
		myColor = c;
		// createMapLayer() do elsewhere now
	}

	public void storeSpriteObjectsToAdd(List<AbstractSpriteObject> spritesToAdd) {
		storedSpriteList = spritesToAdd;
	}

	public List<AbstractSpriteObject> getStoredSpriteList() {
		return storedSpriteList;
	}

	public boolean hasStoredSprites() {
		return (getStoredSpriteList() != null && getStoredSpriteList().size() > 0);

	}

	public SpriteObjectGridManager(int rows, int cols) {
		setCanFillBackground();
		setDefaultEmptySprite(new DefaultSpriteObject());
		numRowsProperty = new SimpleObjectProperty<Integer>();
		numColumnsProperty = new SimpleObjectProperty<Integer>();
		numRowsProperty.set(1);
		numColumnsProperty.set(1);
		spriteGrid = new ArrayList<List<AbstractSpriteObject>>();
		List<AbstractSpriteObject> row1 = new ArrayList<AbstractSpriteObject>();
		row1.add(defaultEmptySprite.newCopy());
		spriteGrid.add(row1);
		activeCells = new HashSet<Integer[]>();

		numRowsProperty.addListener((observable, oldNumRows, newNumRows) -> {
			System.out.println("newNumRows: " + newNumRows);
			System.out.println("oldNumRows: " + oldNumRows);
			if (this.getMapLayer() != null) {
				this.getMapLayer().setNumRows(newNumRows);
			}
			Integer diff = newNumRows - oldNumRows;
			if (diff > 0) {
				for (int i = 0; i < diff; i++) {
					List<AbstractSpriteObject> newRow = new ArrayList<AbstractSpriteObject>();
					for (int j = 0; j < numColumnsProperty.get(); j++) {
						newRow.add(defaultEmptySprite.newCopy());
					}
					spriteGrid.add(newRow);
				}
			} else if (diff < 0) {
				for (int i = 0; i > diff; i--) {
					spriteGrid.remove(oldNumRows - i - 1);
				}
			}
			System.out.println("spriteGridNuMRows: " + spriteGrid.size());
			System.out.println("spriteGridNumCols: " + spriteGrid.get(0).size());
		});

		numColumnsProperty.addListener((observable, oldNumColumns, newNumColumns) -> {
			System.out.println("newNumCols: " + newNumColumns);
			System.out.println("oldNumCols: " + oldNumColumns);
			if (this.getMapLayer() != null) {
				this.getMapLayer().setNumCols(newNumColumns);
			}
			Integer diff = newNumColumns - oldNumColumns;

			for (List<AbstractSpriteObject> row : spriteGrid) {
				if (diff > 0) {
					for (int i = 0; i < diff; i++) {
						row.add(defaultEmptySprite.newCopy());
					}
				} else if (diff < 0) {
					for (int i = 0; i > diff; i--) {
						row.remove(oldNumColumns - i - 1);
					}
				}
			}
		});
		defaultRows = rows;
		defaultColumns = cols;
		// initializeGrid();
	}

	public SpriteObjectGridManager(int rows, int columns, SpriteGridHandler SGH) {
		this(rows, columns);

		setSpriteGridHandler(SGH);
		createMapLayer();
		setSizeToMatchDefaults();
	}

	public void setSpriteGridHandler(SpriteGridHandler SGH) {
		mySpriteGridHandler = SGH;
	}

	public Color getColor() {
		return myColor;
	}

	public void updateSpriteGrid() {
		System.out.println("Updating sprite list, sprite grid:");
		System.out.println(this.spriteGrid);
		System.out.println("spriteGrid size: " + this.spriteGrid.size());
		if (getStoredSpriteList().size() != 0) {
			getStoredSpriteList().forEach(sprite -> {
				setCell(sprite, sprite.getPositionOnGrid());
			});

		}
	}

	public void setColor(Color color) {
		myColor = color;
		this.getMapLayer().setFillColor(color);
	}

	public void setCanFillBackground() {
		canFillBackground = false;
	}

	public int getDefaultCols() {
		return defaultColumns;
	}

	public int getDefaultRows() {
		return defaultRows;
	}

	public boolean canFillBackground() {
		return canFillBackground;
	}

	public int getLayerNum() {
		return myLayerNum;
	}

	public abstract void createMapLayer();

	// public abstract void createMapLayer(List<AbstractSpriteObject>
	// activeSpriteObjects);

	public MapLayer getMapLayer() {
		return this.myMapLayer;
	}

	public void setVisible(boolean visibility) {
		this.getMapLayer().setVisible(visibility);
	}

	public String getName() {
		return getMapLayer().getName();
	}

	public List<AbstractSpriteObject> getEntireListOfSpriteObjects() {
		List<AbstractSpriteObject> ret = new ArrayList<AbstractSpriteObject>();
		for (List<AbstractSpriteObject> SOI_LIST : spriteGrid) {
			SOI_LIST.forEach(sprite -> {
				if (sprite instanceof SpriteObject) {
					ret.add(sprite);
				}
			});

		}

		System.out.println("List of sprites: " + ret);
		System.out.println("List of sprites size: " + ret.size());
		return ret;
	}

	public void setSizeToMatchDefaults() {
		this.setNumCols(defaultColumns);
		this.setNumRows(defaultRows);
		if (this.loadedFromData) {
			updateSpriteGrid();
		}
	}

	public void populateCell(SpriteObject spriteObject, Integer[] row_col) {
		setCell(spriteObject, row_col);
	}

	public void populateCell(SpriteObject spriteObject, ArrayList<Integer[]> row_col) {
		for (Integer[] loc : row_col) {
			setCell(spriteObject, loc);
		}
	}

	private void setCell(AbstractSpriteObject SOI, Integer[] loc) {
		spriteGrid.get(loc[0]).set(loc[1], SOI);
		System.out.println("setCell, into spriteGrid: "+SOI);
	}

	private void setCellAsDefault(Integer[] loc) {
		setCell(defaultEmptySprite.newCopy(), loc);
	}

	public void setDefaultEmptySprite(AbstractSpriteObject SPI) {
		defaultEmptySprite = SPI.newCopy();
	}

	public boolean switchCellActiveStatus(Integer[] makeActive) {
		boolean ret = changeCellActiveStatus(makeActive);
		return ret;
	}

	public void switchCellActiveStatus(ArrayList<Integer[]> makeActive) {
		makeActive.forEach(pos -> {
			changeCellActiveStatus(pos);
		});
	}

	private boolean changeCellActiveStatus(Integer[] pos) {
		System.out.println("pos: " + pos[0] + " " + pos[1]);

		for (Integer[] currentActive : activeCells) {
			if (Arrays.equals(currentActive, pos)) {
				removeActiveCell(pos);
				return false;
			}
		}
		addActiveCell(pos);
		return true;
	}

	public void removeActiveCell(Integer[] in) {
		this.getCell(in).clearPossibleParameters();
		activeCells.remove(in);
	}

	public void addActiveCell(Integer[] pos) {
		System.out.println("Adding active cell!: " + pos[0] + " " + pos[1]);

		activeCells.add(pos);
		System.out.println("active cells size!: " + activeCells.size());
	}

	public void addActiveCell(AbstractSpriteObject ASO) {
		addActiveCell(ASO.getPositionOnGrid());
	}

	public void addActiveCells(List<AbstractSpriteObject> ASOList) {
		for (AbstractSpriteObject ASO : ASOList) {
			this.addActiveCell(ASO);
		}
	}

	public void removeActiveCell(AbstractSpriteObject ASO) {
		removeActiveCell(ASO.getPositionOnGrid());
	}

	public int getRowsForImport() {
		return numRowsForImport;
	}

	public int getColsForImport() {
		return numColsForImport;
	}

	public void removeActiveCells(List<Integer[]> dummy) {
		dummy.forEach(a -> {
			removeActiveCell(a);
		});
	}

	public void resetActiveCells() {
		List<Integer[]> dummy = new ArrayList<Integer[]>(activeCells);
		removeActiveCells(dummy);
	}

	public List<AbstractSpriteObject> getActiveSpriteObjects() {
		List<AbstractSpriteObject> ret = new ArrayList<AbstractSpriteObject>();
		for (Integer[] loc : activeCells) {
			ret.add(getCell(loc));
		}
		return ret;
	}

	public void clearCells(List<Integer[]> cellsToDelete) {
		System.out.println("cellsToClear :" + cellsToDelete);
		// removeActiveCells(cellsToDelete);
		getMapLayer().removeSpritesAtPositions(cellsToDelete);
		for (Integer[] loc : cellsToDelete) {
			System.out.println("clearCells loc loop: " + loc);
			setCellAsDefault(loc);
		}
	}

	private AbstractSpriteObject getCell(Integer[] loc) {
		AbstractSpriteObject ret = spriteGrid.get(loc[0]).get(loc[1]);
		System.out.println("Returning from getCell :" + ret);
		return ret;

	}

	public void matchActiveCellsToSprite(AbstractSpriteObject firstSprite) {
		for (AbstractSpriteObject SOI : getActiveSpriteObjects()) {
			System.out.println("Active Sprite Params: " + SOI.getParameters());
			SOI.applyParameterUpdate(firstSprite.getParameters());
			SOI.setAllActions(firstSprite.getAllActions());
			SOI.setCondidtionRows(firstSprite.getConditionRows());
			SOI.setActionRows(firstSprite.getActionRows());
		}
	}

	public void addRow() {
		setNumCols(numRowsProperty.get() + 1);
	}

	public void setNumRows(Integer newRows) {
		System.out.println("Setting num rows: " + newRows);
		this.numRowsProperty.set(newRows);
	}

	public Integer getNumRows() {
		return this.numRowsProperty.get();
	}

	public void addColumn() {
		setNumCols(numColumnsProperty.get() + 1);
	}

	public Integer getNumCols() {
		return this.numColumnsProperty.get();
	}

	public void setNumCols(Integer newCols) {
		System.out.println("Setting num cols: " + newCols);
		this.numColumnsProperty.set(newCols);
	}
}