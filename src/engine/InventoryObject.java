package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class InventoryObject extends VariableContainer {

	private Map<String, List<Action>> useOptions;

	public InventoryObject() {
		// TODO Auto-generated constructor stub
		useOptions = new HashMap<String, List<Action>>();

	}

	public void addUseOption(String useName, List<Action> actions) {
		if (useOptions.containsKey(useName)) {
			useOptions.get(useName).addAll(actions);
		} else
			useOptions.put(useName, actions);

	}

	public void use(String useCase, GameObject keeper, Layer world) {
		for (Action a : useOptions.get(useCase)) {

			a.execute(keeper, world);
		}
	}

	public List<String> getUses() {
		return new ArrayList<String>(useOptions.keySet());
	}

}
