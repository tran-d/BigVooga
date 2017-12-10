
package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.stringops.StringOperation;
import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class VectorVariableOf implements VectorOperation {

	private StringOperation varName;
	private GameObjectOperation object;

	public VectorVariableOf(@VoogaAnnotation(name = "Sprite", type = VoogaType.VECTORNAME) GameObjectOperation object,
			StringOperation varName) {
		this.object = object;
		this.varName = varName;
	}

	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return object.evaluate(asking, world).getVector(varName.evaluate(asking, world));
	}

}
