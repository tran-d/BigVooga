package authoring_UI;

import java.util.Optional;

import authoring.SpriteParameterI;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class FEDoubleParameter extends FEParameter {
	SpriteParameterI myParam;
	FEParameterName myName;
	TextArea myValue;
	
	protected FEDoubleParameter(SpriteParameterI BEParam) {
		myParam = BEParam;
		BorderStroke border = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
		myName = new FEParameterName(myParam.getName());
		myName.setBorder(new Border(border));
		myValue = new TextArea(myParam.getValue().toString());
		myValue.setPrefWidth(200);
		myValue.setBorder(new Border(border));
		this.getChildren().addAll(myName, myValue);
		this.setPrefHeight(20);
		this.setSpacing(5);

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
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(e.getMessage());
		alert.setContentText("Please enter value for: " + myName.getText());

		alert.showAndWait();
//		Optional<String> result = dialog.showAndWait();
//		result.ifPresent(stringInput -> {
//			try {
//				myParam.checkError(stringInput);
//			} catch (Exception e1) {
//				// if error here, do nothing
//			}
//			myValue.setText(stringInput);
//		});
	}
	
	protected void handleValueChange() {
		handleDouble();
	}
	
	protected void updateParameter() {
		myParam.update(myName.getText(), Double.parseDouble(myValue.getText()));
	}

}
