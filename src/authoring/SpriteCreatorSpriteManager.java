package authoring;

import authoring.Sprite.AbstractSpriteObject;
import authoring_UI.SpriteCreatorSpriteSelector;

public class SpriteCreatorSpriteManager {

	private SpriteCreatorSpriteSelector mySpriteSelector;
	private AbstractSpriteObject selectedSprite;

	public SpriteCreatorSpriteManager() {

	}
	
	public void setSpriteSelector(SpriteCreatorSpriteSelector selector) {
		mySpriteSelector = selector;
	}

	public AbstractSpriteObject getActiveSprite() {
		return selectedSprite;
	}

	public void setActiveSprite(AbstractSpriteObject s) {
		selectedSprite = s;
	}

}
