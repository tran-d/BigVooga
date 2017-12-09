package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteCreatorSpriteManager;
import javafx.scene.layout.VBox;

public class SpriteCreatorSpritePanel extends VBox {
	private SpriteCreatorDisplayPanel displayPanel;
	private SpriteCreatorSpriteSelector spriteSelector;
	private SpriteCreatorSpriteManager mySM;
	// private SpriteSetHelper mySpriteSetHelper;

	public SpriteCreatorSpritePanel(SpriteCreatorGridHandler mySGH, AuthoringEnvironmentManager myAEM, SpriteCreatorSpriteManager SM) {
		// Map<String, List<Pane>> thumbnailSprites =
		// myAEM.getEveryTypeOfSpriteAsThumbnails();
		// mySpriteSetHelper = new SpriteSetHelper(thumbnailSprites);
		System.out.println("CHECK");
		// SPSM = new SpriteParameterSidebarManager(mySGH.getDraggableGrid());
//		
//		ADD DISPLAYPANEL FOR SPRITECREATOR
		mySM = SM;
		displayPanel = new SpriteCreatorDisplayPanel(mySM,myAEM);
//		displayPanel = new DisplayPanel(SPSM, myAEM);
		spriteSelector = new SpriteCreatorSpriteSelector(mySM, mySGH, myAEM);
		mySM.setSpriteSelector(spriteSelector);
		
		//change where displayPanel and spriteSelector gets added. 
		this.getChildren().addAll(displayPanel, spriteSelector);
		this.setSpacing(5);

	}

	public SpriteCreatorDisplayPanel getDisplayPanel() {
		return displayPanel;
	}

	public SpriteCreatorSpriteSelector getSpriteSelector() {
		return spriteSelector;
	}
}
