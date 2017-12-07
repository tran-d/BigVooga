package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class SpritePanels extends VBox {

	private DisplayPanel displayPanel;
	private GameElementSelector gameElementSelector;

	public SpritePanels(MapManager myManager, SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM, SpriteObjectGridManagerI mySOGM) {
		
		displayPanel = new DisplayPanel(myAEM, myManager);
		gameElementSelector = new GameElementSelector(mySGH, myAEM, mySOGM);
		
		this.getChildren().addAll(displayPanel, gameElementSelector);
		
	}
	
	public DisplayPanel getDisplayPanel() {
		return displayPanel;
	}
	
	public Tab getDialoguesTab() {
		return gameElementSelector.getDialoguesTab();
	}
	
}
