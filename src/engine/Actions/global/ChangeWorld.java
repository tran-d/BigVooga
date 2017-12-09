package engine.Actions.global;

import java.util.List;



import engine.Action;
import engine.GameObject;
import engine.GameLayer;
import engine.Layer;
import engine.Actions.movement.MoveTo;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.vectorops.VectorOperation;


/**
 * Currently non-functional.
 * 
 * @author aaronpaskin
 * 
 */
public class ChangeWorld implements Action {

	private Layer newWorld;
	private VectorOperation newLocation;
	
	public ChangeWorld(Layer newWorld, VectorOperation newLocation) {
		this.newWorld = newWorld;
		this.newLocation = newLocation;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
//		List<GameObject> players = world.getWithTag(GameLayer.PLAYER_TAG);
//		world.removeElements(players);
////		MoveTo moveTo = new MoveTo(newPlayerX, newPlayerY);
////		MoveTo moveTo = new MoveTo(newLocation);
//		for(GameObject player : players) {
//			moveTo.execute(player, newWorld);
//		}
//		newWorld.addElements(players);
		//world.setNextWorld(newWorld);
	}

}
