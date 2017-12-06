package authoring;

import java.io.File;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;

import authoring_UI.AuthoringImageView;
import authoring_UI.SpriteDataConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	protected HashMap<String, ArrayList<SpriteParameterI>> categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>();
	protected HashMap<String, ArrayList<SpriteParameterI>> possibleCategoryMap = new HashMap<String, ArrayList<SpriteParameterI>>();;
//	protected ArrayList<SpriteObject> mySpriteObjectInventory;
//	protected ArrayList<InventoryObject> myInventoryObjectInventory;
	protected ArrayList<AbstractSpriteObject> myInventory;

	// protected ImageView myImageView;
	@IsLockedUtility(readableName = "Image Path", getMethod = "getImageURL")
	protected String myImageURL;

	@IsUnlockedUtility(readableName = "Position", getMethod = "getMyPositionOnGrid", setMethod = "setMyPositionOnGrid")
	protected Integer[] myPositionOnGrid;

	protected ObjectProperty<Integer[]> position;

	@IsLockedUtility(readableName = "Name", getMethod = "getName()")
	protected String myName;

	@IsUnlockedUtility(readableName = "Cell Width", getMethod = "getNumCellsWidth", setMethod = "setNumCellsWidth")
	protected Integer myNumCellsWidth;

	protected ObjectProperty<Integer> width;
	protected Function<Integer, Boolean> widthFunction;

	@IsUnlockedUtility(readableName = "Cell Height", getMethod = "getNumCellsHeight", setMethod = "setNumCellsHeight")
	protected Integer myNumCellsHeight;

	protected ObjectProperty<Integer> height;
	protected Function<Integer, Boolean> heightFunction;

	@IsLockedUtility(readableName = "UniqueID", getMethod = "getUniqueID")
	protected String myUniqueID;

	@IsLockedUtility(readableName = "Save File", getMethod = "getSavePath")
	protected String mySavePath;
	
	protected ArrayList<AnimationSequence> myAnimationSequences;

	public AbstractSpriteObject() {
		super();
		initializeVariables();
		setUniqueID();
	}

	private void initializeVariables() {
		myInventory = new ArrayList<AbstractSpriteObject>();
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
//		System.out.println(fileURL);
		myName = fileURL.split("\\.")[0];
		// myName = fileURL.split(".")[0];
	}

	AbstractSpriteObject(HashMap<String, ArrayList<SpriteParameterI>> inCategoryMap) {
		this();
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(inCategoryMap);
	}

	AbstractSpriteObject(HashMap<String, ArrayList<SpriteParameterI>> inCategoryMap, String fileURL) {
		this();
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(inCategoryMap);
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

	public ArrayList<AbstractSpriteObject> getInventory() {
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
		myImageURL = fileURL;
//		System.out.println("fileURL: "+ fileURL);
		this.setImage(new Image(fileURL));
//		"resources"+File.pathSeparator+
		this.setFitWidth(45);
		this.setFitHeight(45);
	}

	private void initializeHeightWidthProperties() {
		width = new SimpleObjectProperty<Integer>();
		initializeWidthFunction();
//		width.addListener((change, previous, next) -> {
//			if (!getWidthFunction().apply(next)) {
////				width.set(previous);
//				this.setNumCellsWidth(previous);
//			}
//			;
//		});
		height = new SimpleObjectProperty<Integer>();
		initializeHeightFunction();
//		height.addListener((change, previous, next) -> {
//			if (!getHeightFunction().apply(next)) {
////				height.set(previous);
//				this.setNumCellsHeight(previous);
//			}
//		});
		if (this.getNumCellsHeight()==null ){
			this.height.set(1);
		}
		
		if (this.getNumCellsWidth()== null){
			this.width.set(1);
		}
	}

	public ObjectProperty<Integer> getWidthObjectProperty() {
		return width;
	}

	private void setWidthObjectProperty(ObjectProperty<Integer> newWidth) throws Exception{
		setWidthObjectProperty(newWidth.get());
	}

	private void setWidthObjectProperty(int newWidth) throws Exception{
		if (width == null) {
			width = new SimpleObjectProperty<Integer>();
		}
		if (this.getWidthFunction().apply(newWidth)){
		width.set(newWidth);
		} else {
			throw new Exception("Could not set width");
		}
	}

	public void setWidthFunction(Function<Integer, Boolean> newFunction) {
//		System.out.println("Width consumer updated");
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

	private void setHeightObjectProperty(ObjectProperty<Integer> newHeight) throws Exception{
		setHeightObjectProperty(newHeight.get());
	}

	private void setHeightObjectProperty(int newWidth) throws Exception{
		if (height == null) {
			height = new SimpleObjectProperty<Integer>();
		}
		if (this.getHeightFunction().apply(newWidth)){
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
//		System.out.println("Setting height consumer");
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

	public void setNumCellsWidth(Integer in) throws Exception{
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

	public void setNumCellsHeight(Integer in) throws Exception{
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
		// if (this.getImage() == null){
		// setupImageURLAndView(getImageURL());
		// }
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
		// System.out.println("myImageURL: "+myImageURL);
		// File f = new File("brick.png");
		// System.out.println("File:\n" + f.toURI().toString());
		// Image image = new Image(f.toURI().toString());
		// Image image = null;
		// try {
		// image = new Image(myImageURL);
		// Alert al = new Alert(AlertType.INFORMATION);
		// al.setGraphic(new ImageView(image));
		// al.showAndWait();
		// } catch (Exception e) {
		// //
		// }
		// System.out.println("getImage().file: "+this.getImage().get);
		return myImageURL;
	}

	public void setName(String name) {
		myName = name;
	}

	public HashMap<String, ArrayList<SpriteParameterI>> getParameters() {
		return categoryMap;
	}

	public void addParameter(SpriteParameterI SP) {
		addParameter("General", SP);

	}

	public boolean addCategory(String category) {
		if (!categoryMap.containsKey(category)) {
			categoryMap.put(category, new ArrayList<SpriteParameterI>());
//			System.out.println("Catgeory added: categoryMap is " + categoryMap);
			return true;
		}
		return false;
	}

	public void addParameter(String category, SpriteParameterI SP) {
		addCategory(category);
		ArrayList<SpriteParameterI> val = categoryMap.get(category);
		val.add(SP);
		categoryMap.put(category, val);
	}

	public void addPossibleParameter(String category, SpriteParameterI SP) {
		if (!possibleCategoryMap.containsKey(category)) {
			possibleCategoryMap.put(category, new ArrayList<SpriteParameterI>());
		}

		ArrayList<SpriteParameterI> val = possibleCategoryMap.get(category);
		val.add(SP);
		possibleCategoryMap.put(category, val);
	}

	public Integer acceptPossibleParameters() {
		int ret = 0;
		for (Entry<String, ArrayList<SpriteParameterI>> keyVal : possibleCategoryMap.entrySet()) {
			String key = keyVal.getKey();

			ArrayList<SpriteParameterI> val = keyVal.getValue();
//			System.out.println("Key: " + key + ", Val: " + val);
			for (SpriteParameterI item : val) {
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

	public void applyParameterUpdate(HashMap<String, ArrayList<SpriteParameterI>> newParams) {
		replaceCategoryMap(newParams);
	}

	public void setParameterMap(HashMap<String, ArrayList<SpriteParameterI>> newParams) {
		replaceCategoryMap(newParams);
	}

	protected void replaceCategoryMap(HashMap<String, ArrayList<SpriteParameterI>> newParams) {
		System.out.println("Replacing cat map");
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(newParams);
	}

	public boolean isSame(AbstractSpriteObject other) {
		if (!(other instanceof AbstractSpriteObject)) {
			return false;
		}
		AbstractSpriteObject otherSO = (AbstractSpriteObject) other;
//		System.out.println("Using custom equals method for Sprite Object");
		HashMap<String, ArrayList<SpriteParameterI>> otherMap = otherSO.getParameters();
		HashMap<String, ArrayList<SpriteParameterI>> thisMap = this.getParameters();
		for (String category : otherMap.keySet()) {
			if (!thisMap.keySet().contains(category)) {
				return false;
			}
			ArrayList<SpriteParameterI> otherParamList = otherMap.get(category);
			ArrayList<SpriteParameterI> thisParamList = new ArrayList<SpriteParameterI>(thisMap.get(category));
			if (otherParamList.size() != thisParamList.size()) {
				return false;
			}
			Iterator<SpriteParameterI> otherIt = otherParamList.iterator();
			while (otherIt.hasNext()) {
				SpriteParameterI otherSPI = otherIt.next();
				Iterator<SpriteParameterI> thisIt = thisParamList.iterator();
				while (thisIt.hasNext()) {
					SpriteParameterI thisSPI = thisIt.next();
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

	protected ArrayList<SpriteParameterI> getSpriteParametersMatching(String type) {
		ArrayList<SpriteParameterI> ret = new ArrayList<SpriteParameterI>();
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

		for (SpriteParameterI SPI : getAllParameters()) {
			if (SPI.getClass().equals(desiredClass)) {
				ret.add(SPI);
			}
		}
		return ret;
	}

	public ArrayList<String> getParameterNamesMatching(String type) {
		ArrayList<SpriteParameterI> concreteParameters = getSpriteParametersMatching(type);
		ArrayList<String> ret = new ArrayList<String>();
		concreteParameters.forEach((item) -> {
			ret.add(item.getName());
		});
		return ret;
	}

	protected ArrayList<SpriteParameterI> getAllParameters() {
		ArrayList<SpriteParameterI> ret = new ArrayList<SpriteParameterI>();
		for (ArrayList<SpriteParameterI> SPI_LIST : getParameters().values()) {
			for (SpriteParameterI SPI : SPI_LIST) {
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
//		System.out.println("updatecategories: " + this.categoryMap);

	}

	// protected HashMap<String, ArrayList<SpriteParameterI>> categoryMap = new
	// HashMap<String, ArrayList<SpriteParameterI>>();
	// protected HashMap<String, ArrayList<SpriteParameterI>>
	// possibleCategoryMap
	// = new HashMap<String, ArrayList<SpriteParameterI>>();;
	// // protected ImageView myImageView;
	// protected String myImageURL;
	// protected Integer[] myPositionOnGrid;
	// protected String myName;
	// protected double myNumCellsWidth;
	// protected double myNumCellsHeight;
	// protected String myUniqueID;
	// public Field[] getInfoToSerialize(){
	// Field[] f = this.getClass().getDeclaredFields();
	// for (int i=0;i<f.length;i++){
	// System.out.println(f.g)
	// System.out.println(f[i]);
	// }
	// System.out.println();
	// return f;
	// }

	public String getSavePath() {
		return mySavePath;
	}

	public void setSavePath(String path) {
		mySavePath = path;
	}
	
	public ArrayList<AnimationSequence> getAnimationSequences(){
		if (myAnimationSequences == null){
			myAnimationSequences = new ArrayList<AnimationSequence>();
			myAnimationSequences.add(new AnimationSequence("Default", new AuthoringImageView(getImageURL())));
		}
		return myAnimationSequences;
	}
	
	public void setAnimationSequences(ArrayList<AnimationSequence> animations){
		myAnimationSequences = animations;
	}
	
	public void createNewAnimationSequence(String name){
		myAnimationSequences.add(new AnimationSequence(name));
	}
	
	public AnimationSequence getAnimationSequence(String name){
		AnimationSequence AS = getAnimationSequences().stream()
								.filter(sequence -> sequence.getName().equals(name))
								.findFirst()
								.get();
		return AS;
	}

	
//	protected abstract Object writeReplace() throws ObjectStreamException;

}
