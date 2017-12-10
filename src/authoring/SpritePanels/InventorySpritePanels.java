package authoring.SpritePanels;

import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;
public class InventorySpritePanels extends SpritePanels{
	
	InventorySpritePanels(){
		super();
	}
	
	public InventorySpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM) {
		super(mySGH, myAEM);
		
	}
	
	@Override
	public void makeDisplayPanel(AuthoringEnvironmentManager myAEM){
		displayPanel = new DisplayPanel(SPSM, myAEM);  
	}
	
	@Override
	public void makeElementSelector(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM){
		gameElementSelector = new InventoryElementSelector(mySGH, myAEM);
	}
}