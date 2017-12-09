package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.sprite.BoundedImage;
import engine.sprite.Sprite;

/**
 * 
 * @author Nikolas Bramblett, Aaron Paskin
 *
 */
public class Holdable extends VariableContainer {

	private Map<String, List<Action>> useOptions;
	private Sprite sprite;
	
	public static final String SELECTED = "Selected";

	public Holdable(Sprite sprite) {
		useOptions = new HashMap<String, List<Action>>();
		addUseOption(SELECTED, new ArrayList<Action>());
		this.sprite = sprite;
	}

	public void addUseOption(String useName, List<Action> actions) {
		if (useOptions.containsKey(useName)) {
			useOptions.get(useName).addAll(actions);
		}
		else
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
	
	public BoundedImage getDisplayable() {
		return sprite.getImage();
	}

}
