package voogasalad_bigvooga;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

public class BooleanSpriteParameter extends SpriteParameter {
	
	CheckBox myCheckBox;
	Boolean myValue;
//	String myName;
//	Boolean myValue; 
//	BooleanProperty boolProp;
//	BooleanSpriteParameter dummy;
//	boolean isDummy;
	
	BooleanSpriteParameter(String name, Object checkedStatus){
		super(name, checkedStatus);
	}
	
	BooleanSpriteParameter(String name, Object checkedStatus, Boolean cloneMade){
		super(name, checkedStatus, cloneMade);
	}
	
	protected void setUpVariables(String name, Object in){

		myValue = (boolean) in;
		myName = name;
		myCheckBox = new CheckBox();
//		boolProp = new SimpleBooleanProperty();
//		boolProp.set(checkedStatus);
		
		 myCheckBox.setSelected(myValue);
		 setJavaFXValueNode(myCheckBox);
	}
	
	protected void setCloneHandlers() {
		myCheckBox.setOnAction((event) -> {
			boolean isSelected = myCheckBox.isSelected();
			dummy.updateValue(isSelected);
			System.out.println("I am dummy: "+this.isDummy);
			System.out.println("New value: "+ myValue);
			}
					);
	}
	
//	private void makeClone(){
//		dummy = new BooleanSpriteParameter(this.myName, this.myValue, true);
//	}
	
	public void update() {
		updateName(dummy.getName());
		updateValue(dummy.getValue());
	}
	
	@Override
	public void updateValue(Object value) {
		myValue = (Boolean) value;
		System.out.println("I am dummy: "+this.isDummy);
		System.out.println("New value: "+ myValue);
		
	}

	@Override
	public Object getValue() {
		
		return myValue;
	}



}
