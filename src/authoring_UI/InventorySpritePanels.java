package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterSidebarManager;

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
