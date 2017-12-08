package authoring_UI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import authoring.NumberSpinner;
import authoring.SpriteObjectGridManager;
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
	private SpriteObjectGridManager activeGrid;
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
	
	public void construct(SpriteGridHandler spriteGridHandler) {
		mySGH = spriteGridHandler;
		makeTopInfo();
		makeLayers(spriteGridHandler);
		createGrid(spriteGridHandler);
	}

	private void makeTopInfo() {
		topHbox = new HBox(10);
		this.getChildren().add(0, topHbox);
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
		scrollGrid.setPannable(true);
		this.getChildren().add(1, scrollGrid);
		this.setMaxWidth(MainAuthoringGUI.AUTHORING_WIDTH/2 + 110);
		this.setMaxHeight(WelcomeScreen.HEIGHT-75);
	}
	
	public List<SpriteObjectGridManager> getGrids(){
		//TODO
		return allGrids;
	}
	
	private void makeLayers(SpriteGridHandler spriteGridHandler){
		SpriteObjectGridManager terrain = new TerrainObjectGridManager(rows, cols, spriteGridHandler);
		SpriteObjectGridManagerForSprites sprites = new SpriteObjectGridManagerForSprites(rows, cols, spriteGridHandler);
		PanelObjectGridManager panels = new PanelObjectGridManager(rows, cols, spriteGridHandler);
		allGrids = new ArrayList<SpriteObjectGridManager>();
		allGrids.add(terrain);
		allGrids.add(sprites);
		allGrids.add(panels);
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
		mySGH.removeActiveCells();
		if (allGrids.contains(ML)){
			allGrids.remove(ML);
		}
		ML.setVisible(false);
	}
	
	private void makeLayerButton(SpriteObjectGridManager ML) {
		HBox hbox = new HBox(10);
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
		if (this.topHbox.getChildren().size()!=0){
			Separator s = new Separator();
			s.setOrientation(Orientation.VERTICAL);
			topHbox.getChildren().add(s);
		}
		topHbox.getChildren().add(in);
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