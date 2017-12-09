package authoring_UI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import authoring.AbstractSpriteObject;
import authoring.InventoryObject;
import authoring.SpriteObject;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpriteCreatorGridHandler {
	private AbstractSpriteObject draggingObject;
	private DataFormat objectFormat;
	private DisplayPanel myDP;
	private DraggableGrid myDG;

	public SpriteCreatorGridHandler(int mapCount, DraggableGrid DG) {
//		objectFormat = new DataFormat("MyObject" + Integer.toString(mapCount));
//		myDG = DG;
	}

	public SpriteCreatorGridHandler(int mapCount, DraggableGrid DG, int sprite) {
		objectFormat = new DataFormat("MySprite" + Integer.toString(mapCount));
		myDG = DG;
	}

	public void setDisplayPanel(SpritePanels spritePanels) {
		myDP = spritePanels.getDisplayPanel();
	}

	public DraggableGrid getDraggableGrid() {
		return myDG;
	}

	protected void addKeyPress(Scene scene) {
		scene.setOnKeyPressed(e -> {
			System.out.println("Key pressed: " + e.getCode());
			if (e.getCode().equals(KeyCode.BACK_SPACE)) {
				deleteSelectedSprites();
			}
		});
	}

	private void deleteSelectedSprites() {
		List<Integer[]> cellsToDelete = new ArrayList<Integer[]>();
		myDG.getActiveGrid().getActiveSpriteObjects().forEach(s -> {
			Integer[] row_col = s.getPositionOnGrid();
			System.out.println("row_col: " + row_col);
			cellsToDelete.add(row_col);
		});
		resetActiveSprites();
		myDP.removeSpriteEditorVBox();

		System.out.println();
		myDG.getActiveGrid().clearCells(cellsToDelete);
	}

	private void resetActiveSprites() {
		myDG.getActiveGrid().resetActiveCells();
	}

	protected void addGridMouseClick(AuthoringMapStackPane pane) {
		pane.setOnMouseClicked(e -> {
			if (!pane.hasChild()) {
				if (!pane.isCoveredByOtherSprite()) {
					changeCellStatus(pane);
				} else if (pane.isCoveredByOtherSprite()) {
					// pane.se
					Event.fireEvent(pane.getCoveringSprite(), new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
							MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null));
				}
			}
		});
	}

	protected void addGridMouseDrag(AuthoringMapStackPane pane) {
		// pane.setOnMouseDragged(e -> {
		// if (pane.isCoveredByOtherSprite()){
		//// Event.fireEvent(pane.getCoveringSprite(), new MouseEvent(MouseEvent.));
		//// pane.getCoveringSprite()
		// Event.fireEvent(pane.getCoveringSprite(), new
		// MouseEvent(MouseEvent.DRAG_DETECTED, 0,
		// 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
		// true, true, true, true, true, true, null));
		// } else {
		// pane.switchActive();
		// }
		// });
		// pane.setOnMouseDragOver(e->{
		// System.out.println("Dragged over");
		// pane.switchActive();
		// });

		// pane.setOnDragOver(event->{
		// System.out.println("Dragover");
		// pane.switchActive();
		//
		// });

		// pane.setOnKeyTyped(value->{
		// System.out.println(value.getCode());
		// });

		// pane.setOnMouseDragEntered(e->{
		// System.out.println("Drag entered");
		// pane.switchActive();
		// });

		// pane.setOnDragDetected(event->{
		// Dragboard db = pane.startDragAndDrop(TransferMode.MOVE);
		// System.out.println("DragDetected");
		// });
	}

	private void changeCellStatus(AuthoringMapStackPane pane) {
		pane.switchActive();
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

	public void addSpriteMouseClick(AbstractSpriteObject s) {
		s.setOnMouseClicked(e -> {
			System.out.println("I clicked sprite : " + s);
			if (s instanceof SpriteObject) {
				if (myDG.getActiveGrid().getMapLayer().getActive().size() > 0) {
					populateGridCells((SpriteObject) s);
				} else {
					boolean activeStatus;
					if (s.getPositionOnGrid() != null) {
						activeStatus = myDG.getActiveGrid().switchCellActiveStatus(s.getPositionOnGrid());
						if (activeStatus) {
							s.setEffect(makeSpriteEffect());
							myDG.getActiveGrid().addActiveCell(s);
						} else {
							s.setEffect(null);
							myDG.getActiveGrid().removeActiveCell(s);
						}

						if (myDG.getActiveGrid().getActiveSpriteObjects().size() == 0) {
							myDP.removeSpriteEditorVBox();
						} else {
							myDP.addSpriteEditorVBox();
							myDP.updateParameterTab();
						}
					}
				}
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

	public void deactivateActiveSprites() {
		System.out.println("RMEOVING ACTIVE CELLS");
		myDG.getActiveGrid().getActiveSpriteObjects().forEach(sprite -> {
			sprite.setEffect(null);
		});
		this.resetActiveSprites();
		// this.myDG.getActiveGrid().resetActiveCells();

		myDP.removeSpriteEditorVBox();
	}

	// public void makeCellInactive(){
	//
	// }
	//
	// public void addSpriteSizeChangeListener() {
	//
	// }

	private void populateGridCells(SpriteObject s) {
		Iterator<AuthoringMapStackPane> it = myDG.getActiveGrid().getMapLayer().getActive().iterator();
		Set<AuthoringMapStackPane> currentActiveCells = new HashSet<AuthoringMapStackPane>();
		myDG.getActiveGrid().getMapLayer().getActive().forEach((item) -> {
			currentActiveCells.add(item);
		});

		currentActiveCells.forEach((item) -> {
			populateIndividualCell(item, s);
		});
		// Set<AuthoringMapStackPane> currentActiveCells = new
		// HashSet<AuthoringMapStackPane>()
		// while (it.hasNext()){
		// AuthoringMapStackPane elem = it.next();
		// if (){
		// it.remove();
		// }

		deactivateActiveSprites();

	}

	private boolean populateIndividualCell(AuthoringMapStackPane cell, SpriteObject s) {
		System.out.println("populating from SGH");
		SpriteObject SO = s.newCopy();
		if (cell.addChild(SO)) {
			cell.setInactive();
			Integer[] cellPos = getStackPanePositionInGrid(cell);
			myDG.getActiveGrid().populateCell(SO, cellPos);
			SO.setPositionOnGrid(cellPos);
			addSpriteMouseClick(SO);
//			addSpriteDrag(SO);
			return true;
		}
		return false;
	}

	private Integer[] getStackPanePositionInGrid(AuthoringMapStackPane pane) {
		int row = pane.getRowIndex();
		int col = pane.getColIndex();
		Integer[] row_col = new Integer[] { row, col };
		return row_col;
	}

	protected void addDropHandling(AuthoringMapStackPane pane) {
		pane.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if (db.hasContent(objectFormat) && draggingObject != null) {
				e.acceptTransferModes(TransferMode.MOVE);
			}
		});

		pane.setOnDragDropped(e -> {
			if (pane.checkCanAcceptChild(draggingObject)) {
				Dragboard db = e.getDragboard();
				MapLayer ML = pane.getMapLayer();
				System.out.println("MapLayer: " + ML.getName());
				int row = ML.getRowIndex(pane);
				int col = ML.getColumnIndex(pane);
				Integer[] row_col = new Integer[] { row, col };
				System.out.println(row_col);

				if (db.hasContent(objectFormat)) {
					if (draggingObject instanceof SpriteObject) {
						if (!(draggingObject.getParent() instanceof AuthoringMapStackPane)) {
							StackPane SP = (StackPane) draggingObject.getParent();
							SP.getChildren().clear();
							AbstractSpriteObject SO = draggingObject.newCopy();
//							this.addSpriteDrag(SO);
							this.addSpriteMouseClick(SO);
							SP.getChildren().add(SO);
						} else if (draggingObject.getParent() instanceof AuthoringMapStackPane) {
							((AuthoringMapStackPane) draggingObject.getParent()).removeChild();
						}

						myDG.getActiveGrid().populateCell((SpriteObject) draggingObject, row_col);
						draggingObject.setPositionOnGrid(row_col);
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

//	public void addSpriteDrag(AbstractSpriteObject s) {
//		s.setOnDragDetected(e -> {
//			if (!myDG.getActiveGrid().getActiveSpriteObjects().contains(s)) {
//				Dragboard db = s.startDragAndDrop(TransferMode.MOVE);
//				db.setDragView(s.snapshot(null, null));
//				ClipboardContent cc = new ClipboardContent();
//				cc.put(objectFormat, " ");
//				db.setContent(cc);
//				draggingObject = s;
//			}
//		});
//	}
}
