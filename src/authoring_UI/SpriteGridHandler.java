package authoring_UI;

import java.util.ArrayList;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SpriteGridHandler {
	private SpriteObject draggingObject;
	private DataFormat objectFormat;
	private SpriteObjectGridManagerI mySOGM;
	private Menu myMenu;
	
	protected SpriteGridHandler(int mapCount, Menu menu, SpriteObjectGridManagerI SOGM) {
		objectFormat = new DataFormat("MyObject" + Integer.toString(mapCount));
		mySOGM = SOGM;
		myMenu = menu;
	}
	

	void addMouseClick(StackPane pane) {
		pane.setOnMouseClicked(e -> {

			boolean activeStatus;
			activeStatus = mySOGM.switchCellActiveStatus(getStackPanePositionInGrid(pane));
			if (activeStatus) {
				pane.setOpacity(.5);
			} else {
				pane.setOpacity(1);
			}
			myMenu.updateParameterTab();

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
			//
			Integer[] row_col = new Integer[] { row, col };
			// ArrayList<Integer[]> activeCells = new ArrayList<Integer[]>();
			// activeCells.add(row_col);

			// mySOGM.addActiveCells(activeCells);

			// myMenu.displayParams();

			if (db.hasContent(objectFormat)) {
				mySOGM.populateCell(draggingObject, row_col);
//				System.out.println("has object format??");
//				System.out.println("Parent size: " + ((Pane) draggingObject.getParent()).getChildren().size());
//				System.out.println("Parent: " + ((Pane) draggingObject.getParent()));
				

				if (draggingObject.getParent() instanceof SpriteSelectPanel) {
					SpriteSelectPanel spritePanel = (SpriteSelectPanel) draggingObject.getParent();
					spritePanel.addNewDefaultSprite(draggingObject);
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

	protected void addDragObject(SpriteObject b) {
		b.setOnDragDetected(e -> {
			Dragboard db = b.startDragAndDrop(TransferMode.MOVE);

			db.setDragView(b.snapshot(null, null));
			ClipboardContent cc = new ClipboardContent();
			cc.put(objectFormat, " ");
			db.setContent(cc);
			draggingObject = b;
		});
	}
}
