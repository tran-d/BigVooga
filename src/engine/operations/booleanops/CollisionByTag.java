package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.Get;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class CollisionByTag implements BooleanOperation {

	private StringOperation tag;
	
	public CollisionByTag(StringOperation tag) {
		this.tag = tag;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		for(GameObject g : world.getObjectsWithTag(tag.evaluate(asking, world))) {
			if(g == asking)
				continue;
			boolean result = new Collision(new Get(asking), new Get(g)).evaluate(asking, world);
			if(result)
				return true;
		}
		return false;
	}
}
