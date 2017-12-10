package authoring.Sprite.Parameters;

import javafx.scene.layout.HBox;

public abstract class FEParameter extends HBox {

	protected abstract void handleValueChange();
	
	public abstract void updateParameter();
	
}
