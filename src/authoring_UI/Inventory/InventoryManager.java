package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.SpritePanels.InventorySpritePanels;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import engine.utilities.data.GameDataHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryManager extends MapManager{
	
	private SpriteObjectGridManager InventoryGridBE;
	private GameDataHandler GDH;

	public InventoryManager(AuthoringEnvironmentManager AEM, Scene currentScene) {
		super(AEM, currentScene);
		GDH = AEM.getGameDataHandler();
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){

		System.out.println("DG in HUDMANAGER");
		DraggableGrid ret = new DraggableGrid(GDH);

		InventoryGridBE = new InventoryGridManager();
		BackgroundGridManager BackgroundGrid = new BackgroundGridManager(InventoryGridBE.getDefaultRows(), InventoryGridBE.getDefaultCols());
		List<SpriteObjectGridManager> grids = new ArrayList<SpriteObjectGridManager>();
		grids.add(BackgroundGrid);
		grids.add(InventoryGridBE);
		ret.setAllGrids(grids);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGER_NAME = "InventoryManager";
	}
	
	@Override 
	protected void setTabTag(){
		TAB_TAG="Inventory";
	}
	
	@Override
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return new ArrayList<DraggableGrid>();
	}
	
	@Override
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new InventorySpritePanels(mySpriteGridHandler, myAEM);
	}
	
	
	

}