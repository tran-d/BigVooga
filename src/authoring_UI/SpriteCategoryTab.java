package authoring_UI;

import authoring.SpriteObject;

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
