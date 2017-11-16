package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteObjectI;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SpriteManager extends TabPane {
	private DraggableGrid myGrid;
	private VBox mySprites;
	private AuthoringEnvironmentManager myAEM;
	
	protected SpriteManager(DraggableGrid grid, AuthoringEnvironmentManager AEM) {
		myGrid = grid;
		mySprites = new VBox();
		createSprites();
        	createSpriteTabs();
        	this.setPrefWidth(110);
        myAEM = AEM;
	}
	
	private void createSprites() {
		SpriteObject s1 = new SpriteObject("tree.png");
		System.out.println(s1);
		SpriteObject s2 = new SpriteObject("brick.png");
		SpriteObject s3 = new SpriteObject("water.png");
		SpriteObject s4 = new SpriteObject("pikachu.png");
		myAEM.addDefaultSprite(s1);
		myAEM.addDefaultSprite(s2);
		myAEM.addDefaultSprite(s3);
		myAEM.addDefaultSprite(s4);
		mySprites.getChildren().addAll(s1.getImageView(), s2.getImageView(), s3.getImageView(), s4.getImageView());
		
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
		//mySprites.getChildren().add(imageStack);
	}
	
    private void createSpriteTabs() {
		Tab defaultSpriteTab = new Tab();
		defaultSpriteTab.setText("Default Sprites");
		defaultSpriteTab.setContent(mySprites);
		defaultSpriteTab.setClosable(false);
		
		Tab mySpriteTab = new Tab();
		mySpriteTab.setText("User Sprites");
		mySpriteTab.setClosable(false);
     
		this.getTabs().addAll(defaultSpriteTab, mySpriteTab);
		this.setSide(Side.RIGHT);
    }

}
