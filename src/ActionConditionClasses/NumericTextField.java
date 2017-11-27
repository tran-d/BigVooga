package ActionConditionClasses;

public class NumericTextField extends StringTextField {
	
	public NumericTextField(String promptText) {
		super(promptText);
		textProperty().addListener((observable,oldText,newText) -> handleNewInput(newText));
	}
	
	@Override
	public Object getInput() {
		return Double.parseDouble(getText());
	}
	
	private void handleNewInput(String newText) {
		 if (!newText.matches("\\d*")) {
	            setText(newText.replaceAll("[^\\d]", ""));
	        }
	}

}