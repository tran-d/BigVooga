package authoring;

import javafx.scene.control.Control;

public interface SpriteParameter {
	
	void updateName(String name);
	void updateValue(Object value);
	Control getJavaFXNode();
	
}
