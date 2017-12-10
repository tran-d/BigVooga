package authoring_UI;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.PanelObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManagerForSprites;
import authoring.GridManagers.TerrainObjectGridManager;
import authoring.util.NumberSpinner;
import engine.utilities.data.GameDataHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private List<SpriteObjectGridManager> showingGrids;
	private StackPane myStackPane;
	private HBox topHbox = new HBox(10);
	private SpriteGridHandler mySGH;
	private Integer rows;
	private Integer cols;

	public DraggableGrid() {
		System.out.println("Draggable Grid constructor called (MAPMAN)");
		rows = 20; // TODO HARDCODED
		cols = 20;
	}
	
	public DraggableGrid(List<SpriteObjectGridManager> SGMs) {
		this();
		allGrids = SGMs;
	}

	
	public void construct(SpriteGridHandler spriteGridHandler){
		if (allGrids == null){
			allGrids = new ArrayList<SpriteObjectGridManager>();
		}
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
		
		
		int layerRows = rows;
		int layerColumns = cols;
		for (SpriteObjectGridManager ml: allGrids){
			myStackPane.getChildren().add(ml.getMapLayer());
			makeLayerButton(ml);
			showLayer(ml);
			layerRows = ml.getNumRows();
			layerColumns = ml.getNumCols();
		}
//		Image image = new Image("pikachu.png");
//		ImageView iv = new ImageView(image);
//		iv.setFitHeight(myStackPane.getHeight());
//		iv.setFitWidth(myStackPane.getWidth());
//		myStackPane.getChildren().add(iv);
		
		addChangeRowsNumberSpinner(layerRows);
		addChangeColumnsNumberSpinner(layerColumns);
		ScrollPane scrollGrid = new ScrollPane(myStackPane);
		scrollGrid.setId("MapGrid");
		scrollGrid.setPannable(true);

		this.getChildren().add(1, scrollGrid);
		this.setId("MapGridAndLayers");
		this.setMaxWidth(MainAuthoringGUI.AUTHORING_WIDTH/2 + 110);
	}
	
	public List<SpriteObjectGridManager> getGrids(){
		//TODO
		return allGrids;
	}
	
	public void setAllGrids(ArrayList<SpriteObjectGridManager> SGMs){
		allGrids = SGMs;
	}
	
	public void setAllGrids(SpriteObjectGridManager SGM){
		allGrids = new ArrayList<SpriteObjectGridManager>();
		allGrids.add(SGM);
	}
	
	private void makeLayers(SpriteGridHandler spriteGridHandler){
		showingGrids = new ArrayList<SpriteObjectGridManager>();
		if (allGrids.size()==0){
		SpriteObjectGridManager background = new BackgroundGridManager(rows, cols, spriteGridHandler);
		SpriteObjectGridManager terrain = new TerrainObjectGridManager(rows, cols, spriteGridHandler);
		SpriteObjectGridManagerForSprites sprites = new SpriteObjectGridManagerForSprites(rows, cols, spriteGridHandler);
		PanelObjectGridManager panels = new PanelObjectGridManager(rows, cols, spriteGridHandler);
		showingGrids.add(background);
		showingGrids.add(terrain);
		showingGrids.add(sprites);
		showingGrids.add(panels);
		showingGrids.forEach(item->{
			allGrids.add(item);
		});
		} else {
			allGrids.forEach(item->{
				System.out.println("lready has a grid!: "+item);
				item.setSpriteGridHandler(spriteGridHandler);
				item.createMapLayer();
//				item.getMapLayer().setSpriteGridHandler();
				showingGrids.add(item);
			});
		}
//		allGrids = new ArrayList<SpriteObjectGridManager>(gridManagers);
	}
	
	public SpriteObjectGridManager getActiveGrid(){
		allGrids.sort(new Comparator<SpriteObjectGridManager>(){
			@Override
			public int compare(SpriteObjectGridManager o1, SpriteObjectGridManager o2) {
				return o2.getMapLayer().getLayerNumber()-o1.getMapLayer().getLayerNumber();
			}
		});
		return allGrids.get(0);
	}
	
	private void showLayer(SpriteObjectGridManager ML){
//		System.out.println("Adding layer: "+ML.getName());
		if (!allGrids.contains(ML)){
			allGrids.add(ML);
		}
		ML.setVisible(true);
	}
	
	private void hideLayer(SpriteObjectGridManager ML){
//		myStackPane.getChildren().remove(ML);
		mySGH.deactivateActiveSprites();
		if (showingGrids.contains(ML)){
			showingGrids.remove(ML);
		}
		ML.setVisible(false);
	}
	
	private void makeLayerButton(SpriteObjectGridManager ML) {
		HBox hbox = new HBox(10);
		hbox.setId("layerbox");
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
		if (ML.canFillBackground()){
			//ColorPicker
			ColorPicker cp = new ColorPicker(Color.SANDYBROWN);
			cp.setOnAction((event)->{
				ML.setColor(cp.getValue());
			});
			
			//Choose Image
			
			Button button = new Button("Set Background Image");
			button.setOnAction((event)->{
				Node parent = ML.getMapLayer().getParent();
				Scene s = parent.getScene();
				while (s == null) {
					parent = parent.getParent();
					s = parent.getScene();
				}
				File f = GameDataHandler.chooseFileForImageSave(s.getWindow());
				FileInputStream fis;
				try{
				fis = new FileInputStream(f);
				ML.getMapLayer().setBackgroundImage(new Image(fis), f.getName());
				} catch (Exception e){
					// Dont change background 
				}
				
			});
	
			hbox.getChildren().addAll(cp, button);
		}
		
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
	
	private void addChangeRowsNumberSpinner(int numRows){
		NumberSpinner ret = new NumberSpinner(new BigDecimal((Integer)numRows), BigDecimal.ONE);
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
	
	private void addChangeColumnsNumberSpinner(int numCols){
		NumberSpinner ret = new NumberSpinner(new BigDecimal((Integer)numCols), BigDecimal.ONE);
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
}