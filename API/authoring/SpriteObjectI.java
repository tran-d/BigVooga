package authoring;

import java.util.ArrayList;

public interface SpriteObjectI {

	ArrayList<SpriteParameter> getParameters();
	void addParameter(SpriteParameter SP);
	boolean applyParameterUpdate(ArrayList<SpriteParameter> NewParams);
	
}
