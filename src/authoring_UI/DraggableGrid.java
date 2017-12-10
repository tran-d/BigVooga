package authoring_UI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import authoring.NumberSpinner;
import authoring.SpriteObjectGridManager;
import authoring.SpriteParameterSidebarManager;
import authoring.TerrainObjectGridManager;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
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
		for (SpriteObjectGridManager ml: allGrids){
			myStackPane.getChildren().add(ml.getMapLayer());
			makeLayerButton(ml);
			showLayer(ml);
		}
		addChangeRowsNumberSpinner();
		addChangeColumnsNumberSpinner();
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
		SpriteObjectGridManager terrain = new TerrainObjectGridManager(rows, cols, spriteGridHandler);
		SpriteObjectGridManagerForSprites sprites = new SpriteObjectGridManagerForSprites(rows, cols, spriteGridHandler);
		PanelObjectGridManager panels = new PanelObjectGridManager(rows, cols, spriteGridHandler);
		showingGrids.add(terrain);
		showingGrids.add(sprites);
		showingGrids.add(panels);
		showingGrids.forEach(item->{
			allGrids.add(item);
		});
		} else {
			allGrids.forEach(item->{
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
		System.out.println("Adding layer: "+ML.getName());
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
}