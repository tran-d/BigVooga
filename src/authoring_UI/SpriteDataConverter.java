package authoring_UI;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.InventoryObject;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.AnimationSequences.AuthoringAnimationSequence;
import authoring.Sprite.Parameters.SpriteParameter;
import engine.Action;
import engine.Condition;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

public class SpriteDataConverter {

	private static final XStream SERIALIZER = setupXStream();

	private static XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		// xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}

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
//	ObservableList<Integer> allConditions;
//	ObservableList<Integer> allActions;
	
//	Function<Integer, Boolean> heightFunction;
//	Function<Integer, Boolean> widthFunction;

	public SpriteDataConverter(AbstractSpriteObject ASO) {
		convertSprite(ASO);
	}

	public String getName() {
		return name;
	}

	public SpriteObject getSprite(File file) {

		return null;
	}

	public SpriteDataConverter getToSerialize() {
		return this;
	}

	public void setPath(String path) {
		mySavePath = path;
	}

	public void convertSprite(AbstractSpriteObject ASO) {
		System.out.println("convertingASO to SDC");
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
		System.out.println("SDC allConditions: "+ASO.getAllConditions());
//		allConditions = ASO.getAllConditions();
		System.out.println("SDC allAction: "+ASO.getAllActions());
//		allActions = ASO.getAllActions();
		System.out.println("SDC allConditionRows: "+ASO.getConditionRows());
		conditionRows = ASO.getConditionRows();
		System.out.println("SDC allActionRows: "+ASO.getActionRows());
		actionRows = ASO.getActionRows();
		myAnimationSequences = ASO.getAnimationSequences();
		spriteConditionOperations = ASO.getSelectedConditionOperations();
		spriteActionOperations = ASO.getSelectedActionOperations();
		ASO.getInventory().forEach(sprite -> {
			inventory.add(new SpriteDataConverter(sprite));
		});
		if (ASO instanceof SpriteObject) {
			spriteType = "SpriteObject";
		} else if (ASO instanceof InventoryObject) {
			spriteType = "InventoryObject";
		}
	}

	public AbstractSpriteObject createSprite() {
		System.out.println("Trying to convert into sprite");
		AbstractSpriteObject ret = null;
		if (spriteType.equals("SpriteObject")) {
			ret = new SpriteObject(true);
		} else if (spriteType.equals("InventoryObject")) {
			ret = new InventoryObject(true);
		} else {
			ret = new SpriteObject(true);
		}
		ret.setImageURL(imageURL);
		ret.setParameterMap(catmap);
		ret.setPositionOnGrid(gridPos);
		ret.setNumCellsHeightNoException(height);
		ret.setNumCellsWidthNoException(width);
		ret.setUniqueID(UUID);
		ret.setName(name);
		ret.setSavePath(mySavePath);
		ret.setTags(tags);
		ret.setSelectedConditionOperations(spriteConditionOperations);
		ret.setSelectedActionOperations(spriteActionOperations);
//		ret.setAllConditions(allConditions);
//		ret.setAllActions(allActions);
		ret.setConditionRows(conditionRows);
		ret.setActionRows(actionRows);
		System.out.println("SDC AnimationSeq: "+this.myAnimationSequences);
		ret.setAnimationSequences(this.myAnimationSequences);
		List<AbstractSpriteObject> newInventory = new ArrayList<AbstractSpriteObject>();
		inventory.forEach(SDC ->{
			newInventory.add(SDC.createSprite());
		});
		ret.setInventory(newInventory);
		System.out.println("spriteInventoryinSDC: "+ret.getInventory());
		return ret;
	}
	
//	private Object readResolve() throws java.io.ObjectStreamException{
//			System.out.println("Resolving in spritedata converter");
//	        return createSprite();   
//	}
}
