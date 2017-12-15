package authoring.SpritePanels;

import authoring.AuthoringEnvironmentManager;
import authoring_UI.SpriteGridHandler;
import javafx.geometry.Side;
import javafx.scene.control.Tab;

public class InventoryElementSelector extends GameElementSelector {

	private final static String INVENTORYTABNAME = "Inventory Templates";
	
	protected InventoryElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM) {
		super(spriteGridHandler, AEM);
	}
	
	
/*	@Override 
//	protected void createSpriteTabs(){
//		TabPane inventoryTemplateTabPane = new TabPane();
//		Tab inventoryTemplateTab = createSubTab(INVENTORYTABNAME, myAEM.getInventoryTemplateController());
//		inventoryTemplateTabPane.setSide(Side.RIGHT);
//		inventoryTemplateTabPane.getTabs().addAll(inventoryTemplateTab);
//		
//		this.getTabs().add(inventoryTemplateTab);
//		this.setSide(Side.TOP);
	}*/

}
