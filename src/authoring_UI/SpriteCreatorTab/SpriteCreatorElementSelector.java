package authoring_UI.SpriteCreatorTab;

import authoring.AuthoringEnvironmentManager;
import authoring.SpritePanels.GameElementSelector;
import authoring_UI.SpriteGridHandler;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class SpriteCreatorElementSelector extends GameElementSelector {

	private final static String INVENTORYTABNAME = "Inventory Templates";
	
	protected SpriteCreatorElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM) {
		super(spriteGridHandler, AEM);
	}
	
	
	@Override 
	protected void createSpriteTabs(){
		Tab defaultSpriteTab = createSubTab(DEFAULT, myAEM.getDefaultSpriteController());
		Tab userSpriteTab = createSubTab(USER, myAEM.getCustomSpriteController());
		Tab importedSpriteTab = createSubTab(IMPORTED, myAEM.getImportedSpriteController());
		
		this.getTabs().addAll(defaultSpriteTab, userSpriteTab, importedSpriteTab);
		this.setSide(Side.TOP);
	}

}

