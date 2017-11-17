package authoring_UI;

import javafx.scene.layout.HBox;

public abstract class FEParameter extends HBox {

	protected abstract void handleValueChange();
	
	protected abstract void updateParameter();
	
}
