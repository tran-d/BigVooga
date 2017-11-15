package authoring_UI;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class MapManager extends TabPane {
	private SpriteManager mySprites;
	private DraggableGrid myGrid;
	
	private int mapNum = 1;

	protected MapManager() {
		mySprites = new SpriteManager();
		myGrid = new DraggableGrid();
		VBox authEnv = new VBox(mySprites, myGrid);
		
		Tab currMap = new Tab();
		currMap.setText("Map " + mapNum);
		currMap.setContent(authEnv);
		this.getTabs().add(currMap);
		mapNum++;	
	}
}
