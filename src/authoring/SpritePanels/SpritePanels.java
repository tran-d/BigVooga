package authoring.SpritePanels;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteParameterSidebarManager;
import authoring_UI.SpriteGridHandler;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class SpritePanels extends VBox {

	protected DisplayPanel displayPanel;
	protected GameElementSelector gameElementSelector;
	protected SpriteParameterSidebarManager SPSM;
//	private SpriteSetHelper mySpriteSetHelper;
	
	public SpritePanels(){
		
	}

	public SpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM) {
//		Map<String, List<Pane>> thumbnailSprites = myAEM.getEveryTypeOfSpriteAsThumbnails();
//		mySpriteSetHelper = new SpriteSetHelper(thumbnailSprites);
		System.out.println("CHECK");
		SPSM = new SpriteParameterSidebarManager(mySGH.getDraggableGrid());
		makeDisplayPanel(myAEM);
		makeElementSelector(mySGH, myAEM);
		this.getChildren().addAll(displayPanel, gameElementSelector);
		this.setSpacing(5);
		
	}
	
	public void makeDisplayPanel(AuthoringEnvironmentManager myAEM){
		displayPanel = new DisplayPanel(SPSM, myAEM);  
	}
	
	public void makeElementSelector(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM){
		gameElementSelector = new GameElementSelector(mySGH, myAEM);
	}
	
	public DisplayPanel getDisplayPanel() {
		return displayPanel;
	}
	
	public Tab getDialoguesTab() {
		return gameElementSelector.getDialoguesTab();
	}
}