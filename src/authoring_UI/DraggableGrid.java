package authoring_UI;

import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class DraggableGrid {
	private ImageView draggingObject;
	private final DataFormat objectFormat = new DataFormat("MyObject");

	
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
