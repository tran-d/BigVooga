package authoring_UI;

import javafx.scene.layout.HBox;

public class SpriteCreatorEnvironment extends HBox {

	protected SpriteCreatorEnvironment(SpriteCreatorSpritePanel SP) {
		setPanels(SP);
	}

	private void setPanels(SpriteCreatorSpritePanel spritePanels) {
		this.getChildren().add(0, spritePanels);
	}

}
