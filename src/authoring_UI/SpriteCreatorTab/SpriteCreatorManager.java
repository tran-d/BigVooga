package authoring_UI.SpriteCreatorTab;

import java.util.ArrayList;
import java.util.List;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.SpritePanels.MenuSpritePanels;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.AuthoringMapEnvironment;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpriteCreatorManager extends MapManager{
	
	private SpriteObjectGridManager SpriteCreatorGridBE;
	private AuthoringEnvironmentManager myAEM;

	public SpriteCreatorManager(AuthoringEnvironmentManager AEM, Scene currentScene) {
		super(AEM, currentScene);
		myAEM = AEM;
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){
		DraggableGrid ret = new DraggableGrid();
		SpriteCreatorGridBE = new SpriteCreatorGridManager();
		BackgroundGridManager BackgroundGrid = new BackgroundGridManager(SpriteCreatorGridBE.getDefaultRows(), SpriteCreatorGridBE.getDefaultCols());
		List<SpriteObjectGridManager> grids = new ArrayList<SpriteObjectGridManager>();
		grids.add(BackgroundGrid);
		grids.add(SpriteCreatorGridBE);
		ret.setAllGrids(grids);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGERNAME = "MenuManager";
	}
	
	@Override 
	protected void setTabTag(){
		TAB_TAG="MenuCreator";
	}
	
	@Override
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return new ArrayList<DraggableGrid>();
	}
	
	@Override
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new SpriteCreatorSpritePanels(mySpriteGridHandler, myAEM);
	}
	
	@Override
	protected AuthoringMapEnvironment makeAuthoringMapEnvironment(SpritePanels spritePanels, DraggableGrid dg){
		
		
	}
	
	
	

}