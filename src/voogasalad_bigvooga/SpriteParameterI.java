package voogasalad_bigvooga;

import java.util.ArrayList;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

public interface SpriteParameterI{
	
	void updateName(String name);
	String getName();
	void updateValue(Object value);
	Control getJavaFXValueNode();
	Object getValue();
	Control getJavaFXNameNode();
	Pane getJavaFXPane();
	boolean isSame(SpriteParameterI other);
	SpriteParameterI getDummy();
	void becomeDummy();
	
}
