package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.vectorops.VectorOperation;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class SetAcceleration implements Action {
	
	private GameObjectOperation object;
	private VectorOperation acceleration;

	public SetAcceleration(GameObjectOperation object, VectorOperation acceleration) {
		this.object = object;
		this.acceleration = acceleration;
	}

	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		object.evaluate(asking, world).setDerivative(2, acceleration.evaluate(asking, world));
	}
}
