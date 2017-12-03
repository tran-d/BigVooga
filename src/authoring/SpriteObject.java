package authoring;

import java.util.ArrayList;

public class SpriteObject extends AbstractSpriteObject {
	
	public SpriteObject(){
		super();
	}
	
	public SpriteObject(boolean in){
		super(in);
	}
	
	public SpriteObject(String fileURL){
		super(fileURL);
	}
	
	
	@Override
	public SpriteObject newCopy() {
		// System.out.println("Making copy");
		SpriteObject ret = new SpriteObject();
		if (this.myImageURL != null) {
			ret.setupImageURLAndView(this.myImageURL);
		}
		ret.setName(this.getName());
		ret.setNumCellsHeight(this.getNumCellsHeight());
		ret.setNumCellsWidth(this.getNumCellsWidth());
		ret.replaceCategoryMap(this.categoryMap);
		ret.setInventory(new ArrayList<AbstractSpriteObject>(this.getInventory()));
		return ret;
	}

}
