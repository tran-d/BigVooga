package authoring_UI;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class FEParameterName extends TextArea {
	
	protected FEParameterName(String name) {
		this.setText(name);
		this.setPrefWidth(170);
		handleNameChange();
	}
	
	private void handleNameChange() {
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			String newText;
			if (newValue.contains("\t")|| newValue.contains(" ")){
				newText = newValue.replaceAll("\\s", "");
				Platform.runLater(() -> { 
					int currentCaretPosition = this.getCaretPosition();
					this.setText(newText);
					this.positionCaret(currentCaretPosition);
				}); 
			} else {
				newText = newValue;
			}
			this.setText(newText);
		});
	}
	
}
