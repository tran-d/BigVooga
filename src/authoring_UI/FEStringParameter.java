package authoring_UI;

import authoring.StringSpriteParameter;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class FEStringParameter extends HBox {
	TextArea myName;
	TextArea myValue;
	
	public FEStringParameter(StringSpriteParameter BEParam) {
		myName = new TextArea(BEParam.getName());
		myValue = new TextArea((String) BEParam.getValue());
		this.getChildren().addAll(myName, myValue);
		this.setPrefHeight(20);
		
		handleValue(BEParam);
	}
	
	private void handleValue(StringSpriteParameter BEParam) {
		myValue.textProperty().addListener((observable, oldValue, newValue) -> {
			
			
		
//			String errorMessage = "Please enter a valid input:\n";
////			if (!newValue.equals(errorMessage)|| newValue.startsWith(errorMessage)){
//			try{
//				if (newValue.startsWith(errorMessage)&&!newValue.equals(errorMessage)){
//					System.out.println("In if");
//					String newText = newValue.replace(errorMessage, "").trim();
//					
//					
//					Platform.runLater(() -> { 
//						myValue.setText(newText);; 
//			        }); 
//				}
//				else {
//					System.out.println("In else");
//					BEParam.updateValue(newValue);
//				}
//
//			} catch (Exception e){
//				myValue.setText(errorMessage);
//			}
////			}
//			System.out.println("End of method");
		});
	}
}
