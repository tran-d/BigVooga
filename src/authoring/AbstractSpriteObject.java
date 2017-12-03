package authoring;

import java.io.File;
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
	protected ArrayList<AbstractSpriteObject> myInventory;

	// protected ImageView myImageView;
	@IsLockedUtility(
			readableName = "Image Path",
			getMethod = "getImagueURL")
	protected String myImageURL;
	@IsUnlockedUtility (
			readableName = "Position",
			getMethod = "getMyPositionOnGrid",
			setMethod = "setMyPositionOnGrid"
			)
	
	protected Integer[] myPositionOnGrid;
	ObjectProperty<Integer[]> position;
	
	@IsLockedUtility(
		readableName = "Name",
				getMethod = "getName()"
	)
	protected String myName;
	@IsUnlockedUtility(
			readableName = "Cell Width",
			getMethod = "getNumCellsWidth",
			setMethod = "setNumCellsWidth"
			)
	ObjectProperty<Integer> width;
	Consumer<Integer> widthConsumer;
	protected int myNumCellsWidth;
	@IsUnlockedUtility(
			readableName = "Cell Height",
			getMethod = "getNumCellsHeight",
			setMethod = "setNumCellsHeight"
			)
	ObjectProperty<Integer> height;
	Consumer<Integer> heightConsumer;
	protected int myNumCellsHeight;
	@IsLockedUtility(
			readableName = "UniqueID",
			getMethod = "getUniqueID"
			)
	
	protected String myUniqueID;
	@IsLockedUtility(
			readableName = "Save File",
			getMethod = "getSavePath")
	protected String mySavePath;

	public AbstractSpriteObject() {
		super();
		initializePositionOnGridProperty();
		initializeHeightWidthProperties();
		setUniqueID();
	}

	public AbstractSpriteObject(boolean isRecreation) {
		if (isRecreation) {
			// Nothing
		} else {
			initializePositionOnGridProperty();
			setUniqueID();
		}
	}

	public AbstractSpriteObject(String fileURL) {
		this();
		setupImageURLAndView(fileURL);
		System.out.println(fileURL);
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
		this.setImage(new Image(myImageURL));
		this.setFitWidth(45);
		this.setFitHeight(45);
	}
	
	private void initializeHeightWidthProperties(){
		width = new SimpleObjectProperty<Integer>();
		widthConsumer = new Consumer<Integer>(){

			@Override
			public void accept(Integer t) {
				// nothing
				
			}
			
		};
		width.addListener((change, previous, next)->{getWidthConsumer().accept(next);});
		height = new SimpleObjectProperty<Integer>();
		heightConsumer = new Consumer<Integer>(){

			@Override
			public void accept(Integer t) {
				// nothing
				
			}
			
		};
		height.addListener((change, previous, next)->{getHeightConsumer().accept(next);});
	}
	
	public ObjectProperty<Integer> getWidthObjectProperty(){
		return width;
	}
	private void setWidthObjectProperty(ObjectProperty<Integer> newWidth){
		width = newWidth;
	}
	
	public void setWidthConsumer(Consumer<Integer> consumer){
		widthConsumer = consumer;
	}
	
	public Consumer<Integer> getWidthConsumer(){
		return widthConsumer;
	}
	public ObjectProperty<Integer> getHeightObjectProperty(){
		return height;
	}
	private void setHeightObjectProperty(ObjectProperty<Integer> newHeight){
		height= newHeight;
	}
	
	public void setHeightConsumer(Consumer<Integer> consumer){
		heightConsumer = consumer;
	}
	
	public Consumer<Integer> getHeightConsumer(){
		return heightConsumer;
	}

	public int getNumCellsWidth() {
		return myNumCellsWidth;
	}

	public void setNumCellsWidth(int in) {
		width.set(in);
		myNumCellsWidth = in;
	}

	public int getNumCellsHeight() {
		return myNumCellsHeight;
	}

	public void setNumCellsHeight(int in) {
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

	public int getRowOnGrid() {
		return getPositionOnGrid()[0];
	}

	public int getColumnOnGrid() {
		return getPositionOnGrid()[1];
	}

	public void setPositionOnGrid(Integer[] pos) {
		position.set(pos);
		myPositionOnGrid = pos;
	}
	
	public void initializePositionOnGridProperty(){
		position = new SimpleObjectProperty<Integer[]>();
	}
	
	public void setPositionOnGridProperty(ObjectProperty<Integer[]> newProp){
		position =newProp;
	}
	
	public ObjectProperty<Integer[]> getPositionOnGridProperty(){
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
			System.out.println("Catgeory added: categoryMap is " + categoryMap);
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

	public int acceptPossibleParameters() {
		int ret = 0;
		for (Entry<String, ArrayList<SpriteParameterI>> keyVal : possibleCategoryMap.entrySet()) {
			String key = keyVal.getKey();

			ArrayList<SpriteParameterI> val = keyVal.getValue();
			System.out.println("Key: " + key + ", Val: " + val);
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
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(newParams);
	}

	public boolean isSame(AbstractSpriteObject other) {
		if (!(other instanceof AbstractSpriteObject)) {
			return false;
		}
		AbstractSpriteObject otherSO = (AbstractSpriteObject) other;
		System.out.println("Using custom equals method for Sprite Object");
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
		System.out.println("updatecategories: " + this.categoryMap);

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

}
