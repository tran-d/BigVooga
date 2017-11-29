package authoring_UI;

import java.util.ArrayList;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpriteGridHandler {
	private SpriteObject draggingObject;
	private DataFormat objectFormat;
	private SpriteObjectGridManagerI mySOGM;
	private Menu myMenu;
	private ArrayList<StackPane> activeGridCells;
	
	protected SpriteGridHandler(int mapCount, Menu menu, SpriteObjectGridManagerI SOGM) {
		objectFormat = new DataFormat("MyObject" + Integer.toString(mapCount));
		mySOGM = SOGM;
		myMenu = menu;
		activeGridCells = new ArrayList<StackPane>();
	}
	
	protected void addKeyPress(Scene scene) {
		scene.setOnKeyPressed(e -> { 
			if (e.getCode().equals(KeyCode.DELETE)) {
				deleteSelectedSprites();
				System.out.println("delete pressed");
			}
		});
	}
	
	private void deleteSelectedSprites() {
		System.out.println("trying to delete");
		ArrayList<Integer[]> cellsToDelete = new ArrayList<Integer[]>();
		mySOGM.getActiveSpriteObjects().forEach(s -> {
			((Pane) s.getParent()).getChildren().remove(s);
			int row = ((GridPane) s.getParent()).getRowIndex(s);
			int col = ((GridPane) s.getParent()).getColumnIndex(s);
			Integer[] row_col = new Integer[] { row, col };
			cellsToDelete.add(row_col);
		});
		mySOGM.clearCells(cellsToDelete);

	}
	
	protected void addGridMouseClick(StackPane pane) {
		pane.setOnMouseClicked(e -> {
			if (pane.getChildren().size() == 0) changeCellStatus(pane);
		});		
	}
	
	private void changeCellStatus(StackPane pane) {
		if(pane.getOpacity() == 1) {
			makeCellActive(pane);
		} else {
			makeCellInactive(pane);
		}
	}
	
	private void makeCellActive(StackPane pane) {
		activeGridCells.add(pane);
		pane.setOpacity(0.5);
	}
	
	private void makeCellInactive(StackPane pane) {
		activeGridCells.remove(pane);
		pane.setOpacity(1);
	}
	

	protected void addSpriteMouseClick(SpriteObject s) {
		s.setOnMouseClicked(e -> {
			
			boolean activeStatus;
			if (s.getPositionOnGrid() != null) {
				activeStatus = mySOGM.switchCellActiveStatus(s.getPositionOnGrid());
				if (activeStatus) {
					s.setEffect(makeSpriteEffect());
				} else {
					s.setEffect(null);
				}
				myMenu.updateParameterTab();
			} else {
				populateGridCells(s);
				removeActiveCells();
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
	
	private void removeActiveCells() {
		activeGridCells.clear();
	}
	
	private void populateGridCells(SpriteObject s) {
		activeGridCells.forEach(cell -> {
			SpriteObject SO = s.newCopy();
			cell.setOpacity(1);
			cell.getChildren().add(SO);
			Integer[] cellPos = getStackPanePositionInGrid(cell);
			mySOGM.populateCell(SO, cellPos);
			SO.setPositionOnGrid(cellPos);
			addSpriteMouseClick(SO);
		});
	}

	private Integer[] getStackPanePositionInGrid(StackPane pane) {
		int row = ((GridPane) pane.getParent()).getRowIndex(pane);
		int col = ((GridPane) pane.getParent()).getColumnIndex(pane);
		Integer[] row_col = new Integer[] { row, col };
		return row_col;
	}

	private void updateGridPane() {
		mySOGM.getGrid();
	}

	protected void addDropHandling(StackPane pane) {
		pane.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if (db.hasContent(objectFormat) && draggingObject != null) {
				e.acceptTransferModes(TransferMode.MOVE);

			}
		});

		pane.setOnDragDropped(e -> {
			Dragboard db = e.getDragboard();
			int row = ((GridPane) pane.getParent()).getRowIndex(pane);
			int col = ((GridPane) pane.getParent()).getColumnIndex(pane);
			Integer[] row_col = new Integer[] { row, col };

			if (db.hasContent(objectFormat)) {
				mySOGM.populateCell(draggingObject, row_col);
				draggingObject.setPositionOnGrid(row_col);
				// gets locations of sprite in pane
				int spriteLocation = ((Pane)draggingObject.getParent()).getChildren().indexOf(draggingObject);
				
				if (draggingObject.getParent() instanceof SpriteSelectPanel) {
					SpriteSelectPanel spritePanel = (SpriteSelectPanel) draggingObject.getParent();
					spritePanel.addNewDefaultSprite(draggingObject, spriteLocation);
				}
				((Pane) draggingObject.getParent()).getChildren().remove(draggingObject);
				pane.getChildren().add(draggingObject);
				e.setDropCompleted(true);
				draggingObject = null;
			}
		});

	}

	protected void addDropToTrash(ImageView trash) {

		trash.setOnDragOver(e -> {
			Dragboard db = e.getDragboard();
			if (db.hasContent(objectFormat) && draggingObject != null) {
				e.acceptTransferModes(TransferMode.MOVE);

			}
		});

		trash.setOnDragDropped(e -> {
			Dragboard db = e.getDragboard();
			ArrayList<SpriteObject> byeSprites = new ArrayList<SpriteObject>();
			byeSprites.add(draggingObject);
			// clear sprites
			// mySOGM.clearCells(byeSprites);

			if (db.hasContent(objectFormat)) {
				((Pane) draggingObject.getParent()).getChildren().remove(draggingObject);
				e.setDropCompleted(true);

				draggingObject = null;
			}
		});
	}

	protected void addDragObject(SpriteObject s) {
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
