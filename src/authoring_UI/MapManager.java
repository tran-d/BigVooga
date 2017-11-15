package authoring_UI;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class MapManager extends TabPane {
	private SpriteManager mySprites;
	private DraggableGrid myGrid;
	
	private int mapNum = 1;

	protected MapManager() {
		myGrid = new DraggableGrid();
		mySprites = new SpriteManager(myGrid);
		HBox authMap = new HBox(myGrid, mySprites);
		
		Tab currMap = new Tab();
		currMap.setText("Map " + mapNum);
		currMap.setContent(authMap);
		this.getTabs().add(currMap);
		// if initial map, don't close out
		if (mapNum == 1) {
			currMap.setClosable(false);
		}
		mapNum++;	
	}
}
