package authoring_UI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

import authoring.NumberSpinner;
import authoring.SpriteObjectGridManager;
import authoring.TerrainObjectGridManager;
import engine.Layer;
import gui.welcomescreen.WelcomeScreen;
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
	private List<SpriteObjectGridManager> allGrids;
	private ArrayList<SpriteObjectGridManager> gridManagers;
	private SpriteObjectGridManager activeGrid;
	private StackPane myStackPane;
	private HBox topHbox = new HBox(10);
	private SpriteGridHandler mySGH;
	private Integer rows;
	private Integer cols;

	public DraggableGrid() {
		rows = 20;
		cols = 20;
		// myGrids = new StackPane();
		// terrainImage = new ImageView(new Image("square.png"));
		// createTerrainGrid();
		// ScrollPane scrollGrid = new ScrollPane(myGrids);
		// scrollGrid.setPannable(true);
		// scrollGrid.setMaxWidth(600);
		// createTerrainGrid();
	}
	
	public void construct(SpriteGridHandler spriteGridHandler) {
		mySGH = spriteGridHandler;
		makeTopInfo();
		makeLayers(spriteGridHandler);
		createGrid(spriteGridHandler);
	}

	private void makeTopInfo() {
		topHbox = new HBox(10);
		this.getChildren().add(topHbox);
	}

	private void createGrid(SpriteGridHandler spriteGridHandler) {
		myStackPane = new StackPane();
		myStackPane.setAlignment(Pos.CENTER);
//		ArrayList<Color> c = new ArrayList<Color>();
//		c.add(Color.TRANSPARENT);
//		c.add(Color.YELLOW);
//		c.add(Color.BLACK);
		for (SpriteObjectGridManager ml: gridManagers){
			myStackPane.getChildren().add(ml.getMapLayer());
			makeLayerButton(ml);
			showLayer(ml);
		}
		addChangeRowsNumberSpinner();
		addChangeColumnsNumberSpinner();
		ScrollPane scrollGrid = new ScrollPane(myStackPane);
		scrollGrid.setId("MapGrid");
		scrollGrid.setPannable(true);
		//scrollGrid.setPrefHeight(value);
		//scrollGrid.setMaxWidth(MainAuthoringGUI.AUTHORING_WIDTH/2 + 170);
//		scrollGrid.mouse
		// spriteGridHandler.addGrid(gp);
		this.getChildren().add(1, scrollGrid);
		this.setId("MapGridAndLayers");
		this.setMaxWidth(MainAuthoringGUI.AUTHORING_WIDTH/2 + 110);
	}
	
	public List<SpriteObjectGridManager> getGrids(){
//		return this.allGrids;
		//TODO
		return allGrids;
	}
	
	private void makeLayers(SpriteGridHandler spriteGridHandler){
		gridManagers = new ArrayList<SpriteObjectGridManager>();
//		MapLayer terrain = new TerrainLayer(15,15,spriteGridHandler);
		SpriteObjectGridManager terrain = new TerrainObjectGridManager(rows, cols, spriteGridHandler);
//		MapLayer terrain = terrMan.getMapLayer();
//		this.getChildren().add(terrain);
//		myStackPane.getChildren().add(new ImageView(new Image("pikachu.png")));
//		makeLayerButton(terrain);
//		SpriteLayer sprites = new SpriteLayer(15,15,spriteGridHandler);
//		MapLayer panels = new PanelLayer(15,15,spriteGridHandler);
		SpriteObjectGridManagerForSprites sprites = new SpriteObjectGridManagerForSprites(rows, cols, spriteGridHandler);
		PanelObjectGridManager panels = new PanelObjectGridManager(rows, cols, spriteGridHandler);
		gridManagers.add(terrain);
		gridManagers.add(sprites);
		gridManagers.add(panels);
		allGrids = new ArrayList<SpriteObjectGridManager>();
		gridManagers.forEach(item->{
			allGrids.add(item);
		});
//		allGrids = new ArrayList<SpriteObjectGridManager>(gridManagers);
	}
	
	public SpriteObjectGridManager getActiveGrid(){
		gridManagers.sort(new Comparator<SpriteObjectGridManager>(){

			@Override
			public int compare(SpriteObjectGridManager o1, SpriteObjectGridManager o2) {
				return o2.getMapLayer().getLayerNumber()-o1.getMapLayer().getLayerNumber();
			}
		});
		return gridManagers.get(0);
	}
	
	private void showLayer(SpriteObjectGridManager ML){
		System.out.println("Adding layer: "+ML.getName());
		if (!gridManagers.contains(ML)){
		gridManagers.add(ML);
		}
//		myStackPane.getChid
//		myStackPane.getChildren().add(ML.getLayerNumber(), ML);
		ML.setVisible(true);
	}
	
	private void hideLayer(SpriteObjectGridManager ML){
//		myStackPane.getChildren().remove(ML);
		mySGH.removeActiveCells();
		if (gridManagers.contains(ML)){
			gridManagers.remove(ML);
		} 
		ML.setVisible(false);
	}
	
	private void makeLayerButton(SpriteObjectGridManager ML) {
		HBox hbox = new HBox(10);
		hbox.setId("layerbox");
		Label label = new Label(ML.getName());
		label.setTextFill(Color.ANTIQUEWHITE);
		

		CheckBox checkbox = new CheckBox();
		checkbox.setTextFill(Color.BISQUE);
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
		if (this.topHbox.getChildren().size()==0){
		Separator s = new Separator();
		
		s.setOrientation(Orientation.VERTICAL);
		topHbox.getChildren().add(s);
		}
		
		topHbox.getChildren().add(in);
		Separator s = new Separator();
		
		s.setOrientation(Orientation.VERTICAL);
		topHbox.getChildren().add(s);
	}
	
	private void addChangeRowsNumberSpinner(){
		NumberSpinner ret = new NumberSpinner(new BigDecimal((Integer)rows), BigDecimal.ONE);
		ret.setCheckFunction(new Function<Integer, Boolean>(){

			@Override
			public Boolean apply(Integer t) {
				for (SpriteObjectGridManager SOGM: allGrids){
					SOGM.setNumRows(t);
				}
				return true;
			}
			
		});
		topHbox.getChildren().add(ret);
	}
	
	private void addChangeColumnsNumberSpinner(){
		NumberSpinner ret = new NumberSpinner(new BigDecimal((Integer)cols), BigDecimal.ONE);
		ret.setCheckFunction(new Function<Integer, Boolean>(){

			@Override
			public Boolean apply(Integer t) {
				for (SpriteObjectGridManager SOGM: allGrids){
					SOGM.setNumCols(t);
				}
				return true;
			}
			
		});
		topHbox.getChildren().add(ret);
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