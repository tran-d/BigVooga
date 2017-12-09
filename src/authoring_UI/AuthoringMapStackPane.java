package authoring_UI;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.event.ChangeListener;

import authoring.AbstractSpriteObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class AuthoringMapStackPane extends StackPane {

	MapLayer myML;
	ObjectProperty<Integer> rowSpanProperty;
	ObjectProperty<Integer> colSpanProperty;
	private int cellSize;
	private boolean active;
	private ObjectProperty<Boolean> activeProperty;
	private Background activeBackground;
	private Background inactiveBackground;
	private BiConsumer<AbstractSpriteObject, Integer> myBindSpriteWidth;
	private BiConsumer<AbstractSpriteObject, Integer> myBindSpriteHeight;
	private AbstractSpriteObject coveringSprite;
	private AbstractSpriteObject mySO;

	private ObjectProperty<Boolean> coveredByStretchedSpriteProperty;
	// private int myRow =

	AuthoringMapStackPane(MapLayer ML) {
		super();
		activeBackground = new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY));
		inactiveBackground = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
		cellSize = MapLayer.CELL_SIZE;

		initializeProperties();
		myML = ML;
	}

	private void initializeProperties() {
		if (rowSpanProperty == null) {
			rowSpanProperty = new SimpleObjectProperty<Integer>();
			rowSpanProperty.set(1);
		}
		this.setRowSpan(1); // Needed to handle removing children

		if (colSpanProperty == null) {
			colSpanProperty = new SimpleObjectProperty<Integer>();
			colSpanProperty.set(1);
		}

		this.setColSpan(1); // Needed to handle removing children

		colSpanProperty.addListener((observable, oldValue, newValue) -> {
			getOnColSpanChange().accept(oldValue, newValue);
		});

		rowSpanProperty.addListener((observable, oldValue, newValue) -> {
			getOnRowSpanChange().accept(oldValue, newValue);
		});

		createActiveProperty();
		createCoveredByOtherSpriteProperty();
		createShapeSpriteWidth();
		createShapeSpriteHeight();
	}

	public void setCoveringSprite(AbstractSpriteObject ASO) {
		this.coveringSprite = ASO;
	}

	public void removeCoveringSprite() {
		this.coveringSprite = null;
	}

	private BiConsumer<Integer, Integer> getOnRowSpanChange() {
		BiConsumer<Integer, Integer> onRowChange = new BiConsumer<Integer, Integer>() {
			// rowSpanProperty.addListener((observable, oldValue, newValue)->{

			// });
			@Override
			public void accept(Integer oldValue, Integer newValue) {
//				System.out.println("newValueRowSpan: " + newValue);
//				System.out.println("oldValueRowSpan: " + newValue);
				int diff = newValue - oldValue;
				int startRow = (diff > 0) ? getRowIndex() + oldValue : getRowIndex() + oldValue - 1;
				for (int i = 0; i < Math.abs(diff); i++) {
					int newRow = startRow + (int) Math.signum(diff) * i;
					for (int column = getColIndex(); column <= getFarRightColumn(); column++) {
						getMapLayer().getChildAtPosition(newRow, column).changeCoveredByOtherSprite();
						getMapLayer().getChildAtPosition(newRow, column).setInactive();

						if (getMapLayer().getChildAtPosition(newRow, column).isCoveredByOtherSprite()) {
							getMapLayer().getChildAtPosition(newRow, column).setCoveringSprite(mySO);
						}

						// getMapLayer().getChildAtPosition(newRow,
						// column).setBackground(new Background(new
						// BackgroundFill(Color.BLACK, CornerRadii.EMPTY,
						// Insets.EMPTY)));
					}
				}

			}
		};
		return onRowChange;

	}

	private BiConsumer<Integer, Integer> getOnColSpanChange() {
		BiConsumer<Integer, Integer> onColChange = new BiConsumer<Integer, Integer>() {

			@Override
			public void accept(Integer oldValue, Integer newValue) {
				// System.out.println("col span change old valeu: " +oldValue);
				// System.out.println("col span change new valeu: " +newValue);
				int diff = newValue - oldValue;
				int startCol = (diff > 0) ? getColIndex() + oldValue : getColIndex() + oldValue - 1;
				for (int i = 0; i < Math.abs(diff); i++) {
					int newColumn = startCol + (int) Math.signum(diff) * i;
					for (int row = getRowIndex(); row <= getFarBottomRow(); row++) {
						getMapLayer().getChildAtPosition(row, newColumn).changeCoveredByOtherSprite();
						getMapLayer().getChildAtPosition(row, newColumn).setInactive();
						if (getMapLayer().getChildAtPosition(row, newColumn).isCoveredByOtherSprite()) {
							getMapLayer().getChildAtPosition(row, newColumn).setCoveringSprite(mySO);
						}
						// getMapLayer().getChildAtPosition(row,
						// newColumn).setBackground(new Background(new
						// BackgroundFill(Color.BLACK, CornerRadii.EMPTY,
						// Insets.EMPTY)));
					}
				}

			}

		};
		return onColChange;
	}
	// colSpanProperty.addListener((observable, oldValue, newValue)->{
	// System.out.println("Changing column span:\nOld: "+oldValue+"\nNew:
	// "+newValue);
	//
	//
	//
	// });
	// }

	// AuthoringMapStackPane(MapLayer M, int row, int col){
	// this(ML);
	//
	//
	// }

	private void createCoveredByOtherSpriteProperty() {
		coveredByStretchedSpriteProperty = new SimpleObjectProperty<Boolean>();
		coveredByStretchedSpriteProperty.set(false);
		coveredByStretchedSpriteProperty.addListener((e, oldVal, newVal) -> {
			if (!newVal) {
				this.coveringSprite = null;
			}
			// System.out.println("My cover property was " + oldVal + ", but now
			// it is " + newVal);
		});

	}

	public void setCoveredByOtherSprite(boolean covered) {
		coveredByStretchedSpriteProperty.set(covered);
	}

	public void changeCoveredByOtherSprite() {
		coveredByStretchedSpriteProperty.set(!coveredByStretchedSpriteProperty.get());
	}

	public boolean isCoveredByOtherSprite() {
		return coveredByStretchedSpriteProperty.get();
	}

	public AbstractSpriteObject getCoveringSprite() {
		return this.coveringSprite;
	}

	private void createActiveProperty() {
		activeProperty = new SimpleObjectProperty<Boolean>();
		activeProperty.set(false);
		activeProperty.addListener((observable, oldValue, newValue) -> {
			// System.out.println("new value: " + newValue);
			if (newValue) {
				this.getMapLayer().addActive(this);
				this.setBackground(activeBackground);
			} else {
				this.getMapLayer().removeActive(this);
				this.setBackground(inactiveBackground);
			}
		});
	}

	public boolean isActive() {
		return activeProperty.get();
	}

	public void setActive() {
		activeProperty.set(true);
	}

	public void setInactive() {
		activeProperty.set(false);
	}

	public void setActiveProperty(boolean in) {
		activeProperty.set(in);
	}

	public void switchActive() {
		activeProperty.set(!activeProperty.get());
	}

	public MapLayer getMapLayer() {
		return myML;
	}

	public void createShapeSpriteWidth() {
		myBindSpriteWidth = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				t.setFitWidth(u * cellSize);
			}

		};
	}

	public void createDefaultShapeSpriteWidth() {
		myBindSpriteWidth = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				// Nothing
			}

		};
	}

	public void createDefaultShapeSpriteHeight() {
		myBindSpriteHeight = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				// Nothing
			}

		};
	}

	public void createShapeSpriteHeight() {
		myBindSpriteHeight = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				t.setFitHeight(u * cellSize);
			}

		};
	}

	private BiConsumer<AbstractSpriteObject, Integer> getShapeSpriteHeightBiConsumer() {
		return myBindSpriteHeight;
	}

	private BiConsumer<AbstractSpriteObject, Integer> getShapeSpriteWidthBiConsumer() {
		return myBindSpriteWidth;
	}

	public void shapeSpriteToCellSize() {
		createShapeSpriteWidth();
		createShapeSpriteHeight();
		this.setAlignment(mySO, Pos.CENTER_LEFT);

		
		this.rowSpanProperty.addListener((observable, oldValue, newValue) -> {
			// getOnRowSpanChange().accept(oldValue, newValue);
			this.getShapeSpriteHeightBiConsumer().accept(mySO, newValue);
		});

		this.colSpanProperty.addListener((observable, oldValue, newValue) -> {
			// getOnColSpanChange().accept(oldValue, newValue);
			getShapeSpriteWidthBiConsumer().accept(mySO, newValue);
		});
		// ASO.setFitWidth(this.getWidth()*.9);
		// ASO.fitWidthProperty().unbind();
		// ASO.fitWidthProperty().bind(this.widthProperty());
		// ASO.fitHeightProperty().bind(this.heightProperty());
		// ASO.setFitHeight(this.getHeight()*.99);
	}

	public boolean checkCanAcceptChild(AbstractSpriteObject ASO) {
	
		return checkChangeSizeIsValid(ASO, getRowIndex(), getRowIndex() + ASO.getNumCellsHeight() - 1, getColIndex(),
				getColIndex() + ASO.getNumCellsWidth() - 1);
	}

	public boolean addChild(AbstractSpriteObject ASO) {

		// ASO
		if (checkCanAcceptChild(ASO)) {
			mySO = ASO;
			this.getChildren().clear();
			setCoveredByOtherSprite(true);
			this.setCoveringSprite(mySO);
			shapeSpriteToCellSize();

			setRowSpan(mySO.getNumCellsHeight());
			setColSpan(mySO.getNumCellsWidth());
			mySO.setFitHeight(this.rowSpanProperty.get() * cellSize);
			mySO.setFitWidth(this.colSpanProperty.get() * cellSize);

			// System.out.println("Adding ASO to AMStackPane");
			this.getChildren().add(mySO);
			mySO.setWidthFunction(widthCheckValidFunction());
			mySO.setHeightFunction(heightCheckValidFunction());
			return true;
		}
		return false;
		// this.widthProperty().addListener((value) -> {
		// // ASO.setFitWidth(this.getWidth()*.99);
		// System.out.println("width changed");
		// });
		//
		// this.heightProperty().addListener((value) -> {
		// // ASO.setFitHeight(this.getHeight()*.99);
		// });

	}

	public void removeChild() {
		createDefaultShapeSpriteWidth();
		createDefaultShapeSpriteHeight();
		// int rowStart = this.getRowIndex();
		// int colStart = this.getColIndex();
		// for (int row = getRowIndex(); row < getRowIndex() + getRowSpan() - 1;
		// row++) {
		// for (int col = getColIndex(); col < getColIndex() + getColSpan() - 1;
		// col++) {
		// this.getMapLayer().getChildAtPosition(row,
		// col).setCoveredByOtherSprite(false);
		// }
		// }

		setRowSpan(1);
		setColSpan(1);
		this.setCoveredByOtherSprite(false);
		this.getChildren().clear();
	}

	// public boolean isEmpty(){
	// return this.getChildren().size();
	// }

	public boolean hasChild() {
		int size = this.getChildren().size();
		if (this.getChildren().size() == 1) {
			Node child = this.getChildren().get(0);
			if (child instanceof DefaultSpriteObject) {
				return false;
			}
			return true;
		}
		return size > 0;
	}

	// private void createRowSpanProperty(){
	//
	// }

	public void setRowSpan(int span) {
		// System.out.println("Resizing row span, " + span);
		if (span<=0){
			span=1;
		}
		getMapLayer().setRowSpan(this, span);
		rowSpanProperty.set(span);
	}

	public void setColSpan(int span) {
		// System.out.println("Resizing column span, " + span);
		if (span<=0){
			span = 1;
		}
		getMapLayer().setColumnSpan(this, span);
		colSpanProperty.set(span);
	}

	int getRowSpan() {

		return getMapLayer().getRowSpan(this);
	}

	int getColSpan() {
		return getMapLayer().getColumnSpan(this);
	}

	public int getRowIndex() {
		return getMapLayer().getRowIndex(this);
	}

	public int getColIndex() {
		return getMapLayer().getColumnIndex(this);
	}

	private int getFarRightColumn() {
		return getColIndex() + getColSpan() - 1;
	}

	private int getFarBottomRow() {
		return getRowIndex() + getRowSpan() - 1;
	}

	private boolean checkChangeRowSpanIsValid(int newRowSpan) {
		if (newRowSpan <= this.getRowSpan()) {
			return true;
		} else {
			int startRow = this.getFarBottomRow() + 1;
			int endRow = this.getRowIndex() + newRowSpan - 1;
			int startCol = this.getColIndex();
			int endCol = this.getFarRightColumn();
			return checkChangeSizeIsValid(null, startRow, endRow, startCol, endCol);
		}

	}

	private boolean checkChangeSizeIsValid(AbstractSpriteObject ASO, Integer startRow, Integer endRow, Integer startColumn, Integer endColumn) {
//System.out.println("startRow: "+startRow);
//System.out.println("endRow: "+endRow);
//System.out.println("startColumn: "+startColumn);
//System.out.println("endColumn: "+endColumn);

		for (int row = startRow; row <= endRow; row++) {
			for (int column = startColumn; column <= endColumn; column++) {
//				System.out.println("row: " + row + ", col: " + column);
				AuthoringMapStackPane newCoveredCell = getMapLayer().getChildAtPosition(row, column);
				if (newCoveredCell.isCoveredByOtherSprite()) {
					if (ASO!=null&&newCoveredCell.getCoveringSprite().equals(ASO)){
						// Nothing just keep checking cells
					} else {
//					System.out.println("row: " + row + ", col: " + column);
//					System.out.println("Cannt change size");
					return false;
					}

				}
			}
		}
//		System.out.println("Can change size");
		return true;
	}

	private boolean checkChangeColumnSpanIsValid(Integer newColumnSpan) {
//		System.out.println("Column int: " + newColumnSpan);
		if (newColumnSpan <= this.getColSpan()) {
			return true;
		} else {
			int endCol = this.getColIndex() + newColumnSpan - 1;
//			System.out.println("farRight: " + this.getFarRightColumn());
//			System.out.println("endCol: " + endCol);
			return this.checkChangeSizeIsValid(null, this.getRowIndex(), this.getFarBottomRow(), this.getFarRightColumn() + 1,
					endCol);
		}
	}

	public Function<Integer, Boolean> heightCheckValidFunction() {
		Function<Integer, Boolean> consumer = new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer t) {
				boolean ret = true;
				// System.out.println("Checking validity of height in stackpane,
				// new height: " + t);
				if (t <= 0) {
					ret = false;
					// } else if (t == 1) {
					// ret = true;
				} else if (t < getRowSpan()) {
					// int diff = getRowSpan()-t;
					// for (int newRow = 0; newRow<diff;newRow--){
					// for (int column = getColIndex(); column <=
					// getFarRightColumn(); column++) {
					// getMapLayer().getChildAtPosition(newRow, column)
					// .setCoveredByOtherSprite(false);
					// }
					// }

					ret = true;
				} else {

					ret = checkChangeRowSpanIsValid(t);

					// if (ret) {
					// for (int column = getColIndex(); column <=
					// getFarRightColumn(); column++) {
					// getMapLayer().getChildAtPosition(newRow,
					// column).setCoveredByOtherSprite(true);
					// }
					// }
				}

				if (ret) {
					setRowSpan(t);
				}
				return ret;
			}

		};
		return consumer;
	}

	public Function<Integer, Boolean> widthCheckValidFunction() {
		Function<Integer, Boolean> consumer = new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer t) {
				// System.out.println("Checking validity of width in stackpane,
				// new width: " + t);
				boolean ret = true;
				if (t <= 0) {
					ret = false;
					// } else if (t == 1) {
					// ret = true;
				} else if (t < getColSpan()) {
					// int diff = getColSpan()-t;
					// for (int newCol = 0; newCol<diff;newCol--){
					// for (int row = getRowIndex(); row <= getFarBottomRow();
					// row++) {
					// getMapLayer().getChildAtPosition(row,
					// newCol).setCoveredByOtherSprite(false);
					// }
					// }
					ret = true;
				} else {
					ret = checkChangeColumnSpanIsValid(t);

					// if (ret) {
					// for (int row = getRowIndex(); row <= getFarBottomRow();
					// row++) {
					// getMapLayer().getChildAtPosition(row,
					// newCol).setCoveredByOtherSprite(true);
					// }
					// }
				}

				if (ret) {
					setColSpan(t);
				}
				return ret;

			}

		};
		return consumer;
	}
}
