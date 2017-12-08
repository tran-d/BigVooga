package authoring;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BooleanSpriteParameter extends SpriteParameter {
	
//	CheckBox myCheckBox;
	Boolean myValue;
//	String myName;
//	Boolean myValue; 
//	BooleanProperty boolProp;
//	BooleanSpriteParameter dummy;
//	boolean isDummy;
	
	BooleanSpriteParameter(){
		
	}
	
	
	BooleanSpriteParameter(String name, Object checkedStatus){
		super(name, checkedStatus);
	}
	
//	BooleanSpriteParameter(String name, Object checkedStatus, Boolean cloneMade){
//		super(name, checkedStatus, cloneMade);
//	}
	
	protected void setUpVariables(String name, Object in){
		myValue = (boolean) in;
		myName = name;
//		myCheckBox = new CheckBox();
//		boolProp = new SimpleBooleanProperty();
//		boolProp.set(checkedStatus);
		
//		 myCheckBox.setSelected(myValue);
//		 setJavaFXValueNode(myCheckBox);
	}
	
//	protected void setCloneHandlers() {
//		myCheckBox.setOnAction((event) -> {
//			boolean isSelected = myCheckBox.isSelected();
//			dummy.updateValue(isSelected);
//			System.out.println("I am dummy: "+this.isDummy);
//			System.out.println("New value: "+ myValue);
//			}
//					);
//	}
	
//	protected void makeClone(){
//		dummy = new BooleanSpriteParameter(this.myName, this.myValue, true);
//	}
	
//	public void update(String newName, Object newValue) {
////		updateName(dummy.getName());
////		updateValue(dummy.getValue());
//		updateName
//		
//		//NEED NEW CODE
//	}
	
	@Override
	public void updateValue(Object value) {
		myValue = (Boolean) value;
//		System.out.println("I am dummy: "+this.isDummy);
//		System.out.println("New value: "+ myValue);
		
	}

	@Override
	public Object getValue() {	
		return myValue;
	}

	@Override
	public boolean checkError(Object value) throws Exception {
		if (!(value instanceof Boolean)){
			throw new Exception("Boolean Parameters must have Boolean values!!");
		}
		return true;
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(myName);
		out.writeObject(myValue);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		myName = (String)in.readObject();
		myValue = (Boolean)in.readObject();
	}
	
	public BooleanSpriteParameter newCopy(){
		return new BooleanSpriteParameter(new String(this.getName()), new Boolean(((Boolean)this.getValue())));
	}


}
