package authoring_UI;

import authoring.SpriteParameterI;
import javafx.scene.control.CheckBox;

public class FEBooleanParameter extends FEParameter {
	SpriteParameterI myParam;
	FEParameterName myName;	
	CheckBox myCheckBox;
	Boolean myValue;
	
	protected FEBooleanParameter(SpriteParameterI BEParam) {
		myParam = BEParam;
		myName = new FEParameterName(myParam.getName());
		myCheckBox = new CheckBox();
		myValue = (Boolean) myParam.getValue();
		myCheckBox.setSelected(myValue);
		this.getChildren().addAll(myName, myCheckBox);
		this.setPrefHeight(20);

		handleValueChange();
	}
	
	private void handleCheckBox() {
		myCheckBox.setOnAction((event) -> {
			boolean isSelected = myCheckBox.isSelected();
			myCheckBox.setSelected(isSelected);
			myValue = isSelected;
		});
	}
	
	protected void handleValueChange() {
		handleCheckBox();
	}
	
	protected void updateParameter() {
		myParam.update(myName.getText(), myValue);
	}
	
	

}

