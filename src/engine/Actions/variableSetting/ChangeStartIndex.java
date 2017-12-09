package engine.Actions.variableSetting;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.gameobjectops.GameObjectOperation;

public class ChangeStartIndex implements Action{

	private DoubleOperation startIndex;
	private GameObjectOperation object;
	
	public ChangeStartIndex(DoubleOperation startIndex, GameObjectOperation object) {
		this.startIndex = startIndex;
		this.object = object;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		int start = (int)Math.round(startIndex.evaluate(asking, world));
		object.evaluate(asking, world).getInventory().setStartIndex(start);
	}

}
