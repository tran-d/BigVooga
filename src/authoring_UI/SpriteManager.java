package authoring_UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SpriteManager extends HBox {
	private DraggableGrid myGrid;
	
	public SpriteManager() {
		createSprites();
        	myGrid = new DraggableGrid();
	}
	
	private void createSprites() {
		 StackPane treeStack = new StackPane();
	     for (int k = 0; k < 10; k ++) {
	        	ImageView tree = new ImageView(new Image("tree.png"));
	        tree.setFitWidth(45);
	        tree.setFitHeight(45);
	        treeStack.getChildren().add(tree);
	        myGrid.addDragObject(tree);
        }
	    StackPane brickStack = new StackPane();
	    for (int n = 0; n < 10; n++) {
	    		ImageView brick = new ImageView(new Image("brick.png"));
	        	brick.setFitWidth(45);
	        	brick.setFitHeight(45);
	        	brickStack.getChildren().add(brick);
	        	myGrid.addDragObject(brick);
	    }
	    StackPane waterStack = new StackPane();
	    for (int n = 0; n < 10; n++) {
	    		ImageView water = new ImageView(new Image("water.png"));
	        water.setFitWidth(45);
	        water.setFitHeight(45);
	        waterStack.getChildren().add(water);	
	        myGrid.addDragObject(water);
	    }
	    StackPane pikaStack = new StackPane();
	    for (int m = 0; m < 10; m++) {
	        ImageView pika = new ImageView(new Image("pikachu.png"));
	        pika.setFitWidth(45);
	        pika.setFitHeight(45);
	        pikaStack.getChildren().add(pika);
	        myGrid.addDragObject(pika);
	    	}
	    
        this.getChildren().addAll(treeStack, brickStack, waterStack, pikaStack);
	}

}
