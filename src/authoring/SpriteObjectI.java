package authoring;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.ImageView;

public interface SpriteObjectI {

	HashMap<String, ArrayList<SpriteParameterI>> getParameters();
	void addParameter(SpriteParameterI SP);
	void applyParameterUpdate(HashMap<String, ArrayList<SpriteParameterI>> newParams);
	boolean isSame(SpriteObject other);
	SpriteObjectI newCopy();
	ImageView getImageView();
	void setImageURL(String fileLocation);
	Integer[] getPositionOnGrid();
	void setPositionOnGrid(Integer[] pos);
	String getName();
	void setName(String name);
	void changeCategoryName(String prev, String next);
	
}
