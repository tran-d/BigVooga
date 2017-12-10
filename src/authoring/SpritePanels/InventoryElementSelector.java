package authoring.SpritePanels;

import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.Sprite.Parameters.*;
import authoring.Sprite.AnimationSequences.*;
import authoring.Sprite.UtilityTab.*;
import authoring.Sprite.InventoryTab.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;
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
