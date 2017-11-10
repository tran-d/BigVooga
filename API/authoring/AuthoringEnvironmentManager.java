package authoring;

import java.util.ArrayList;

public interface AuthoringEnvironmentManager {
	
	ArrayList<SpriteObjectI> getDefaultGameSprites();
	ArrayList<SpriteObjectI> getUserDefinedSprites();
}
