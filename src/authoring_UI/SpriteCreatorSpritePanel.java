package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterSidebarManager;
import javafx.scene.layout.VBox;

public class SpriteCreatorSpritePanel extends VBox {
	private DisplayPanel displayPanel;
	private SpriteCreatorSpriteSelector spriteSelector;
	private SpriteParameterSidebarManager SPSM;
	// private SpriteSetHelper mySpriteSetHelper;

	public SpriteCreatorSpritePanel(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM) {
		// Map<String, List<Pane>> thumbnailSprites =
		// myAEM.getEveryTypeOfSpriteAsThumbnails();
		// mySpriteSetHelper = new SpriteSetHelper(thumbnailSprites);
		System.out.println("CHECK");
		// SPSM = new SpriteParameterSidebarManager(mySGH.getDraggableGrid());
		displayPanel = new DisplayPanel(SPSM, myAEM);
		spriteSelector = new SpriteCreatorSpriteSelector(mySGH, myAEM);
		this.getChildren().addAll(displayPanel, spriteSelector);
		this.setSpacing(5);

	}

	public DisplayPanel getDisplayPanel() {
		return displayPanel;
	}

}
