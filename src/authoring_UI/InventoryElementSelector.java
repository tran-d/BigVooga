package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class InventoryElementSelector extends GameElementSelector {

	private final static String INVENTORYTABNAME = "Inventory Templates";
	
	protected InventoryElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM) {
		super(spriteGridHandler, AEM);
	}
	
	
	@Override 
	protected void createSpriteTabs(){
//		TabPane inventoryTemplateTabPane = new TabPane();
		Tab inventoryTemplateTab = createSubTab(INVENTORYTABNAME, myAEM.getInventoryTemplateController());
//		inventoryTemplateTabPane.setSide(Side.RIGHT);
//		inventoryTemplateTabPane.getTabs().addAll(inventoryTemplateTab);
		
		this.getTabs().add(inventoryTemplateTab);
		this.setSide(Side.TOP);
	}

}
