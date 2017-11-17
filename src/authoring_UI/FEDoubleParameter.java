package authoring_UI;

import java.util.Optional;

import authoring.SpriteParameterI;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class FEDoubleParameter extends FEParameter {
	SpriteParameterI myParam;
	FEParameterName myName;
	TextArea myValue;
	
	protected FEDoubleParameter(SpriteParameterI BEParam) {
		myParam = BEParam;
		myName = new FEParameterName(myParam.getName());
		myValue = new TextArea((String) myParam.getValue());
		this.getChildren().addAll(myName, myValue);
		this.setPrefHeight(20);
		
		handleValueChange();
	}
	
	private void handleDouble() {
		myValue.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				myParam.checkError(newValue);
			} catch (Exception e) {
				displayErrorDialog(newValue, e);
				
			}
			myValue.setText(newValue);
		});
	}
	
	private void displayErrorDialog(String value, Exception e) {
		TextInputDialog dialog = new TextInputDialog(value);
		dialog.setTitle("Error");
		dialog.setHeaderText(e.getMessage());
		dialog.setContentText("Please enter value for: " + myName.getText());
		
		ButtonType doneButton = new ButtonType("Done", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(doneButton);
		
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(stringInput -> {
			try {
				myParam.checkError(stringInput);
			} catch (Exception e1) {
				// if error here, do nothing
			}
			myValue.setText(stringInput);
		});
	}
	
	protected void handleValueChange() {
		handleDouble();
	}
	
	protected void updateParameter() {
		myParam.update(myName.getText(), Double.parseDouble(myValue.getText()));
	}

}
