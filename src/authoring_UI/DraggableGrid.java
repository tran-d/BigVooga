package authoring_UI;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.PanelObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManagerForSprites;
import authoring.GridManagers.TerrainObjectGridManager;
import authoring.Sprite.AnimationSequences.AuthoringImageView;
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

public class DraggableGrid extends VBox implements DraggableGridAPI{
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
	private String savePath;

	public DraggableGrid() {
		rows = 20; // TODO HARDCODED
		cols = 20;
	}
	
	public SpriteGridHandler getSGH() {
		return mySGH;
	}
	
	public void loadLayers(List<SpriteObjectGridManager> SOGMList) {
		Random rand = new Random();
		if (allGrids == null) allGrids = new ArrayList<SpriteObjectGridManager>();
		this.setAllGrids(SOGMList);
//		mySGH = new SpriteGridHandler(rand.nextInt(), this);
//		for (SpriteObjectGridManager SOGM : SOGMList) {
//			SOGM.setSpriteGridHandler(mySGH);
			
//			if (SOGM.getLayerNum() == 0) {
//				System.out.println("NUM ROWS IN DG: " + SOGM.getRowsForImport());
//				TerrainObjectGridManager TOGM = new TerrainObjectGridManager(SOGM.getRowsForImport(), SOGM.getColsForImport(), SOGM.getLayerNum(), SOGM.getColor());
//				TOGM.setSpriteGridHandler(mySGH);
//				System.out.println("TOGM HAS THIS MANY SPRITES: " + SOGM.getStoredSpriteList().size());
//				TOGM.createMapLayer(SOGM.getStoredSpriteList()); 
//				allGrids.add(TOGM);
//			}
//			if (SOGM.getLayerNum() == 1) {
//				System.out.println("NUM ROWS IN DG: " + SOGM.getRowsForImport());
//				SpriteObjectGridManagerForSprites SOGMS = new SpriteObjectGridManagerForSprites(SOGM.getRowsForImport(), SOGM.getColsForImport(), SOGM.getLayerNum(), SOGM.getColor());
//				SOGMS.setSpriteGridHandler(mySGH);
//				System.out.println("SOGMS HAS THIS MANY SPRITES: " + SOGM.getStoredSpriteList().size());
//				SOGMS.createMapLayer(SOGM.getStoredSpriteList()); 
//				allGrids.add(SOGMS);
//			}
//			if (SOGM.getLayerNum() == 2) {
//				System.out.println("NUM ROWS IN DG: " + SOGM.getRowsForImport());
//				PanelObjectGridManager POGM = new PanelObjectGridManager(SOGM.getRowsForImport(), SOGM.getColsForImport(), SOGM.getLayerNum(), SOGM.getColor());
//				POGM.setSpriteGridHandler(mySGH);
//				System.out.println("POGM HAS THIS MANY SPRITES: " + SOGM.getStoredSpriteList().size());
//				POGM.createMapLayer(SOGM.getStoredSpriteList());
//				allGrids.add(POGM);
//			}
		
		System.out.println("successfully added all grids, allGrids size is: " + allGrids.size());
	}
	
	public DraggableGrid(int row, int col) {
		rows = row;
		cols = col;
	}
	
	public DraggableGrid(List<SpriteObjectGridManager> SGMs) {
		this();
		allGrids = SGMs;
	}

	@Override
	public void construct(SpriteGridHandler spriteGridHandler){
		if (allGrids == null){
			System.out.println("GRIDS IS NULL!");
			allGrids = new ArrayList<SpriteObjectGridManager>();
		}
		mySGH = spriteGridHandler;
		makeTopInfo();
		makeLayers(spriteGridHandler);
		createGrid(spriteGridHandler);
	}

	private void makeTopInfo() {
		topHbox = new HBox(10);
		topHbox.setAlignment(Pos.CENTER);
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
			System.out.println("LAYER ROWS : " + layerRows);
			layerColumns = ml.getNumCols();
		}
	
