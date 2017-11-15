package voogasalad_bigvooga;

import java.util.ArrayList;
import java.util.HashMap;
import engine.Action;

public class SpriteObject implements SpriteObjectI{
	
	private HashMap<String, ArrayList<SpriteParameterI>> categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>();
	private HashMap<Boolean,Action> myConditionActions = new HashMap<Boolean,Action>();
	
	public SpriteObject() {};
	
	public SpriteObject(HashMap<String, ArrayList<SpriteParameterI>> inCategoryMap) {
		categoryMap = new HashMap<String, ArrayList<SpriteParameterI>>(inCategoryMap);
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
	public boolean equals(SpriteObject other){
		if (!(other instanceof SpriteObject)) {
	        return false;
	    }
		SpriteObject otherSO = (SpriteObject) other;
		System.out.println("Using custom equals method for Sprite Object");
		HashMap<String, ArrayList<SpriteParameterI>> otherMap = otherSO.getParameters();
		HashMap<String, ArrayList<SpriteParameterI>> thisMap = this.getParameters();
		return thisMap.equals(otherMap);
	}
	
	@Override
	public int hashCode() {
	    int hashCode = 1;
	   
	    for (ArrayList<SpriteParameterI> val: getParameters().values()){
	    	for (SpriteParameterI SP: val){
	    	hashCode = hashCode * 37 + SP.hashCode();
	    	}
	    }

	    return hashCode;
	}
	
	@Override
	public SpriteObject newCopy(){
		return new SpriteObject(this.categoryMap);
	}

	@Override
	public void addConditionAction(Boolean Condition, Action action) {
		myConditionActions.put(Condition, action);
	}
	
}
