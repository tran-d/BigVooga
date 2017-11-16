package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DraggableGrid extends HBox {
	private ImageView draggingObject;
	private DataFormat objectFormat;
	
	protected DraggableGrid(int mapCount, SpriteObjectGridManagerI SOGM) {
		createGrid();
		createTrash();
		objectFormat = new DataFormat("MyObject" + Integer.toString(mapCount));
		SpriteObjectGridManagerI mySOGM = SOGM;
	}
	
	private void createGrid() {
		GridPane gp = new GridPane();
        
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                    StackPane sp = new StackPane();
                    sp.setPrefHeight(50);
                    sp.setPrefWidth(50);
                    sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT);
                    sp.setBorder(new Border(border));

                    gp.add(sp, i, j);
                    addDropHandling(sp);
            }
        }
        ScrollPane scrollGrid = new ScrollPane(gp);
        scrollGrid.setPannable(true);
        this.getChildren().add(scrollGrid);
	}
	
	private void createTrash() {
		ImageView trashCan = new ImageView(new Image("trash.png"));
		trashCan.setFitWidth(45);
	    trashCan.setFitHeight(45);
		addDropToTrash(trashCan);
		this.getChildren().add(0, trashCan);
	}
	
	
    private void addDropHandling(StackPane pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(objectFormat) && draggingObject != null) {
                e.acceptTransferModes(TransferMode.MOVE);

            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();

            if (db.hasContent(objectFormat)) {
                ((Pane)draggingObject.getParent()).getChildren().remove(draggingObject);
                pane.getChildren().add(draggingObject);
                e.setDropCompleted(true);

                draggingObject = null;
            }           
        });

    }
	
	private void addDropToTrash(ImageView trash) {
   	 
	 	trash.setOnDragOver(e -> {
	 		Dragboard db = e.getDragboard();
	 		if (db.hasContent(objectFormat) && draggingObject != null) {
	 			e.acceptTransferModes(TransferMode.MOVE);

	 		}
	 	});
	 	
	 	trash.setOnDragDropped(e -> {
	 		Dragboard db = e.getDragboard();
	 		if (db.hasContent(objectFormat)) {
	 			((Pane)draggingObject.getParent()).getChildren().remove(draggingObject);
	 			e.setDropCompleted(true);
	 			
	 			draggingObject = null;
	 		}
	 	});
	}
	
	protected void addDragObject(ImageView b) {
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
