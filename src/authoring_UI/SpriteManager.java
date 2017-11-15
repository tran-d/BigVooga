package authoring_UI;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SpriteManager extends TabPane {
	private DraggableGrid myGrid;
	private HBox mySprites;
	
	protected SpriteManager() {
		createSprites();
        	myGrid = new DraggableGrid();
        	createSpriteTabs();
	}
	
	private void createSprites() {
		createImageStack("tree.png");
		createImageStack("brick.png");
		createImageStack("water.png");
		createImageStack("pikachu.png");	    
	}
	
	private void createImageStack(String imageName) {
		StackPane imageStack = new StackPane();
		for (int k = 0; k < 10; k ++) {
			ImageView image = new ImageView(new Image(imageName));
			image.setFitWidth(45);
			image.setFitHeight(45);
			imageStack.getChildren().add(image);
			myGrid.addDragObject(image);
		}
		mySprites.getChildren().add(imageStack);
	}
	
    private void createSpriteTabs() {
		Tab defaultSpriteTab = new Tab();
		defaultSpriteTab.setText("Default Sprites");
		defaultSpriteTab.setContent(mySprites);
     
		Tab mySpriteTab = new Tab();
		mySpriteTab.setText("User Sprites");
     
		this.getTabs().addAll(defaultSpriteTab, mySpriteTab);

    }

}
