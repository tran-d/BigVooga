package authoring_UI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.Sprite.SpriteObject;
import authoring.SpritePanels.DisplayPanel;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.Map.MapLayer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpriteGridHandler {
	private AbstractSpriteObject draggingObject;
	private DataFormat objectFormat;
	private DisplayPanel myGridDP;
	private DisplayPanel myElementSelectorDP;
	private DraggableGrid myDG;
	protected boolean gridIsShown;
	private boolean shiftDown;
	
	public SpriteGridHandler(int mapCount, DraggableGrid DG) {
		Random rand = new Random();
		int parent = rand.nextInt();
		objectFormat = new DataFormat("MyObject" + parent + Integer.toString(mapCount));
		System.out.println("SGH made with objForm: " + objectFormat);
		myDG = DG;
	}

	public void setGridIsShown(boolean shown) {
		System.out.println("Grid is shown (" + shown + ") for " + myDG.getActiveGrid().getClass());
		this.gridIsShown = shown;

	}

	public SpriteGridHandler(int mapCount, DraggableGrid DG, int sprite) {
		objectFormat = new DataFormat("MySprite" + Integer.toString(mapCount));
		myDG = DG;
	}

	public void setSpritePanel(SpritePanels spritePanels) {
		setGridDisplayPanel(spritePanels.getDisplayPanel());
		setElementSelectorDisplayPanel(spritePanels.getElementSelectorDisplayPanel());
	}
	
	public void setGridDisplayPanel(DisplayPanel dispPanel) {
		myGridDP = dispPanel;
	}
	
	public void setElementSelectorDisplayPanel(DisplayPanel dispPanel) {
		myElementSelectorDP = dispPanel;
		System.out.println("myElemSelectorDP is "+myElementSelectorDP);
	}

	public DraggableGrid getDraggableGrid() {
		return myDG;
	}

	public void addKeyPress(Scene scene) {
		System.out.println("Added key press");
		scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()){
				case SHIFT:
					if(gridIsShown){
					shiftDown = false;
					}
					break;
				default:
					//Nothing
					break;
				}
				
			}
			
		});
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent e) {
				System.out.println("handling a key??: " + e.getCode());

				switch (e.getCode()) {
				case BACK_SPACE:
					onBackSpace();
					break;
				case SHIFT:
					if (gridIsShown){
					shiftDown = true;
					}
					break;
				case C:
					deactivateActiveAuthoringMapStackPaneCells();
					break;
				case L:
					clearLayerOfSprites();
					break;
				case W:
					onTop();
					break;
				case D:
					onRight();
					break;
				case A:
					onLeft();
					break;
				case S:
					onBottom();
					break;
				case Q:
					onNW();
					break;
				case E:
					onNE();
					break;
				case Z:
					onSW();
					break;
				case X:
					onSE();
					break;
				default:
					// nothing
					break;
				}
			}
		});
	}

	private void onBackSpace() {
		System.out.println("In if statement");
		if (gridIsShown) {
			System.out.println("Grid shown for ");
			System.out.println("Class " + myDG.getActiveGrid().getClass());
			deleteSelectedSprites();
		}
	}

	private void onRight() {
		activateNeighbor(0, 1);
	}

	private void onLeft() {
		activateNeighbor(0, -1);
	}

	private void onTop() {
		activateNeighbor(-1, 0);
	}

	private void onBottom() {
		activateNeighbor(1, 0);
	}

	private void onNE() {
		activateNeighbor(-1, 1);
	}

	private void onSE() {
		activateNeighbor(1, 1);
	}

	private void onNW() {
		activateNeighbor(-1, -1);
	}

	private void onSW() {
		activateNeighbor(1, -1);
	}

	private void activateNeighbor(int rowChange, int colChange) {
		if (gridIsShown) {
			MapLayer ML = myDG.getActiveGrid().getMapLayer();
			Set<AuthoringMapStackPane> activeSet  = new HashSet<AuthoringMapStackPane>();
			if (shiftDown){
				activeSet.addAll(ML.getActive());
			} else {
				activeSet.addAll(ML.getMostRecentActive());
			}
			Set<AuthoringMapStackPane> newMostRecent  = new HashSet<AuthoringMapStackPane>();
			activeSet.forEach(activeCell->{
				int row = activeCell.getRowIndex();
				int col = activeCell.getColIndex();
				AuthoringMapStackPane Neighbor = ML.getChildAtPosition(row + rowChange, col + colChange);
				if (Neighbor != null) {
					Neighbor.setActive();
					if (ML.getMostRecentActive().contains(activeCell)){
					newMostRecent.add(Neighbor);
					}
				}
			});
			ML.setMostRecentActive(newMostRecent);
		}
	}
	
	private void clearLayerOfSprites(){
		deleteSprites(myDG.getActiveGrid().getEntireListOfSpriteObjects());
	}

	private void deleteSelectedSprites() {
		deleteSprites(myDG.getActiveGrid().getActiveSpriteObjects());
		resetActiveSprites();
		myGridDP.removeSpriteEditorVBox();
		
	}
	
	private void deleteSprites(List<AbstractSpriteObject> toDelete){
		List<Integer[]> cellsToDelete = new ArrayList<Integer[]>();
		toDelete.forEach(s -> {
			System.out.println("In the lambda");
			Integer[] row_col = s.getPositionOnGrid();
			System.out.println(row_col);
			// System.out.println("row_col: " + row_col);
			cellsToDelete.add(row_col);
		});
		System.out.println(myDG.getActiveGrid().getActiveSpriteObjects().size());
		myDG.getActiveGrid().clearCells(cellsToDelete);
	}

