package authoring.Sprite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import authoring.Sprite.AnimationSequences.AuthoringAnimationSequence;
import authoring.Sprite.AnimationSequences.AuthoringImageView;
import authoring.Sprite.Parameters.BooleanSpriteParameter;
import authoring.Sprite.Parameters.DoubleSpriteParameter;
import authoring.Sprite.Parameters.SpriteParameter;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring.Sprite.Parameters.StringSpriteParameter;
import authoring_actionconditions.ActionTreeView;
import authoring_actionconditions.ConditionTreeView;
import engine.Action;
import engine.Condition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractSpriteObject extends ImageView {

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface IsLockedUtility {
		String readableName();

		String getMethod();
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface IsUnlockedUtility {
		String readableName();

		String getMethod();

		String setMethod();
	}
	
	protected Map<String, List<SpriteParameter>> categoryMap = new HashMap<String, List<SpriteParameter>>();
	protected Map<String, List<SpriteParameter>> possibleCategoryMap = new HashMap<String, List<SpriteParameter>>();;
	protected List<AbstractSpriteObject> myInventory;

	protected Map<Condition, List<Action>> myBehavior = new HashMap<Condition, List<Action>>();

	@IsLockedUtility(readableName = "Image Path: ", getMethod = "getImageURL")
	protected String myImageURL;

	@IsUnlockedUtility(readableName = "Position: ", getMethod = "getMyPositionOnGrid", setMethod = "setMyPositionOnGrid")
	protected Integer[] myPositionOnGrid;

	protected ObjectProperty<Integer[]> position;

	@IsLockedUtility(readableName = "Name: ", getMethod = "getName()")
	protected String myName;

	@IsUnlockedUtility(readableName = "Cell Width: ", getMethod = "getNumCellsWidth", setMethod = "setNumCellsWidth")
	protected Integer myNumCellsWidth;

	protected ObjectProperty<Integer> width;
	protected Function<Integer, Boolean> widthFunction;

	@IsUnlockedUtility(readableName = "Cell Height: ", getMethod = "getNumCellsHeight", setMethod = "setNumCellsHeight")
	protected Integer myNumCellsHeight;

	protected ObjectProperty<Integer> height;
	protected Function<Integer, Boolean> heightFunction;

	@IsLockedUtility(readableName = "UniqueID: ", getMethod = "getUniqueID")
	protected String myUniqueID;

	// @IsLockedUtility(readableName = "Save File", getMethod = "getSavePath")
	protected String mySavePath;
	protected ObservableList<Integer> allConditions;
	protected ObservableList<Integer> allActions;
	protected Map<Condition, List<Integer>> conditionRows;
	protected HashMap<ConditionTreeView, List<Integer>> conditionTreeViews;
	protected List<Action> actionRows;
	protected List<ActionTreeView> actionTreeViews;
	protected List<AuthoringAnimationSequence> myAnimationSequences;
	protected List<String> myTags;

	public AbstractSpriteObject() {
		super();
		initializeVariables();
		setUniqueID();
		initializeActionConditions();
	}

	private void initializeActionConditions() {
		allConditions = FXCollections.observableArrayList();
		allActions = FXCollections.observableArrayList();
		conditionTreeViews = new HashMap<ConditionTreeView, List<Integer>>();
		actionTreeViews = new LinkedList<ActionTreeView>();
		actionRows = new LinkedList<Action>();
		conditionRows = new HashMap<Condition, List<Integer>>();
	}

	private void initializeVariables() {
		myTags = new ArrayList<String>();
		myInventory = new ArrayList<AbstractSpriteObject>();
		myAnimationSequences = new ArrayList<AuthoringAnimationSequence>();
		initializePositionOnGridProperty();
		initializeHeightWidthProperties();
	}

	public AbstractSpriteObject(boolean isRecreation) {
		if (isRecreation) {
			// Nothing
		} else {
			initializeVariables();
			setUniqueID();
		}
	}

	public AbstractSpriteObject(String fileURL) {
		this();
		setupImageURLAndView(fileURL);
		// myName = fileURL.split("\\.")[0];
	}

	public AbstractSpriteObject(Image image, String path) {
		this();
		setupImageURLAndView(image, path);
		// myName = fileURL.split("\\.")[0];
	}

	AbstractSpriteObject(HashMap<String, List<SpriteParameter>> inCategoryMap) {
		this();
		categoryMap = new HashMap<String, List<SpriteParameter>>(inCategoryMap);
	}

	AbstractSpriteObject(HashMap<String, List<SpriteParameter>> inCategoryMap, String fileURL) {
		this();
		categoryMap = new HashMap<String, List<SpriteParameter>>(inCategoryMap);
		setupImageURLAndView(fileURL);
	}

	protected void setUniqueID() {
		if (myUniqueID == null) {
			myUniqueID = SpriteIDGenerator.getInstance().getUniqueID();
		}
	}

	public void setUniqueID(String ID) {
		if (myUniqueID == null) {
			myUniqueID = ID;
		}
	}

	public String getUniqueID() {
		return myUniqueID;
	}

	public List<String> getTags() {
		return this.myTags;
	}

	public void setTags(Collection<String> newTags) {
		if (myTags == null) {
			myTags = new ArrayList<String>();
		}
		this.myTags.clear();
		this.myTags.addAll(newTags);
	}

	public List<AbstractSpriteObject> getInventory() {
		if (myInventory == null) {
			myInventory = new ArrayList<AbstractSpriteObject>();
		}
		return myInventory;
	}

	public void setInventory(Collection<AbstractSpriteObject> inventoryList) {
		if (myInventory == null) {
			myInventory = new ArrayList<AbstractSpriteObject>();
		}
		myInventory.clear();
		myInventory.addAll(inventoryList);
	}

	public void addToInventory(AbstractSpriteObject ASO) {
		myInventory.add(ASO);
	}

	public boolean removeFromInventory(AbstractSpriteObject ASO) {
		if (getInventory().contains(ASO)) {
			getInventory().remove(ASO);
			return true;
		}
		return false;
	}

	public void clearInventory() {
		myInventory.clear();
	}

	protected void setupImageURLAndView(String fileURL) {
		FileInputStream fis;
		Image im;
		try {
			fis = new FileInputStream(new File(fileURL));
			im = new Image(fis);
		} catch (FileNotFoundException e) {
			im = new Image(fileURL);
		}
		setupImageURLAndView(im, fileURL);
	}

	public void setupImageURLAndView(Image image, String path) {
		myImageURL = path;
		this.setImage(image);
		this.setFitWidth(45);
		this.setFitHeight(45);
	}

	private void initializeHeightWidthProperties() {
		width = new SimpleObjectProperty<Integer>();
		initializeWidthFunction();
		height = new SimpleObjectProperty<Integer>();
		initializeHeightFunction();
		if (this.getNumCellsHeight() == null) {
			this.myNumCellsHeight = 1;
			this.height.set(1);
		}
		if (this.getNumCellsWidth() == null) {
			this.myNumCellsWidth = 1;
			this.width.set(1);
		}
	}

	public ObjectProperty<Integer> getWidthObjectProperty() {
		return width;
	}

	private void setWidthObjectProperty(ObjectProperty<Integer> newWidth) throws Exception {
		setWidthObjectProperty(newWidth.get());
	}

	private void setWidthObjectProperty(int newWidth) throws Exception {
		if (width == null) {
			width = new SimpleObjectProperty<Integer>();
		}
		if (this.getWidthFunction().apply(newWidth)) {
			width.set(newWidth);
		} else {
			throw new Exception("Could not set width");
		}
	}

	public void setWidthFunction(Function<Integer, Boolean> newFunction) {
		if (widthFunction == null) {
			initializeWidthFunction();
		}
		widthFunction = newFunction;
	}

	public Function<Integer, Boolean> getWidthFunction() {
		return widthFunction;
	}

	public ObjectProperty<Integer> getHeightObjectProperty() {
		return height;
	}

	private void setHeightObjectProperty(ObjectProperty<Integer> newHeight) throws Exception {
		setHeightObjectProperty(newHeight.get());
	}

	private void setHeightObjectProperty(int newWidth) throws Exception {
		if (height == null) {
			height = new SimpleObjectProperty<Integer>();
		}
		if (this.getHeightFunction().apply(newWidth)) {
			height.set(newWidth);
		} else {
			throw new Exception("Could not change height");
		}
	}

	private void initializeHeightFunction() {
		heightFunction = new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer t) {
				// Default true lets height change
				return true;
			}
		};
	}

	private void initializeWidthFunction() {
		widthFunction = new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer t) {
				// Default true lets width change
				return true;
			}
		};
	}

	public void setHeightFunction(Function<Integer, Boolean> consumer) {
		if (heightFunction == null) {
			initializeHeightFunction();
		}
		heightFunction = consumer;
	}

	public Function<Integer, Boolean> getHeightFunction() {
		return heightFunction;
	}

	public Integer getNumCellsWidth() {
		return myNumCellsWidth;
	}

	public void setNumCellsWidth(Integer in) throws Exception {
		setWidthObjectProperty(in);
		myNumCellsWidth = in;
	}

	public void setNumCellsWidthNoException(Integer in) {
		if (width == null) {
			width = new SimpleObjectProperty<Integer>();
		}
		width.set(in);
		myNumCellsWidth = in;
	}

	public Integer getNumCellsHeight() {
		return myNumCellsHeight;
	}

	public void setNumCellsHeight(Integer in) throws Exception {
		setHeightObjectProperty(in);
		myNumCellsHeight = in;
	}

	public void setNumCellsHeightNoException(Integer in) {
		if (height == null) {
			height = new SimpleObjectProperty<Integer>();
		}
		height.set(in);
		myNumCellsHeight = in;
	}

	protected double[] getCenterCoordinates() {
		return new double[] { getXCenterCoordinate(), getYCenterCoordinate() };
	}

	public double getYCenterCoordinate() {
		double height = getNumCellsHeight();
		double ypos = getRowOnGrid();
		double centery = ypos + height / 2;
		return centery;
	}

	public double getXCenterCoordinate() {
		double width = getNumCellsWidth();
		double xpos = getColumnOnGrid();
		double centerx = xpos + width / 2;
		return centerx;
	}

	public ImageView getImageView() {
		return this;
	}

	public Integer[] getPositionOnGrid() {
		return myPositionOnGrid;
	}

	public Integer getRowOnGrid() {
		return getPositionOnGrid()[0];
	}

	public Integer getColumnOnGrid() {
		return getPositionOnGrid()[1];
	}

	public void setPositionOnGrid(Integer[] pos) {
		setPositionOnGridProperty(pos);
		myPositionOnGrid = pos;
	}

	public void initializePositionOnGridProperty() {
		position = new SimpleObjectProperty<Integer[]>();
	}

	public void setPositionOnGridProperty(ObjectProperty<Integer[]> newProp) {
		setPositionOnGridProperty(newProp.get());
	}

	public void setPositionOnGridProperty(Integer[] newPos) {
		if (position == null) {
			initializePositionOnGridProperty();
		}
		position.set(newPos);
	}

	public ObjectProperty<Integer[]> getPositionOnGridProperty() {
		return position;
	}

	public void setImageURL(String fileLocation) {
		setupImageURLAndView(fileLocation);
	}

	public String getImageURL() {
		return myImageURL;
	}

	public void setName(String name) {
		myName = name;
	}

	public Map<String, List<SpriteParameter>> getParameters() {
		return categoryMap;
	}

	public void addParameter(SpriteParameter SP) {
		addParameter("General", SP);

	}

	public boolean addCategory(String category) {
		if (!categoryMap.containsKey(category)) {
			categoryMap.put(category, new ArrayList<SpriteParameter>());
			return true;
		}
		return false;
	}

	public void addParameter(String category, SpriteParameter SP) {
		addCategory(category);
		List<SpriteParameter> val = categoryMap.get(category);
		val.add(SP);
		categoryMap.put(category, val);
	}

	public void addPossibleParameter(String category, SpriteParameter SP) {
		if (!possibleCategoryMap.containsKey(category)) {
			possibleCategoryMap.put(category, new ArrayList<SpriteParameter>());
		}
		List<SpriteParameter> val = possibleCategoryMap.get(category);
		val.add(SP);
		possibleCategoryMap.put(category, val);
	}

	public Integer acceptPossibleParameters() {
		int ret = 0;
		for (Entry<String, List<SpriteParameter>> keyVal : possibleCategoryMap.entrySet()) {
			String key = keyVal.getKey();
			List<SpriteParameter> val = keyVal.getValue();
			for (SpriteParameter item : val) {
				boolean added = addCategory(key);
				if (added) {
					ret += 1;
				}
				addParameter(key, item);
			}
		}
		return ret;
	}

	public void clearPossibleParameters() {
		this.possibleCategoryMap.clear();
	}

	public void applyParameterUpdate(Map<String, List<SpriteParameter>> map) {
		replaceCategoryMap(map);
	}

	public void setParameterMap(Map<String, List<SpriteParameter>> newParams) {
		replaceCategoryMap(newParams);
	}

	protected void replaceCategoryMap(Map<String,List<SpriteParameter>> newParams) {
//		;


		this.categoryMap = getNewCopyOfCategoryMap(newParams);
		// categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(newParams);
		// ;
	}
	
	protected Map<String, List<SpriteParameter>> getNewCopyOfCategoryMap(Map<String, List<SpriteParameter>> newParams){
		HashMap<String, List<SpriteParameter>> newCategoryMap = new HashMap<String, List<SpriteParameter>>();
		if (newParams!=null){
			newParams.forEach((key, value)->{
				ArrayList<SpriteParameter> params = new ArrayList<SpriteParameter>();
				value.forEach((SpriteParam)->{
					params.add(SpriteParam.newCopy());
				});
				newCategoryMap.put(key, params);
			});
		}
		return newCategoryMap;
	}

	public boolean isSame(AbstractSpriteObject other) {
		if (!(other instanceof AbstractSpriteObject)) {
			return false;
		}
		AbstractSpriteObject otherSO = (AbstractSpriteObject) other;
		Map<String, List<SpriteParameter>> otherMap = otherSO.getParameters();
		Map<String, List<SpriteParameter>> thisMap = this.getParameters();
		for (String category : otherMap.keySet()) {
			if (!thisMap.keySet().contains(category)) {
				return false;
			}
			List<SpriteParameter> otherParamList = otherMap.get(category);
			List<SpriteParameter> thisParamList = new ArrayList<SpriteParameter>(thisMap.get(category));
			if (otherParamList.size() != thisParamList.size()) {
				return false;
			}
			Iterator<SpriteParameter> otherIt = otherParamList.iterator();
			while (otherIt.hasNext()) {
				SpriteParameterI otherSPI = otherIt.next();
				Iterator<SpriteParameter> thisIt = thisParamList.iterator();
				while (thisIt.hasNext()) {
					SpriteParameter thisSPI = thisIt.next();
					if (thisSPI.isSame(otherSPI)) {
						thisIt.remove();
						break;
					}
				}
			}
			if (thisParamList.size() > 0) {
				return false;
			}
		}
		return true;
	}
	

	public abstract AbstractSpriteObject newCopy();

	protected List<SpriteParameter> getSpriteParametersMatching(String type) {
		List<SpriteParameter> ret = new ArrayList<SpriteParameter>();
		Class desiredClass;
		switch (type) {
		case "Boolean":
			desiredClass = BooleanSpriteParameter.class;
			break;
		case "Double":
			desiredClass = DoubleSpriteParameter.class;
			break;
		case "String":
			desiredClass = StringSpriteParameter.class;
			break;
		default:
			desiredClass = SpriteParameter.class;
			break;
		}

		for (SpriteParameter SPI : getAllParameters()) {
			if (SPI.getClass().equals(desiredClass)) {
				ret.add(SPI);
			}
		}
		return ret;
	}

	public List<String> getParameterNamesMatching(String type) {
		List<SpriteParameter> concreteParameters = getSpriteParametersMatching(type);
		List<String> ret = new ArrayList<String>();
		concreteParameters.forEach((item) -> {
			ret.add(item.getName());
		});
		return ret;
	}

	protected List<SpriteParameter> getAllParameters() {
		List<SpriteParameter> ret = new ArrayList<SpriteParameter>();
		for (List<SpriteParameter> SPI_LIST : getParameters().values()) {
			for (SpriteParameter SPI : SPI_LIST) {
				ret.add(SPI);
			}
		}
		return ret;
	}

	public String getName() {
		return myName;
	}

	public void updateCategoryName(String prev, String next) {
		if (getParameters().containsKey(prev)) {
			if (!next.equals(prev)) {
				getParameters().put(next, getParameters().remove(prev));
			}
		} else {
			this.addCategory(next);
		}
	}

	public String getSavePath() {
		return mySavePath;
	}

	public void setSavePath(String path) {
		mySavePath = path;
	}
	
	public List<AuthoringAnimationSequence> getAnimationSequences(){
//		if (myAnimationSequences == null){
//			myAnimationSequences = new ArrayList<AuthoringAnimationSequence>();
//			myAnimationSequences.add(new AuthoringAnimationSequence("Default", new AuthoringImageView(getImageURL())));
//		}
		return myAnimationSequences;
	}

	public List<String> getAnimationSequenceNames() {
		List<String> ret = new ArrayList<String>();
		getAnimationSequences().forEach(sequence -> {
			ret.add(sequence.getName());
		});
		return ret;
	}

	public void setAnimationSequences(List<AuthoringAnimationSequence> animations) {
		myAnimationSequences = animations;
//		= new ArrayList<AuthoringAnimationSequence>();
//		animations.forEach(aniseq->{
//			myAnimationSequences.add(new AuthoringAnimationSequence(aniseq));
//		});
		;
		;
	}

	public void createNewAnimationSequence(String name) {
		myAnimationSequences.add(new AuthoringAnimationSequence(name));
	}

	public AuthoringAnimationSequence getAnimationSequence(String name) {
		AuthoringAnimationSequence AS = getAnimationSequences().stream()
				.filter(sequence -> sequence.getName().equals(name)).findFirst().get();
		return AS;
	}

	public void setAllConditions(ObservableList<Integer> allConditions) {
		this.allConditions = allConditions;
	}

	public void setAllActions(ObservableList<Integer> allActions) {
		this.allActions = allActions;
	}

	public void setConditions(HashMap<ConditionTreeView, List<Integer>> conditionTree) {
		try {
			conditionTreeViews = conditionTree;
			conditionRows = new HashMap<Condition, List<Integer>>();
			for (ConditionTreeView conditionTreeView : conditionTree.keySet()) {
				conditionRows.put(conditionTreeView.getCondition(), conditionTree.get(conditionTreeView));
			}
//			}
		} catch (NullPointerException e) {
			throw e;
		} catch (NumberFormatException e) {
			throw e;
		}

	}

	public void setActions(List<ActionTreeView> actionTree) {
		try {
			actionTreeViews = actionTree;
			actionRows = new ArrayList<Action>();
			for (ActionTreeView actionTreeView : actionTree) {
				actionRows.add(actionTreeView.getAction());
			}
//			}
		} catch (NullPointerException e) {
			throw e;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	public ObservableList<Integer> getAllConditions() {
		return allConditions;
	}

	public ObservableList<Integer> getAllActions() {
		return allActions;
	}

	public HashMap<ConditionTreeView, List<Integer>> getConditionTreeviews() {
		
	if (conditionTreeViews==null){
		conditionTreeViews = new HashMap<ConditionTreeView, List<Integer>>();
	}
		return conditionTreeViews;
	}

	public List<ActionTreeView> getActionTreeViews() {
		if (actionTreeViews==null){
			actionTreeViews = new ArrayList<ActionTreeView>();
		}
		return actionTreeViews;
	}
	
	public void setConditionRows(Map<Condition, List<Integer>> newCondRows){
		conditionRows = newCondRows;
	}
	public void setActionRows(List<Action> newActionRows){
		actionRows = newActionRows;
	}
	
	public Map<Condition, List<Integer>> getConditionRows(){
		return conditionRows;
	}
	public List<Action> getActionRows(){
		return actionRows;
	}

	/**
	 * Converts the Front-end formatting into back-end mapping of Conditons and
	 * Actions.
	 * 
	 * @return Map<Condition, List<Action>>
	 */
	public Map<Condition, List<Action>> conditionActionPairings() {
		Map<Condition, List<Action>> temp = new HashMap<Condition, List<Action>>();
		for (Condition c : conditionRows.keySet()) {
			List<Action> actions = new ArrayList<Action>();
			for (Integer i : conditionRows.get(c)) {
				actions.add(actionRows.get(i-1));
			}
			temp.put(c, actions);
		}

		return temp;
	}

	// protected abstract Object writeReplace() throws ObjectStreamException;

}