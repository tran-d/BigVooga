package authoring;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;

public class StringSpriteParameter extends SpriteParameter {
	
	String myValue; 
	
	public StringSpriteParameter(String name, Object value){
			super(name, value);	
		}
	

	@Override
	public void updateValue(Object value) {
		myValue = (String) value;
		
	}

	@Override
	public Object getValue() {
		return myValue;
	}


	@Override
	protected void setUpVariables(String name, Object value) {
		myValue = (String) value;
		myName = name;
	
	}


	@Override
	public boolean checkError(Object value) throws Exception {
		if(((String) value).matches(".*\\d+.*")){
			throw new Exception("The input is not a valid string.");
		}
		return true;
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
//
}
