package controller.player;

import java.util.List;

import engine.GameObject;

public class DataView {

	public DataView() {

	}

	public void display(List<GameObject> allGameObjects) {
		for(GameObject object : allGameObjects) {
			display(object.getName(), object.getAllDoubleVariables(), object.getAllStringVars, object.getAllBooleanVars(), object.getInventory().getFullInventory());
		}
	}
	
	public void display(String name, )

}
