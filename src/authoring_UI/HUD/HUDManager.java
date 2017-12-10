package authoring_UI.HUD;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import javafx.stage.Stage;

public class HUDManager extends MapManager{

	
	private SpriteObjectGridManager HUDGridBE;
	private AuthoringEnvironmentManager myAEM;

	public HUDManager(AuthoringEnvironmentManager AEM, Stage currentStage) {
		super(AEM, currentStage);
		myAEM = AEM;
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){
		System.out.println("DG in HUDMANAGER");
		DraggableGrid ret = new DraggableGrid();
		HUDGridBE = new HUDGridManager();
		ret.setAllGrids(HUDGridBE);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGERNAME = "HUDManager";
	}
	
	@Override 
	protected void setTabTag(){
		TAB_TAG="HUD";
	}
	
	@Override
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return new ArrayList<DraggableGrid>();
	}
	
	@Override
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new SpritePanels(mySpriteGridHandler, myAEM);
	}

	
}


	
