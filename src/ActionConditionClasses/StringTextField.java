package ActionConditionClasses;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class StringTextField extends TextField implements TextFieldI {
	
	private static final String ERROR = "ERROR";
	
	public StringTextField(String promptText) {
		super();
		setPromptText(promptText);
	}
	
	@Override
	public void displayErrorMessage(String caption,String errorMessage) {
		Alert error = new Alert(AlertType.ERROR);
		error.setTitle(ERROR);
		error.setHeaderText(caption);
		error.setContentText(errorMessage);
	}

	@Override
	public Object getInput() {
		return getText();
	}

}
