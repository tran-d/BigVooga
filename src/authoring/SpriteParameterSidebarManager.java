package authoring;

import java.util.ArrayList;
import java.util.HashMap;

import authoring_UI.DraggableGrid;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpriteParameterSidebarManager {

	// ScrollPane SP;
	HashMap<String, ArrayList<SpriteParameterI>> everyStateParameter = new HashMap<String, ArrayList<SpriteParameterI>>();
	HashMap<String, String> newNameOldName = new HashMap<String, String>();
	boolean firstTimeThrough = true;
	SpriteObject firstSprite;
//	SpriteObjectGridManagerI mySOGM;
	DraggableGrid myDG;

	SpriteParameterSidebarManager(DraggableGrid DG) {
		myDG = DG;
//		mySOGM = SOGM;
	}

	public SpriteObject getActiveSprite() throws Exception {
		// mySOGM = SOGM;
		ArrayList<SpriteObject> sprites = myDG.getActiveGrid().getActiveSpriteObjects();
		checkActiveCellsMatch(sprites);
		return firstSprite;
	}

	private void checkActiveCellsMatch(ArrayList<SpriteObject> SO_List) throws Exception {
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
