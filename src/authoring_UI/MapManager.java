package authoring_UI;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObjectGridManagerI;
import javafx.geometry.Side;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class MapManager extends TabPane {
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab currentTab;
	private Tab addTab;
	private double myWidth;
	private double myHeight;
	private AuthoringEnvironmentManager myAEM;
	private SpriteObjectGridManagerI mySOGM;
	
	private int myTabCount = 1;
	private static final String TABTAG = "map ";
	

	protected MapManager(double width, double height) {
		myWidth = width;
		myHeight = height;
		mySelectModel = this.getSelectionModel();
		setTab();	
	}
	
	private void setTab() {
		this.setSide(Side.TOP);
		addTab = new Tab();
		addTab.setClosable(false);
		addTab.setText("+");
		addTab.setOnSelectionChanged(e -> {
			createTab(myTabCount);
			mySelectModel.select(currentTab);
		});
		this.getTabs().add(addTab);
	}

	private HBox setupScene() {
		Menu myMenu = new Menu();
		myAEM = new AuthoringEnvironmentManager();
		mySOGM = myAEM.getGridManager();
		DraggableGrid myGrid = new DraggableGrid(myTabCount, mySOGM);
		SpriteManager mySprites = new SpriteManager(myGrid, myAEM);
		HBox authMap = new HBox(myMenu, myGrid, mySprites);
		authMap.setPrefWidth(myWidth);
		authMap.setPrefHeight(myHeight);
		
		return authMap;
	}

	private void createTab(int tabCount) {
		currentTab = new Tab();
		String tabName = TABTAG + Integer.toString(tabCount);
		currentTab.setText(tabName);
		currentTab.setContent(setupScene());
		this.getTabs().add(this.getTabs().size() - 1, currentTab);
		myTabCount++;
	}
}
