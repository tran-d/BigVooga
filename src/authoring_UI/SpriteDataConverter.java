package authoring_UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.AnimationSequences.AuthoringAnimationSequence;
import authoring.Sprite.Parameters.SpriteParameter;
import engine.Action;
import engine.Condition;
import engine.utilities.data.GameDataHandler;

public class SpriteDataConverter {

	GameDataHandler myGDH;
	Map<Condition, List<Integer>> conditionRows;
	List<Action> actionRows;
	Map<String, List<SpriteParameter>> catmap;
	List<SpriteDataConverter> inventory;
	String imageURL;
	Integer[] gridPos;
	String name;
	Integer width;
	Integer height;
	String UUID;
	List<AuthoringAnimationSequence> myAnimationSequences;
	List<String> spriteConditionOperations;
	List<List<String>> spriteActionOperations;
	
	
	String mySavePath;
	String spriteType;
	List<String> tags;

	public SpriteDataConverter(AbstractSpriteObject ASO) {
		convertSprite(ASO);
	}

	public String getName() {
		return name;
	}

//	public SpriteObject getSprite(File file) {
//
//		return null;
//	}

	public SpriteDataConverter getToSerialize() {
		return this;
	}

	public void setPath(String path) {
		mySavePath = path;
	}

	public void convertSprite(AbstractSpriteObject ASO) {
		catmap = ASO.getParameters();
		gridPos = ASO.getPositionOnGrid();
		name = ASO.getName();
		width = ASO.getNumCellsWidth();
		height = ASO.getNumCellsHeight();
		UUID = ASO.getUniqueID();
		imageURL = ASO.getImageURL();
		mySavePath = ASO.getSavePath();
		tags = ASO.getTags();
		inventory = new ArrayList<SpriteDataConverter>();
//		allConditions = ASO.getAllConditions();
//		allActions = ASO.getAllActions();
		conditionRows = ASO.getConditionRows();
		actionRows = ASO.getActionRows();
		myAnimationSequences = ASO.getAnimationSequences();
		spriteConditionOperations = ASO.getSelectedConditionOperations();
		System.out.println("Selected Condition Operations: " + spriteConditionOperations);
		spriteActionOperations = ASO.getSelectedActionOperations();
		System.out.println("Selected Action and Category: " + spriteActionOperations);
		ASO.getInventory().forEach(sprite -> {
			inventory.add(new SpriteDataConverter(sprite));
		});
		if (ASO instanceof SpriteObject) {
			spriteType = "SpriteObject";
		} else if (ASO instanceof InventoryObject) {
			spriteType = "InventoryObject";
		}
		myGDH = null;
	}
	
	public void setGameDataHandler(GameDataHandler GDH){
		myGDH = GDH;
	}

	public AbstractSpriteObject createSprite() {
		
		AbstractSpriteObject ret = null;
		
		if (spriteType.equals("SpriteObject")) {
			ret = new SpriteObject(true, myGDH);
		} else if (spriteType.equals("InventoryObject")) {
			ret = new InventoryObject(true, myGDH);
		} else {
			ret = new SpriteObject(true, myGDH);
		}
		ret.setParameterMap(catmap);
		ret.setPositionOnGrid(gridPos);
		myAnimationSequences.forEach(seq->seq.setGameDataHandler(myGDH));
		ret.setAnimationSequences(myAnimationSequences);
		ret.setNumCellsHeightNoException(height);
		ret.setNumCellsWidthNoException(width);
		ret.setUniqueID(UUID);
		ret.setName(name);
		ret.setSavePath(mySavePath);
		ret.setTags(tags);
		ret.setSelectedConditionOperations(spriteConditionOperations);
		System.out.println("selectedConditionOperations yeah " + spriteConditionOperations);
		ret.setSelectedActionOperations(spriteActionOperations);
		System.out.println("selectedActionOperations yeahhhh " + spriteActionOperations);
//		ret.setAllConditions(allConditions);
//		ret.setAllActions(allActions);
		ret.setConditionRows(conditionRows);
		ret.setActionRows(actionRows);
		System.out.println("ActionRows Size: "+actionRows.size());
		System.out.println("SDC AnimationSeq: "+this.myAnimationSequences);
		List<AbstractSpriteObject> newInventory = new ArrayList<AbstractSpriteObject>();
		inventory.forEach(SDC ->{
			newInventory.add(SDC.createSprite());
		});
		ret.setInventory(newInventory);
		System.out.println("spriteInventoryinSDC: "+ret.getInventory());
		ret.setImageURL(imageURL);

		return ret;
	}
	
//	private Object readResolve() throws java.io.ObjectStreamException{
//			;
//	        return createSprite();   
//	}
}
