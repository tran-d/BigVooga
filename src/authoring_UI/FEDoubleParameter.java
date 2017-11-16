package authoring_UI;

import authoring.IntegerSpriteParameter;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class FEDoubleParameter extends HBox {
	TextArea myName;
	TextArea myValue;
	
	public FEDoubleParameter(IntegerSpriteParameter BEParam) {
		myName = new TextArea(BEParam.getName());
		myValue = new TextArea((String) BEParam.getValue());
		this.getChildren().addAll(myName, myValue);
		this.setPrefHeight(20);

	}

}
