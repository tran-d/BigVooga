package authoring.SpritePanels;

import authoring.AuthoringEnvironmentManager;
import authoring_UI.SpriteGridHandler;
import javafx.geometry.Side;
import javafx.scene.control.Tab;

public class MenuElementSelector extends GameElementSelector {

	private final static String MenuTabName = "Menu Templates";
	
	protected MenuElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM) {
		super(spriteGridHandler, AEM);
	}
	
	
	@Override 
	protected void createSpriteTabs(){
//		TabPane inventoryTemplateTabPane = new TabPane();
		Tab inventoryTemplateTab = createSubTab(MenuTabName, myAEM.getMenuTemplateController());
//		inventoryTemplateTabPane.setSide(Side.RIGHT);
//		inventoryTemplateTabPane.getTabs().addAll(inventoryTemplateTab);
		
		this.getTabs().add(inventoryTemplateTab);
		this.setSide(Side.TOP);
	}

}
