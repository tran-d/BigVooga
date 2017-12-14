package authoring_UI.SpriteCreatorTab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import authoring.AuthoringEnvironmentManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.SpritePanels.SpritePanels;
import authoring_UI.DraggableGrid;
import authoring_UI.MapManager;
import authoring_UI.SpriteGridHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class SpriteCreatorManagerSlack extends MapManager{
	
	private SpriteObjectGridManager SpriteCreatorGridBE;

	public SpriteCreatorManagerSlack(AuthoringEnvironmentManager AEM, Scene currentScene, String type) {
		super(AEM, currentScene, type);
		
	}
	
	@Override 
	protected DraggableGrid makeDraggableGrid(){
		DraggableGrid ret = new DraggableGrid();
		if (myType.equals("SpriteObject")){
		SpriteCreatorGridBE = new SpriteCreatorGridManager(myAEM, (Image im, String filePath)->new SpriteObject(im, filePath));
		} else if (myType.equals("InventoryObject")){
			SpriteCreatorGridBE = new SpriteCreatorGridManager(myAEM, (Image im, String filePath) -> new InventoryObject(im, filePath));
		} else {
			SpriteCreatorGridBE = new SpriteCreatorGridManager(myAEM, (Image im, String filePath)->new SpriteObject(im, filePath));
		}
//		BackgroundGridManager BackgroundGrid = new BackgroundGridManager(SpriteCreatorGridBE.getDefaultRows(), SpriteCreatorGridBE.getDefaultCols());
		List<SpriteObjectGridManager> grids = new ArrayList<SpriteObjectGridManager>();
//		grids.add(BackgroundGrid);
		grids.add(SpriteCreatorGridBE);
		ret.setAllGrids(grids);
		return ret;
	}
	
	@Override
	protected void setManagerName(){
		MANAGERNAME = myType.equals("InventoryObject") ?"InventoryCreator" : "SpriteCreator";
	}
	
	@Override 
	protected void setTabTag(){
		TAB_TAG=myType.equals("InventoryObject") ?"InventoryCreator" : "SpriteCreator";
	}
	
	@Override
	protected List<DraggableGrid> getListOfDraggableGrids(){
		return new ArrayList<DraggableGrid>();
	}
	
	@Override
	protected SpritePanels makeSpritePanels(SpriteGridHandler mySpriteGridHandler){
		return new SpriteCreatorSpritePanels(mySpriteGridHandler, myAEM, myType);
	}
//	@Override
//	protected AuthoringMapEnvironment makeAuthoringMapEnvironment(SpritePanels spritePanels, DraggableGrid dg) {
//		AuthoringMapEnvironment ret = new AuthoringMapEnvironment(spritePanels, dg);
//		return null;
////		ret.addNode();
//	}
	

}