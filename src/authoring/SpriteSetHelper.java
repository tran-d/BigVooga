package authoring;

import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;

public class SpriteSetHelper {
	Map<String, List<Pane>> allSpritesAsThumbnails;

	public SpriteSetHelper(Map<String, List<Pane>> everyTypeOfSpriteAsThumbnails) {
		System.out.println("CHECK SPRITE SET HELPER" );
		allSpritesAsThumbnails = everyTypeOfSpriteAsThumbnails;
	}
	
	public Map<String, List<Pane>> getThumbnailSprites() {
		return allSpritesAsThumbnails;
	}
}