package authoring;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.ImageView;

public interface SpriteObjectI {

	HashMap<String, ArrayList<SpriteParameterI>> getParameters();
	void addParameter(SpriteParameterI SP);
	void applyParameterUpdate(HashMap<String, ArrayList<SpriteParameterI>> newParams);
	boolean isSame(SpriteObjectI other);
	SpriteObjectI newCopy();
	ImageView getImageView();
	void setImageURL(String fileLocation);
	Integer[] getPositionOnGrid();
	
}
