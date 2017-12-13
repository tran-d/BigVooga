package authoring_UI.SpriteCreatorTab;


import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;
public class SpriteCreatorSpritePanels extends SpritePanels{
	
	SpriteCreatorSpritePanels(){
		super();
	}
	
	public SpriteCreatorSpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM) {
		super(mySGH, myAEM);
		
	}
	
	@Override
	public void makeLayerDisplayPanel(AuthoringEnvironmentManager myAEM){
		displayPanel = new DisplayPanel(SPSM, myAEM);  
	}
	
	
	
	@Override
	public void makeElementSelector(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM){
		gameElementSelector = new SpriteCreatorElementSelector(mySGH, myAEM);
	}
}

