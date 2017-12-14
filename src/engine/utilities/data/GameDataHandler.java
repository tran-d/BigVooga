package engine.utilities.data;

import java.util.function.Supplier;

import javafx.stage.Stage;

public class GameDataHandler extends LocalGameDataHandler {
	
	public GameDataHandler(Stage s) {
		super(()->LocalGameDataHandler.selectDirectory(s).toURI().toString());
	}
	
	public GameDataHandler(Stage s, String projectName) {
		super(()->LocalGameDataHandler.selectDirectory(s).toURI().toString(), projectName);
	}
	
	public GameDataHandler(Supplier<String> pathSupplier) {
		super(pathSupplier);
	}
	
	public GameDataHandler(Supplier<String> pathSupplier, String projectName) {
		super(pathSupplier, projectName);
	}
	
}