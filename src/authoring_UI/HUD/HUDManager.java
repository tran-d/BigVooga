package authoring_UI.HUD;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import javafx.scene.Scene;

public class HUDManager extends MapManager{

	
	private SpriteObjectGridManager HUDGridBE;

	public HUDManager(AuthoringEnvironmentManager AEM, Scene currentScene) {
		super(AEM, currentScene);
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){
		;
		DraggableGrid ret = new DraggableGrid();
		HUDGridBE = new HUDGridManager();
		BackgroundGridManager BackgroundGrid = new BackgroundGridManager(HUDGridBE.getDefaultRows(), HUDGridBE.getDefaultCols());
		List<SpriteObjectGridManager> grids = new ArrayList<SpriteObjectGridManager>();
		grids.add(BackgroundGrid);
		grids.add(HUDGridBE);
		ret.setAllGrids(grids);
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


	
