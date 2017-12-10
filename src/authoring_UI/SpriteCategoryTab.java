package authoring_UI;

import authoring.Sprite.*;

public abstract class SpriteCategoryTab {
	
	protected SpriteObject mySO;
	
	SpriteCategoryTab(SpriteObject SO){
		
	}
	
	SpriteCategoryTab() {
		
	}
	
	private void setupContent() {
		
	}
	
	
	protected abstract void onSelect();
}
