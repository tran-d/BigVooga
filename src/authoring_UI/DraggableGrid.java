package authoring_UI;

import java.util.ArrayList;

import engine.Layer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
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
	private ArrayList<MapLayer> mapLayers;
	
	private StackPane myStackPane;
	private HBox topHbox = new HBox(10);

	protected DraggableGrid(SpriteGridHandler spriteGridHandler) {
		
		// myGrids = new StackPane();
		// terrainImage = new ImageView(new Image("square.png"));
		// createTerrainGrid();
		makeTopInfo();
		makeLayers(spriteGridHandler);
		createGrid(spriteGridHandler);
		
		
		
		// ScrollPane scrollGrid = new ScrollPane(myGrids);
		// scrollGrid.setPannable(true);
		// scrollGrid.setMaxWidth(600);
		// createTerrainGrid();
	}

	private void makeTopInfo() {
		topHbox = new HBox(10);
		this.getChildren().add(0, topHbox);
	}
	

	private void createGrid(SpriteGridHandler spriteGridHandler) {

		myStackPane = new StackPane();
		myStackPane.setAlignment(Pos.CENTER);
//		ArrayList<Color> c = new ArrayList<Color>();
//		c.add(Color.TRANSPARENT);
//		c.add(Color.YELLOW);
//		c.add(Color.BLACK);

		
		for (MapLayer ml: mapLayers){
			myStackPane.getChildren().add(ml);
			makeLayerButton(ml);
			showLayer(ml);
		}
		
		ScrollPane scrollGrid = new ScrollPane(myStackPane);
		scrollGrid.setPannable(true);
		scrollGrid.setMaxWidth(500);

		// spriteGridHandler.addGrid(gp);

		this.getChildren().add(1, scrollGrid);
	}
	
	private void makeLayers(SpriteGridHandler spriteGridHandler){
		mapLayers = new ArrayList<MapLayer>();
		MapLayer terrain = new TerrainLayer(15,15,spriteGridHandler);
//		this.getChildren().add(terrain);
//		myStackPane.getChildren().add(new ImageView(new Image("pikachu.png")));
//		makeLayerButton(terrain);
		MapLayer sprites = new SpriteLayer(15,15,spriteGridHandler);
		MapLayer panels = new PanelLayer(15,15,spriteGridHandler);
		mapLayers.add(terrain);
		mapLayers.add(sprites);
		mapLayers.add(panels);
	}
	
	
	
	private void showLayer(MapLayer ML){
		System.out.println("Adding layer: "+ML.getName());
//		myStackPane.getChid
//		myStackPane.getChildren().add(ML.getLayerNumber(), ML);
		ML.setVisible(true);
	}
	
	private void hideLayer(MapLayer ML){
//		myStackPane.getChildren().remove(ML);
		ML.setVisible(false);
	}
	
	private void makeLayerButton(MapLayer ML) {
		HBox hbox = new HBox(10);
		
		Label label = new Label(ML.getName());
		
		CheckBox checkbox = new CheckBox();
		checkbox.setSelected(true);
		checkbox.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue){
					showLayer(ML);
				} else {
					hideLayer(ML);
				}
				
			}
			
		});
		
		hbox.getChildren().addAll(label, checkbox);
		
		
		addLayerButton(hbox);
	}
	
	
	private void addLayerButton(HBox in){
		if (this.topHbox.getChildren().size()!=0){
			Separator s = new Separator();
			s.setOrientation(Orientation.VERTICAL);
			topHbox.getChildren().add(s);
		}
		topHbox.getChildren().add(in);
	}
	
	
	
	
	// private void createTerrainGrid() {
	// terrainGrid = new GridPane();
	// for(int i = 0; i < 10; i++){
	// for(int j = 0; j < 10; j++){
	// StackPane sp = new StackPane();
	// sp.setPrefHeight(50);
	// sp.setPrefWidth(50);
	// sp.getChildren().add(terrainImage);
	// terrainGrid.add(sp, i, j);
	// }
	// }
	// }

	// private HBox createTerrainButton() {
	// myTerrain = new ComboBox<String>();
	// myTerrain.getItems().addAll("Grass", "Desert", "Rock");
	// myTerrain.getSelectionModel().selectFirst();
	// Label terrainLabel = new Label("Choose Terrain");
	// HBox terrainBox = new HBox(terrainLabel, myTerrain);
	// terrainBox.setPadding(new Insets(5));
	// terrainBox.setSpacing(5);
	//
	// myTerrain.setOnAction(e -> changeTerrain());
	//
	// return terrainBox;
	// }
	//
	// private void changeTerrain() {
	// terrainImage = new ImageView(new Image(myTerrain.getValue().toLowerCase()
	// + ".png"));
	// terrainImage.setFitWidth(50);
	// terrainImage.setFitHeight(50);
	// myGrid.getChildren().forEach(cell -> {
	// System.out.println("cell " + cell);
	// StackPane sp = (StackPane) cell;
	// System.out.println("sp " + sp);
	// sp.getChildren().clear();
	// sp.getChildren().add(terrainImage);
	// });
	// for(int col = 0; col < 10; col++){
	// for(int row = 0; row < 10; row++){
	// int index = (row * (col+1)) + col;
	// StackPane sp = (StackPane) terrainGrid.getChildren().get(index);
	// sp.getChildren().add(terrainImage);
	// }
	// }
	// }


}
