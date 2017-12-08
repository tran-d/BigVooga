package engine.Actions.global;

import java.util.List;



import engine.Action;
import engine.GameObject;
import engine.GameLayer;
import engine.Layer;
import engine.Actions.movement.MoveTo;
import engine.operations.doubleops.DoubleOperation;


/**
 * Currently non-functional.
 * 
 * @author aaronpaskin
 * 
 */
public class ChangeWorld implements Action {

	private Layer newWorld;
	private DoubleOperation newPlayerX;
	private DoubleOperation newPlayerY;
	
	public ChangeWorld(Layer newWorld, DoubleOperation newPlayerX, DoubleOperation newPlayerY) {
		this.newWorld = newWorld;
		this.newPlayerX = newPlayerX;
		this.newPlayerY = newPlayerY;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		List<GameObject> players = world.getWithTag(GameLayer.PLAYER_TAG);
		world.removeElements(players);
		MoveTo moveTo = new MoveTo(newPlayerX, newPlayerY);
		for(GameObject player : players) {
			moveTo.execute(player, newWorld);
		}
		newWorld.addElement(players);
		//world.setNextWorld(newWorld);
	}

}
