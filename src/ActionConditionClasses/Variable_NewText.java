package ActionConditionClasses;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public abstract class Variable_NewText extends HBox implements ChoiceBoxI<String>, TextFieldI {
	
	private static final String ERROR_DIALOG = "ERROR";
	
	private ChoiceBox<String> stringVariables;
	private TextField textField;
	
	public Variable_NewText() {
		stringVariables = new ChoiceBox<String>();
		textField = new TextField();
		getChildren().addAll(stringVariables,textField);
	}
	
	@Override
	public void addListChangeListener(ListChangeListener<String> c) {
		stringVariables.getItems().addListener(c);
	}
	
	@Override
	public void setOptions(ObservableList<String> list) {
		stringVariables.setItems(list);
	}
	
	@Override
	public Object getCurrentChoice() {
		return stringVariables.getValue();
	}
	
	@Override
	public String getTextFieldValue() {
		return textField.getText();
	}
	
	@Override
	public void displayErrorMessage(String caption,String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(ERROR_DIALOG);
		alert.setHeaderText(caption);
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}
}
