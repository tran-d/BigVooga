package authoring_UI;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DraggableGrid extends VBox {
	private StackPane myGrids;
	private ComboBox<String> myTerrain;
	private ImageView terrainImage;
	private GridPane terrainGrid;
	private GridPane myGrid;
	
	protected DraggableGrid(SpriteGridHandler spriteGridHandler) {
		myGrids = new StackPane();
		terrainImage = new ImageView(new Image("square.png"));
		createTerrainGrid();
		createGrid(spriteGridHandler);
		ScrollPane scrollGrid = new ScrollPane(myGrids);
		scrollGrid.setPannable(true);
		this.getChildren().addAll(createTerrainButton(), scrollGrid);
	}
	
	private void createGrid(SpriteGridHandler spriteGridHandler) {
		myGrid = new GridPane();
        
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                    StackPane sp = new StackPane();
                    sp.setPrefHeight(50);
                    sp.setPrefWidth(50);
//                    sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT);
                    sp.setBorder(new Border(border));
                    myGrid.add(sp, i, j);
                    spriteGridHandler.addDropHandling(sp);
                    spriteGridHandler.addGridMouseClick(sp);
            }
        }
        spriteGridHandler.addGrid(myGrid);
        myGrids.getChildren().add(myGrid);
	}
	
	private void createTerrainGrid() {
		terrainGrid = new GridPane();
		 for(int i = 0; i < 10; i++){
	            for(int j = 0; j < 10; j++){
	                    StackPane sp = new StackPane();
	                    sp.setPrefHeight(50);
	                    sp.setPrefWidth(50);
	                    sp.getChildren().add(terrainImage);
	                    terrainGrid.add(sp, i, j);
	            }
	        }
	}
	
	private HBox createTerrainButton() {
		myTerrain = new ComboBox<String>();
		myTerrain.getItems().addAll("Grass", "Desert", "Rock");
		myTerrain.getSelectionModel().selectFirst();
		Label terrainLabel = new Label("Choose Terrain"); 
		HBox terrainBox = new HBox(terrainLabel, myTerrain);
		terrainBox.setPadding(new Insets(5));
	    terrainBox.setSpacing(5);
	    
	    myTerrain.setOnAction(e -> changeTerrain());
	    
	    return terrainBox;
	}
	
	private void changeTerrain() {
		terrainImage = new ImageView(new Image(myTerrain.getValue().toLowerCase() + ".png"));
		terrainImage.setFitWidth(50);
		terrainImage.setFitHeight(50);
		myGrid.getChildren().forEach(cell -> {
			System.out.println("cell " + cell);
			StackPane sp = (StackPane) cell;
			System.out.println("sp " + sp);
			sp.getChildren().clear();
			sp.getChildren().add(terrainImage);
		});
//		for(int col = 0; col < 10; col++){
//            for(int row = 0; row < 10; row++){
//            		int index = (row * (col+1)) + col;
//                 StackPane sp = (StackPane) terrainGrid.getChildren().get(index);
//                 sp.getChildren().add(terrainImage);
//            }
//        }
	}
	
}
