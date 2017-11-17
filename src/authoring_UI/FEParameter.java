package authoring_UI;

import authoring.SpriteParameterI;
import javafx.scene.layout.HBox;

public abstract class FEParameter extends HBox {

	public abstract void handleUserChange();
	
	public abstract void updateParameter();
	
}
