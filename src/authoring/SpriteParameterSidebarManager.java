package authoring;

import java.util.ArrayList;
import java.util.HashMap;

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
	SpriteObjectI firstSprite;
	SpriteObjectGridManagerI mySOGM;

	SpriteParameterSidebarManager(SpriteObjectGridManagerI SOGM) {
		mySOGM = SOGM;
	}

	public SpriteObjectI getActiveSprite() throws Exception {
		// mySOGM = SOGM;
		ArrayList<SpriteObjectI> sprites = mySOGM.getActiveSpriteObjects();
		checkActiveCellsMatch(sprites);
		return firstSprite;
	}

	private void checkActiveCellsMatch(ArrayList<SpriteObjectI> SO_List) throws Exception {
		if (SO_List.size() > 0) {
			for (SpriteObjectI SO : SO_List) {
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

	private void initializeMaps(SpriteObjectI SO) {
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
		mySOGM.matchActiveCellsToSprite(firstSprite);
	}

}
