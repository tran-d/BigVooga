package voogasalad_bigvooga;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;

public class StringSpriteParameter extends SpriteParameter {
	
	private String myValue; 
	private TextArea myTextArea;
	
	StringSpriteParameter(String name, Object value){
			super(name, value);	
		}
	

	public StringSpriteParameter(String name, Object value, Boolean isDummy) {
		super(name, value, isDummy);
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
		updateName(name);
		
		myTextArea = new TextArea();
		myTextArea.setText(myValue);	
		setJavaFXValueNode(myTextArea);
	}


	@Override
	protected void setCloneHandlers() {
		myTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("Old Value: "+oldValue);
			System.out.println("New Value: "+newValue);
			String errorMessage = "Please enter a valid input:\n";
//			if (!newValue.equals(errorMessage)|| newValue.startsWith(errorMessage)){
			try{
				if (newValue.startsWith(errorMessage)&&!newValue.equals(errorMessage)){
					System.out.println("In if");
					String newText = newValue.replace(errorMessage, "").trim();
					
					
					Platform.runLater(() -> { 
						myTextArea.setText(newText);; 
			        }); 
				}
				else {
					System.out.println("In else");
					updateValue(newValue);
				}

			} catch (Exception e){
				myTextArea.setText(errorMessage);
			}
//			}
			System.out.println("End of method");
		});

		
	}

}
