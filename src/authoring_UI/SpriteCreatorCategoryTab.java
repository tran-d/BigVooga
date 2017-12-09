package authoring_UI;

import javafx.scene.control.Tab;

abstract class SpriteCreatorCategoryTab extends Tab {
	private String tabName;
	
	protected SpriteCreatorCategoryTab(String name) {
		tabName = name;
		this.setText(tabName);
	}
}
