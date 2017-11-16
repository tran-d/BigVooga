package authoring;

import java.util.Observable;

import javafx.application.Platform;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;

public class DoubleSpriteParameter extends SpriteParameter {
	
//	String myName;
	Double myValue; 
//	TextArea myTextArea;
	
	DoubleSpriteParameter(String name, Object value){
		super(name, value);
		}
	

//	public IntegerSpriteParameter(String name, Integer value, Boolean isDummy) {
//		super(name, value, isDummy);
//	}


	@Override
	public void updateValue(Object value) {
		if (value instanceof String){
			try {
				myValue = Double.parseDouble((String) value);
				System.out.println("myValue in updateValue: "+myValue);
			} catch (Exception e){
				throw e;
			}
		} else if (value instanceof Double) {
			myValue = (Double) value;
		} else if (value instanceof Integer) {
			myValue = (Double) value;
		} else {
			throw new RuntimeException("Double Parameter must have a double as a value.");
		}
		
	}



	@Override	
	protected void setUpVariables(String name, Object in){
		if (!(in instanceof Double)){
			throw new RuntimeException("Double Parameter must have a double as a value.");
		}
		myValue = (Double) in;
		myName = name;
		
//		myTextArea = new TextArea();
//		myTextArea.setText(myValue.toString());
		
		
//		boolProp = new SimpleBooleanProperty();
//		boolProp.set(checkedStatus);
		
//		 myCheckBox.setSelected(checkedStatus);
//		 setJavaFXValueNode(myTextArea);
	}


//	@Override
//	protected void setCloneHandlers() {
//		myTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
//			System.out.println("Old Value: "+oldValue);
//			System.out.println("New Value: "+newValue);
//			String errorMessage = "Please enter a valid input:\n";
////			if (!newValue.equals(errorMessage)|| newValue.startsWith(errorMessage)){
//			try{
//				if (newValue.startsWith(errorMessage)&&!newValue.equals(errorMessage)){
//					System.out.println("In if");
//					String newText = newValue.replace(errorMessage, "").trim();
//					
//					
//					Platform.runLater(() -> { 
//						myTextArea.setText(newText);; 
//			        }); 
//				}
//				else {
//					System.out.println("In else");
//					dummy.updateValue(newValue);
//				}
//
//			} catch (Exception e){
//				myTextArea.setText(errorMessage);
//			}
////			}
//			System.out.println("End of method");
//		});
//
//		
//	}


	@Override
	public Object getValue() {
		return myValue;
	}


	@Override
	public boolean checkError(Object value) throws Exception{
		
		if(!(value instanceof String)){
			throw new Exception("The input is not valid");
		}
		
		String valString = (String) value;
		valString = valString.trim();
		Double valDouble;
		
		try{
			valDouble = Double.parseDouble(valString);
		} catch (Exception e){
			throw new Exception(String.format("The input %s is not a valid double.", valString));
		}
		
//			String errorMessage = "Please enter a valid input:\n";
//			if (!newValue.equals(errorMessage)|| newValue.startsWith(errorMessage)){
//			try{
//				if (newValue.startsWith(errorMessage)&&!newValue.equals(errorMessage)){
//					System.out.println("In if");
//					String newText = newValue.replace(errorMessage, "").trim();
//					
//					
//					Platform.runLater(() -> { 
//						myTextArea.setText(newText);; 
//			        }); 
//				}
//				else {
//					System.out.println("In else");
//					dummy.updateValue(newValue);
//				}
//
//			} catch (Exception e){
//				myTextArea.setText(errorMessage);
//			}
////			}
//			System.out.println("End of method");
		
		return true;
	}
	

}