private	void resetActiveSprites() {
	System.out.println("resetting activeCells");
		myDG.getActiveGrid().resetActiveCells();
	}

	public void addGridMouseClick(AuthoringMapStackPane pane) {
		pane.setOnMouseClicked(e -> {
			if (!pane.hasChild()) {
				if (!pane.isCoveredByOtherSprite()) {
					changeCellStatus(pane);
					if (pane.isActive()){
						myDG.getActiveGrid().getMapLayer().addMostRecentActive(pane);
					}
				} else if (pane.isCoveredByOtherSprite()) {
					// pane.se
					Event.fireEvent(pane.getCoveringSprite(), new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
							MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null));
				}
			}
		});
	}

	public void addGridMouseDrag(AuthoringMapStackPane pane) {
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
				
				if (s.getPositionOnGrid() != null) {
					setActiveElementSprite(null);
					boolean activeStatus;
					activeStatus = myDG.getActiveGrid().switchCellActiveStatus(s.getPositionOnGrid());
					System.out.println("Cell is active? "+ activeStatus);
					if (activeStatus) {
						s.setEffect(makeSpriteEffect());
					} else {
						s.setEffect(null);
					}
					System.out.println("In addSpriteMClick: numActiveCells: "+myDG.getActiveGrid().getActiveSpriteObjects().size());

						if (myDG.getActiveGrid().getActiveSpriteObjects().size() == 0) {
							myGridDP.removeSpriteEditorVBox();
						} else {
							myGridDP.addSpriteEditorVBox();
							myGridDP.updateParameterTab();
						}
					}
				else {
					this.deactivateActiveSprites();
					setActiveElementSprite(s);
				} 
				
				}
			} else if (s instanceof InventoryObject) {
				// TODO: what if it is an inventory object?
			}
		});
	}
	
	private void setActiveElementSprite(AbstractSpriteObject ASO){
		System.out.println("inSetActiveElemSprite elemSelectorDP value is: "+this.myElementSelectorDP);
		System.out.println("inSetActiveElemSprite sprite value is: "+ASO);
		AbstractSpriteObject prevActive = this.myElementSelectorDP.setActiveSprite(ASO);
		if (ASO!=null){
			System.out.println("prevActive: "+prevActive);
			if (ASO.equals(prevActive)){
				ASO.setEffect(null);
			}
			ASO.setEffect(makeSpriteEffect());
		}
		if (prevActive!=null){
			prevActive.setEffect(null);
		}
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

		myGridDP.removeSpriteEditorVBox();
	}
	
	public void deactivateActiveAuthoringMapStackPaneCells(){
		myDG.getActiveGrid().getMapLayer().removeAllActive();
	}
	
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
			addSpriteDrag(SO);
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

	public void addDropHandling(AuthoringMapStackPane pane) {
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
				// System.out.println("MapLayer: " + ML.getName());
				int row = ML.getRowIndex(pane);
				int col = ML.getColumnIndex(pane);
				Integer[] row_col = new Integer[] { row, col };
				// System.out.println(row_col);

				if (db.hasContent(objectFormat)) {
					if (draggingObject instanceof SpriteObject) {
						if (!(draggingObject.getParent() instanceof AuthoringMapStackPane)) {
							StackPane SP = (StackPane) draggingObject.getParent();
							SP.getChildren().clear();
							AbstractSpriteObject SO = draggingObject.newCopy();
							this.addSpriteDrag(SO);
							this.addSpriteMouseClick(SO);
							SP.getChildren().add(SO);
						} else if (draggingObject.getParent() instanceof AuthoringMapStackPane) {
							((AuthoringMapStackPane) draggingObject.getParent()).removeChild();
						}

						myDG.getActiveGrid().populateCell((SpriteObject) draggingObject, row_col);
						draggingObject.setPositionOnGrid(row_col);
						pane.addChild(draggingObject);
					} else if (draggingObject instanceof InventoryObject) {
						if (pane.hasChild()){
						pane.getChild().addToInventory(draggingObject);
						}
					}
					
					e.setDropCompleted(true);
					draggingObject = null;
				}
			}
		});
	}

	public void addSpriteDrag(AbstractSpriteObject s) {
		s.setOnDragDetected(e -> {
			if (!myDG.getActiveGrid().getActiveSpriteObjects().contains(s)) {
				Dragboard db = s.startDragAndDrop(TransferMode.MOVE);
				db.setDragView(s.snapshot(null, null));
				ClipboardContent cc = new ClipboardContent();
				cc.put(objectFormat, " ");
				db.setContent(cc);
				draggingObject = s;
			}
		});
	}
}