package authoring.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

import authoring.Sprite.Parameters.SpriteParameterI;
import javafx.scene.image.Image;

public class SpriteObject extends AbstractSpriteObject{
	
//	private static final long serialVersionUID = 2616423033716253195L;
	
	public SpriteObject(){

	}
	
	public SpriteObject(boolean in){
		super(in);
	}
	
	public SpriteObject(String fileURL){
		super(fileURL);
	}
	
	public SpriteObject(Image image, String fileURL){
		super(image, fileURL);
	}

	
	
	@Override
	public SpriteObject newCopy() {
		// System.out.println("Making copy");
		SpriteObject ret = new SpriteObject();
		if (this.myImageURL != null) {
			ret.setupImageURLAndView(this.myImageURL);
		}
		ret.setName(this.getName());
		Integer newHeight = this.getNumCellsHeight()!=null ? new Integer(this.getNumCellsHeight()) : null;
		ret.setNumCellsHeightNoException(newHeight);
		Integer newWidth = this.getNumCellsWidth()!=null ? new Integer(this.getNumCellsWidth()) : null;
		ret.setNumCellsWidthNoException(newWidth);
		HashMap<String, ArrayList<SpriteParameterI>> newCategoryMap = new HashMap<String, ArrayList<SpriteParameterI>>();
		ret.replaceCategoryMap(this.categoryMap);
		
		ret.setInventory(new ArrayList<AbstractSpriteObject>(this.getInventory()));
		ret.setAnimationSequences(this.getAnimationSequences());
		ret.setAllActions(this.getAllActions());
		ret.setAllConditions(this.getAllConditions());
		ret.setConditions(this.getConditionTreeViews());
		ret.setActions(this.getActionTreeViews());
		
		return ret;
	}
	
//	protected Object writeReplace() throws java.io.ObjectStreamException {
//		System.out.println("Making using writeRepalce");
//		return new SpriteDataConverter(this);
//	}

//	@Override
//	public void writeExternal(ObjectOutput out) throws IOException {
//		out.writeObject(categoryMap);
//	    out.writeObject(mySpriteObjectInventory);
//	    out.writeObject(myInventoryObjectInventory);
//	    out.writeObject(myImageURL);
//	    out.writeObject(myPositionOnGrid);
//	    out.writeObject(myName);
//	    out.writeObject(myNumCellsWidth);
//	    out.writeObject(myNumCellsHeight);
//	    out.writeObject(myUniqueID);
//	    out.writeObject(mySavePath);
//	    out.writeObject(myAnimationSequences);
//		
//	}
//
//	@Override
//	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//		categoryMap = (HashMap<String, ArrayList<SpriteParameterI>>)in.readObject();
//		mySpriteObjectInventory = (ArrayList<SpriteObject>)in.readObject();
//		myInventoryObjectInventory = (ArrayList<InventoryObject>)in.readObject();
//		myImageURL =(String)in.readObject();
//		myPositionOnGrid = (Integer[])in.readObject();
//		myName = (String) in.readObject();
//		myNumCellsWidth = (Integer) in.readObject();
//		myNumCellsHeight = (Integer) in.readObject();
//		myUniqueID = (String) in.readObject();
//		mySavePath = (String) in.readObject();
//		myAnimationSequences = (ArrayList<AnimationSequence>) in.readObject();	
//	}

}
