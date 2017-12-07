package authoring_UI;

import java.util.ArrayList;

import authoring.AbstractSpriteObject;
import authoring.InventoryObject;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpriteGridHandler {
	private AbstractSpriteObject draggingObject;
	private DataFormat objectFormat;
	// private SpriteObjectGridManagerI mySOGM;
	private DisplayPanel myDP;
	private ArrayList<AuthoringMapStackPane> activeGridCells;
	// private ArrayList<StackPane> activeSpriteGridCells;
//	private ArrayList<AbstractSpriteObject> activeSpriteGridCells;
	private DraggableGrid myDG;
	// private GridPane myGrid;

	protected SpriteGridHandler(int mapCount, DraggableGrid DG) {
		objectFormat = new DataFormat("MyObject" + Integer.toString(mapCount));
		// mySOGM = SOGM;
		myDG = DG;
//		activeSpriteGridCells = new ArrayList<AbstractSpriteObject>();
		activeGridCells = new ArrayList<AuthoringMapStackPane>();
		// activeSpriteGridCells = new ArrayList<StackPane>();
	}

	public void setDisplayPanel(SpritePanels spritePanels) {
		myDP = spritePanels.getDisplayPanel();
	}

	// protected void addGrid(GridPane grid) {
	// myGrid = grid;
	// }

	protected void addKeyPress(Scene scene) {
		
		scene.setOnKeyPressed(e -> {
//			System.out.println("Key pressed: "+ e.getCode());
			if (e.getCode().equals(KeyCode.BACK_SPACE)) {
				deleteSelectedSprites();
			}
		});
	}

	private void deleteSelectedSprites() {
		ArrayList<Integer[]> cellsToDelete = new ArrayList<Integer[]>();
		myDG.getActiveGrid().getActiveSpriteObjects().forEach(s -> {
			Integer[] row_col = s.getPositionOnGrid();
//			System.out.println("row_col: " + row_col);
			cellsToDelete.add(row_col);
		});
		deactivateActiveSprites();
		myDP.removeSpriteEditorVBox();
		myDG.getActiveGrid().clearCells(cellsToDelete);

		// mySOGM.removeActiveCells(cellsToDelete);
	}

	private void deactivateActiveSprites() {
		// this.activeSpriteGridCells.clear();
//		activeSpriteGridCells.forEach(spriteCell -> {
//			((AuthoringMapStackPane) spriteCell.getParent()).removeChild();
//		});
//		myDG.getActiveGrid()
		myDG.getActiveGrid().resetActiveCells();
	}

	protected void addGridMouseClick(AuthoringMapStackPane pane) {
		pane.setOnMouseClicked(e -> {
			if (!pane.hasChild()){
				if (!pane.isCoveredByOtherSprite()){
					changeCellStatus(pane);
				} else if (pane.isCoveredByOtherSprite()){
					Event.fireEvent(pane.getCoveringSprite(), new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
			                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
			                true, true, true, true, true, true, null));
				}
				
			}
		});
	}

	protected void addGridMouseDrag(AuthoringMapStackPane pane) {
		pane.setOnMouseDragEntered(e -> {
			if (pane.getChildren().size() == 0)
				changeCellStatus(pane);
			System.out.println("ENTERED BY MOUSE DRAG");
		});
	}

	private void changeCellStatus(AuthoringMapStackPane pane) {
		pane.switchActive();
		if (pane.isActive()) {
			activeGridCells.add(pane);
		} else {
			activeGridCells.remove(pane);
		}
		// if(pane.getOpacity() == 1) {
		// makeCellActive(pane);
		// } else {
		// makeCellInactive(pane);
		// }
	}

	// private void makeCellActive(AuthoringMapStackPane pane) {
	// activeGridCells.add(pane);
	// pane.switch
	// pane.setOpacity(0.5);
	// }

	// private void makeCellInactive(StackPane pane) {
	// activeGridCells.remove(pane);
	// pane.setOpacity(1);
	// }

	protected void addSpriteMouseClick(AbstractSpriteObject s) {
		s.setOnMouseClicked(e -> {
			System.out.println("I clicked sprite : " + s);
			
			
			
			if (s instanceof SpriteObject) {
				
				if (this.activeGridCells.size()>0){
					populateGridCells((SpriteObject) s);
					removeActiveCells();
				} else {
				boolean activeStatus;
				if (s.getPositionOnGrid() != null) {
					activeStatus = myDG.getActiveGrid().switchCellActiveStatus(s.getPositionOnGrid());
					if (activeStatus) {
						s.setEffect(makeSpriteEffect());
						// activeSpriteGridCells.add((StackPane) s.getParent());
//						activeSpriteGridCells.add(s);
						myDG.getActiveGrid().addActiveCell(s);
					} else {
//						 s.clearPossibleParameters();
						s.setEffect(null);
						// activeSpriteGridCells.remove((StackPane)
						// s.getParent());
						myDG.getActiveGrid().removeActiveCell(s);
//						activeSpriteGridCells.remove(s);

//						myDP.removeSpriteEditorVBox();
					}

					if (myDG.getActiveGrid().getActiveSpriteObjects().size() == 0) {
						myDP.removeSpriteEditorVBox();
					} else {
						myDP.addSpriteEditorVBox();
						myDP.updateParameterTab();
					}
				} }
//				else {
//					populateGridCells((SpriteObject) s);
//					removeActiveCells();
//				}
			} else if (s instanceof InventoryObject) {
				// TODO: what if it is an inventory object?
			}
		});
	}

	private Effect makeSpriteEffect() {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(5.0);
		dropShadow.setOffsetY(5.0);
		dropShadow.setColor(Color.GREY);
		Glow glow = new Glow(0.5);

		return glow;
	}

	public void removeActiveCells() {
//		System.out.println("RMEOVING ACTIVE CELLS");
		// activeGridCells.forEach(e->{
		// System.out.println(this.getStackPanePositionInGrid(e));
		//
		// e.setEffect(new GaussianBlur());
		// });
		myDG.getActiveGrid().getActiveSpriteObjects().forEach(sprite -> {
			sprite.setEffect(null);
		});
		this.deactivateActiveSprites();
//		this.myDG.getActiveGrid().resetActiveCells();

		myDP.removeSpriteEditorVBox();

	}
	
	public void makeCellInactive(){
		
	}

	public void addSpriteSizeChangeListener() {

	}

	private void populateGridCells(SpriteObject s) {
		activeGridCells.forEach(cell -> {

//			System.out.println("populating from SGH");
			SpriteObject SO = s.newCopy();
			// cell.setOpacity(1);
			// cell.getChildren().clear();
			// cell.getChildren().add(SO);
			cell.addChild(SO);
			cell.setInactive();
			Integer[] cellPos = getStackPanePositionInGrid(cell);
			myDG.getActiveGrid().populateCell(SO, cellPos);
			SO.setPositionOnGrid(cellPos);
			addSpriteMouseClick(SO);
			addSpriteDrag(SO);			
		});
		activeGridCells.clear();
	}

	private Integer[] getStackPanePositionInGrid(AuthoringMapStackPane pane) {
		int row = pane.getRowIndex();
		int col = pane.getColIndex();
		// System.out.println("getStackPanePos: "+row+", " + col);
		Integer[] row_col = new Integer[] { row, col };
		return row_col;
	}

	private void updateGridPane() {
		myDG.getActiveGrid().getGrid();
	}

	protected void addDropHandling(AuthoringMapStackPane pane) {
		pane.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if (db.hasContent(objectFormat) && draggingObject != null) {
				e.acceptTransferModes(TransferMode.MOVE);

			}
		});

		pane.setOnDragDropped(e -> {
			if (!pane.isCoveredByOtherSprite()) {
				Dragboard db = e.getDragboard();
				MapLayer ML = pane.getMapLayer();
//				System.out.println("MapLayer: " + ML.getName());
				int row = ML.getRowIndex(pane);
				int col = ML.getColumnIndex(pane);
				Integer[] row_col = new Integer[] { row, col };
//				System.out.println(row_col);

				if (db.hasContent(objectFormat)) {
					if (draggingObject instanceof SpriteObject) {

						if (!(draggingObject.getParent() instanceof AuthoringMapStackPane)) {
							StackPane SP = (StackPane) draggingObject.getParent();
//							GridPane GP = (GridPane) SP.getParent();
//							int spriteRow = GP.getRowIndex(SP);
//							int spriteCol = GP.getColumnIndex(SP);
							SP.getChildren().clear();
							AbstractSpriteObject SO = draggingObject.newCopy();
							this.addSpriteDrag(SO);
							this.addSpriteMouseClick(SO);
							SP.getChildren().add(SO);
							
							
//							SpriteSelectPanel spritePanel = (SpriteSelectPanel) draggingObject.getParent();
//							spritePanel.addNewDefaultSprite(draggingObject, spriteLocation);
//							((Pane) draggingObject.getParent()).getChildren().remove(draggingObject);
						} else if (draggingObject.getParent() instanceof AuthoringMapStackPane) {
							((AuthoringMapStackPane) draggingObject.getParent()).removeChild();
						}

						myDG.getActiveGrid().populateCell((SpriteObject) draggingObject, row_col);
						draggingObject.setPositionOnGrid(row_col);
						// gets locations of sprite in pane
					} else if (draggingObject instanceof InventoryObject) {
						// TODO: What if the dragged sprite is inventory?
					}

					pane.addChild(draggingObject);
					e.setDropCompleted(true);
					draggingObject = null;
				}
			}
		});

	}

	// protected void addDropToTrash(ImageView trash) {
	//
	// trash.setOnDragOver(e -> {
	// Dragboard db = e.getDragboard();
	// if (db.hasContent(objectFormat) && draggingObject != null) {
	// e.acceptTransferModes(TransferMode.MOVE);
	//
	// }
	// });
	//
	// trash.setOnDragDropped(e -> {
	// Dragboard db = e.getDragboard();
	// ArrayList<SpriteObject> byeSprites = new ArrayList<SpriteObject>();
	// byeSprites.add(draggingObject);
	// // clear sprites
	// // mySOGM.clearCells(byeSprites);
	//
	// if (db.hasContent(objectFormat)) {
	// ((Pane) draggingObject.getParent()).getChildren().remove(draggingObject);
	// e.setDropCompleted(true);
	//
	// draggingObject = null;
	// }
	// });
	// }

	protected void addSpriteDrag(AbstractSpriteObject s) {
		s.setOnDragDetected(e -> {
			Dragboard db = s.startDragAndDrop(TransferMode.MOVE);

			db.setDragView(s.snapshot(null, null));
			ClipboardContent cc = new ClipboardContent();
			cc.put(objectFormat, " ");
			db.setContent(cc);
			draggingObject = s;
		});
	}
}
