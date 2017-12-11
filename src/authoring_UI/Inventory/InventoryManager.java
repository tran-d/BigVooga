package authoring_UI.Inventory;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.SpritePanels.InventorySpritePanels;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import javafx.stage.Stage;

public class InventoryManager extends MapManager{
	
	private SpriteObjectGridManager InventoryGridBE;
	private AuthoringEnvironmentManager myAEM;

	public InventoryManager(AuthoringEnvironmentManager AEM, Stage currentStage) {
		super(AEM, currentStage);
		myAEM = AEM;
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){
		System.out.println("DG in HUDMANAGER");
		DraggableGrid ret = new DraggableGrid();
		InventoryGridBE = new InventoryGridManager();
		ret.setAllGrids(InventoryGridBE);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGERNAME = "InventoryManager";
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