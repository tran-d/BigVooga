package controller.player;

import java.util.List;
import java.util.Map;

import engine.GameObject;
import engine.Holdable;
import javafx.stage.Stage;

public class DataView {

	private Stage stage;

	public DataView(Stage stage) {
		this.stage = stage;
	}

	public void display(List<GameObject> allGameObjects) {
		for (GameObject object : allGameObjects) {
			display(object.getName(), object.getAllDoubleVars(), object.getAllStringVars(), object.getAllBooleanVars(),
					object.getInventory().getFullInventory());
		}
	}

	private void display(String name, Map<String, Double> allDoubleVars, Map<String, String> allStringVars,
			Map<String, Boolean> allBooleanVars, List<Holdable> fullInventory) {
		
	}

}
