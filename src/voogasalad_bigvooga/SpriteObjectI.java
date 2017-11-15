package voogasalad_bigvooga;

import java.util.ArrayList;
import java.util.HashMap;

public interface SpriteObjectI {

	HashMap<String, ArrayList<SpriteParameterI>> getParameters();
	void addParameter(SpriteParameterI SP);
	void applyParameterUpdate(HashMap<String, ArrayList<SpriteParameterI>> newParams);
	boolean equals(SpriteObject other);
	SpriteObjectI newCopy();
	
}
