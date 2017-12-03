package authoring;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.ImageView;

public class InventoryObject extends AbstractSpriteObject{

	public InventoryObject(boolean b) {
		super(b);
	}

	@Override
	public AbstractSpriteObject newCopy() {
		SpriteObject ret = new SpriteObject();
		if (this.myImageURL != null) {
			ret.setupImageURLAndView(this.myImageURL);
		}
		ret.setName(this.getName());
		ret.replaceCategoryMap(this.categoryMap);
		return ret;
	}
	


}
