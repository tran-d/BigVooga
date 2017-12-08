package authoring_UI;

import java.util.List;
import java.util.Map;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import authoring.SpriteParameterSidebarManager;
import authoring.SpriteSetHelper;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SpritePanels extends VBox {

	private DisplayPanel displayPanel;
	private GameElementSelector gameElementSelector;
	private SpriteParameterSidebarManager SPSM;
	private SpriteSetHelper mySpriteSetHelper;

	public SpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM) {
		Map<String, List<Pane>> thumbnailSprites = myAEM.getEveryTypeOfSpriteAsThumbnails();
		mySpriteSetHelper = new SpriteSetHelper(thumbnailSprites);
		System.out.println("CHECK");
		SPSM = new SpriteParameterSidebarManager(mySGH.getDraggableGrid());
		displayPanel = new DisplayPanel(SPSM, mySpriteSetHelper); // DOES NOT NEED AEM.
		gameElementSelector = new GameElementSelector(mySGH, myAEM);
		this.getChildren().addAll(displayPanel, gameElementSelector);
		this.setSpacing(5);
		
	}
	
	public DisplayPanel getDisplayPanel() {
		return displayPanel;
	}
	
	public Tab getDialoguesTab() {
		return gameElementSelector.getDialoguesTab();
	}
}