		addChangeRowsNumberSpinner(layerRows);
		addChangeColumnsNumberSpinner(layerColumns);
		ScrollPane scrollGrid = new ScrollPane(myStackPane);
		scrollGrid.setId("MapGrid");
		scrollGrid.setPannable(true);

		this.getChildren().add(1, scrollGrid);
		this.setId("MapGridAndLayers");
		this.setMaxWidth(MainAuthoringGUI.AUTHORING_WIDTH/2 + 124);
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
	
	public void setAllGrids(List<SpriteObjectGridManager> SGMs){
		allGrids = new ArrayList<SpriteObjectGridManager>();
		allGrids.addAll(SGMs);
	}
	
	private void makeLayers(SpriteGridHandler spriteGridHandler){
		showingGrids = new ArrayList<SpriteObjectGridManager>();
		if (allGrids.size()==0){
			System.out.println("SHOULD NOT BE GOING THROUGH THIS ALERT ALERT ALERT");
		SpriteObjectGridManager background = new BackgroundGridManager(rows, cols, spriteGridHandler);
		SpriteObjectGridManager terrain = new TerrainObjectGridManager(rows, cols, spriteGridHandler);
		SpriteObjectGridManagerForSprites sprites = new SpriteObjectGridManagerForSprites(rows, cols, spriteGridHandler);
		PanelObjectGridManager panels = new PanelObjectGridManager(rows, cols, spriteGridHandler);
		allGrids.add(background);
		allGrids.add(terrain);
		allGrids.add(sprites);
		allGrids.add(panels);
		allGrids.forEach(item->{
			showingGrids.add(item);
		});
		} else {
			allGrids.forEach(item->{
				System.out.println("already has a grid!: "+item);
				item.setSpriteGridHandler(spriteGridHandler);
				item.createMapLayer();
				
				item.setSizeToMatchDefaults();
			
//				item.getMapLayer().setSpriteGridHandler();
				showingGrids.add(item);
			});
		}
	}
	
	public SpriteObjectGridManager getActiveGrid(){
		showingGrids.sort(new Comparator<SpriteObjectGridManager>(){
			@Override
			public int compare(SpriteObjectGridManager o1, SpriteObjectGridManager o2) {
				if (o1 instanceof BackgroundGridManager){
					return 1;
				} else if (o2 instanceof BackgroundGridManager){
					return -1;
				}
				return o2.getLayerNum()-o1.getLayerNum();
			}
		});

		return showingGrids.get(0);
	}
	
	private void showLayer(SpriteObjectGridManager ML){
//		System.out.println("Adding layer: "+ML.getName());
		if (!showingGrids.contains(ML)){
			showingGrids.add(ML);
		}
		ML.setVisible(true);
	}
	
	private void hideLayer(SpriteObjectGridManager ML){
//		myStackPane.getChildren().remove(ML);
//		ML.getActiveSpriteObjects()
		mySGH.deactivateActiveSprites();
		if (showingGrids.contains(ML)){
			showingGrids.remove(ML);
		}
		ML.setVisible(false);
	}
	
	private void makeLayerButton(SpriteObjectGridManager ML) {
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);
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
			ColorPicker cp = new ColorPicker(Color.TRANSPARENT);
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
				File file = GameDataHandler.chooseFileForImageSave(s.getWindow());
				if (file != null) {
					Image image = new Image(GameDataHandler.getImageURIAndCopyToResources(file));
					ML.getMapLayer().setBackgroundImage(image, file.getName());
				}
			});
	
			hbox.getChildren().addAll(cp, button);
			hbox.setAlignment(Pos.CENTER);
		}
		
		addLayerButton(hbox);
	}
	
	private void addLayerButton(HBox in){
		
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
	
	public void setSavePath(String setPath) {
		savePath = setPath;
	}

	public String getSavePath() {
		return savePath;
	}
}