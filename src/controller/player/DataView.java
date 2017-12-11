package controller.player;

import java.util.List;
import java.util.Map;

import engine.GameObject;
import javafx.stage.Stage;

public class DataView {

	private Stage stage;

	public DataView(Stage stage) {
		this.stage = stage;
	}

	public void display(List<GameObject> allGameObjects) {
		for (GameObject object : allGameObjects) {
//			List<String> holdableNames = new ArrayList<>();
//			for(Holdable h : object.getInventory().getFullInventory()) {
//				holdableNames.add(h.getName());
//			}
			display(object.getName(), object.getAllDoubleVars(), object.getAllStringVars(), object.getAllBooleanVars());
		}
	}

	private void display(String name, Map<String, Double> allDoubleVars, Map<String, String> allStringVars,
			Map<String, Boolean> allBooleanVars) {
		
	}

}
