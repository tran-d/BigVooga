package authoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SpriteObject extends ImageView implements SpriteObjectI{
	
	private HashMap<String, ArrayList<SpriteParameterI>> categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>();
	private ImageView myImageView;
	private String myImageURL;
	private ActionManager actionManager;
	
	public SpriteObject() {
		
	}

	public SpriteObject(String fileURL){
		myImageURL = fileURL;
		this.setImage(new Image(myImageURL));
		this.setFitWidth(45);
		this.setFitHeight(45);
	}
	
	public SpriteObject(HashMap<String, ArrayList<SpriteParameterI>> inCategoryMap) {
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(inCategoryMap);
	}
	
	SpriteObject(HashMap<String, ArrayList<SpriteParameterI>> inCategoryMap, String fileURL) {
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(inCategoryMap);
		myImageURL = fileURL;
		this.setImage(new Image(myImageURL));
	}
	
	@Override
	public ImageView getImageView(){
		return myImageView;
	}
	
	@Override 
	public void setImageURL(String fileLocation){
		myImageURL = fileLocation;
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
		categoryMap = newParams;
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
	
//	@Override
//	public int hashCode() {
//	    int hashCode = 1;
//	   
//	    for (ArrayList<SpriteParameterI> val: getParameters().values()){
//	    	for (SpriteParameterI SP: val){
//	    	hashCode = hashCode * 37 + SP.hashCode();
//	    	}
//	    }
//
//	    return hashCode;
//	}
	
	@Override
	public void applyUpdates() {
		for (ArrayList<SpriteParameterI> paramList: categoryMap.values()){
			for (SpriteParameterI SPI: paramList){
				SPI.becomeDummy();
			}
		}
	}
	
	@Override
	public SpriteObject newCopy(){
		System.out.println("Making copy");
		if(this.myImageURL!=null) {
		return new SpriteObject(this.categoryMap, this.myImageURL);
		} else {
			return new SpriteObject(this.categoryMap);
		}
	}
	
}
