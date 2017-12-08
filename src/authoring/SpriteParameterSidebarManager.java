package authoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring_UI.DraggableGrid;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpriteParameterSidebarManager {
	Map<String, List<SpriteParameterI>> everyStateParameter = new HashMap<String, List<SpriteParameterI>>();
	Map<String, String> newNameOldName = new HashMap<String, String>();
	boolean firstTimeThrough = true;
	SpriteObject firstSprite;
	DraggableGrid myDG;
	private boolean multipleCellsActive = false;

	public SpriteParameterSidebarManager(DraggableGrid DG) {
		myDG = DG;
	}
	
	public boolean multipleActive(){
		return multipleCellsActive;
	}

	public SpriteObject getActiveSprite() throws Exception {
		List<SpriteObject> sprites = myDG.getActiveGrid().getActiveSpriteObjects();
		checkActiveCellsMatch(sprites);
		return firstSprite;
	}

	private void checkActiveCellsMatch(List<SpriteObject> SO_List) throws Exception {
		multipleCellsActive = (SO_List.size()>1);
		if (SO_List.size() > 0) {
			firstTimeThrough = true;
			for (SpriteObject SO : SO_List) {
				if (firstTimeThrough) {
					initializeMaps(SO);
					firstTimeThrough = false;
				} else {
					boolean matches = SO.isSame(firstSprite);
					if (!matches) {
						throw new Exception("Sprites are not identical");
					}
				}
			}
		} else {
			setNoCellsActive();
			System.out.println("No cells active");
		}
	}

	private void initializeMaps(SpriteObject SO) {
		firstSprite = SO;
		everyStateParameter = SO.getParameters();
		newNameOldName = new HashMap<String, String>();
	}
	
	public void setNoCellsActive() {
		firstTimeThrough = true;
		firstSprite = null;
		everyStateParameter = null;
		newNameOldName = null;
	}

	public void apply() {
		myDG.getActiveGrid().matchActiveCellsToSprite(firstSprite);
	}
}