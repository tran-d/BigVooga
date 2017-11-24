package authoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteObject extends ImageView implements SpriteObjectI{
	
	private HashMap<String, ArrayList<SpriteParameterI>> categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>();
	private ImageView myImageView;
	private String myImageURL;
	private Integer[] myPositionOnGrid;
	private String myName;

	
	public SpriteObject() {
		
	}

	public SpriteObject(String fileURL){
		setupImageURLAndView(fileURL);
//		myName = fileURL.split(".")[0];
	}
	
	SpriteObject(HashMap<String, ArrayList<SpriteParameterI>> inCategoryMap) {
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(inCategoryMap);
	}
	
	SpriteObject(HashMap<String, ArrayList<SpriteParameterI>> inCategoryMap, String fileURL) {
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(inCategoryMap);
		setupImageURLAndView(fileURL);
	}
	
	private void setupImageURLAndView(String fileURL){
		myImageURL = fileURL;
		this.setImage(new Image(myImageURL));
		this.setFitWidth(45);
		this.setFitHeight(45);
	}
	
	
	@Override
	public ImageView getImageView(){
		return myImageView;
	}
	
	@Override 
	public Integer[] getPositionOnGrid(){
		return myPositionOnGrid;
	}
	
	@Override 
	public void setPositionOnGrid(Integer[] pos){
		myPositionOnGrid = pos;
	}
	
	@Override 
	public void setImageURL(String fileLocation){
		setupImageURLAndView(fileLocation);
	}
	
	@Override
	public void setName(String name){
		myName = name;
	}
	
	@Override
	public HashMap<String, ArrayList<SpriteParameterI>> getParameters() {	
		return categoryMap;
	}

	@Override
	public void addParameter(SpriteParameterI SP) {
		addParameter("General", SP);
		
	}
	
	public void addParameter(String category,SpriteParameterI SP){
		if (!categoryMap.containsKey(category)){
			categoryMap.put(category, new ArrayList<SpriteParameterI>());
		}
		
		ArrayList<SpriteParameterI> val = categoryMap.get(category);
		val.add(SP);
		categoryMap.put(category, val);
	}
	

	@Override
	public void applyParameterUpdate(HashMap<String, ArrayList<SpriteParameterI>> newParams) {
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(newParams);
	}

	@Override
	public boolean isSame(SpriteObjectI other){
		if (!(other instanceof SpriteObject)) {
	        return false;
	    }
		SpriteObject otherSO = (SpriteObject) other;
		System.out.println("Using custom equals method for Sprite Object");
		HashMap<String, ArrayList<SpriteParameterI>> otherMap = otherSO.getParameters();
		HashMap<String, ArrayList<SpriteParameterI>> thisMap = this.getParameters();
		for (String category: otherMap.keySet()){
			if (!thisMap.keySet().contains(category)) { return false;}
			ArrayList<SpriteParameterI> otherParamList = otherMap.get(category);
			ArrayList<SpriteParameterI> thisParamList = new ArrayList<SpriteParameterI>(thisMap.get(category));
			if (otherParamList.size() != thisParamList.size()){
				return false;
			}
			Iterator<SpriteParameterI> otherIt = otherParamList.iterator();
			while (otherIt.hasNext()){
				SpriteParameterI otherSPI = otherIt.next();
				Iterator<SpriteParameterI> thisIt = thisParamList.iterator();
				while (thisIt.hasNext()){
					SpriteParameterI thisSPI = thisIt.next();
					if (thisSPI.isSame(otherSPI)) {
						thisIt.remove();
						break;
					}
				}
			}
			if (thisParamList.size()>0){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public SpriteObject newCopy(){
//		System.out.println("Making copy");
		if(this.myImageURL!=null) {
		return new SpriteObject(this.categoryMap, this.myImageURL);
		} else {
			return new SpriteObject(this.categoryMap);
		}
	}
	
	private ArrayList<SpriteParameterI> getSpriteParametersMatching(String type) {
		ArrayList<SpriteParameterI> ret = new ArrayList<SpriteParameterI>();
		Class desiredClass;
		switch (type){
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
		
		for (SpriteParameterI SPI: getAllParameters()){
			if (SPI.getClass().equals(desiredClass)){
				ret.add(SPI);
			}
		}
		return ret;
	}
	
	public ArrayList<String> getParameterNamesMatching(String type) {
		ArrayList<SpriteParameterI> concreteParameters = getSpriteParametersMatching(type); 
		ArrayList<String> ret = new ArrayList<String>();
		concreteParameters.forEach((item) -> {ret.add(item.getName());});
		return ret;
	}
	
	private ArrayList<SpriteParameterI> getAllParameters() {
		ArrayList<SpriteParameterI> ret = new ArrayList<SpriteParameterI>();
		for (ArrayList<SpriteParameterI> SPI_LIST: getParameters().values()){
			for(SpriteParameterI SPI: SPI_LIST){
				ret.add(SPI);
			}
		}
		return ret;
	}

	@Override
	public String getName() {
		return myName;
	}
	
	
	
}
