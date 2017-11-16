package authoring_UI;

import authoring.BooleanSpriteParameter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class FEBooleanParameter extends HBox {
	TextArea myName;
	CheckBox myCheckBox;
	Boolean myValue;
	
	public FEBooleanParameter(BooleanSpriteParameter BEParam) {
		myName = new TextArea(BEParam.getName());
		myCheckBox = new CheckBox();
		myValue = (Boolean) BEParam.getValue();
		myCheckBox.setSelected(myValue);
		this.getChildren().addAll(myName, myCheckBox);
		this.setPrefHeight(20);

		handleCheckBox(BEParam);
	}
	
	private void handleCheckBox(BooleanSpriteParameter BEParam) {
		myCheckBox.setOnAction((event) -> {
			boolean isSelected = myCheckBox.isSelected();
			myCheckBox.setSelected(isSelected);
			// update when Apply is pressed
//			BEParam.updateValue(isSelected);
		});
	}
	
	

}

