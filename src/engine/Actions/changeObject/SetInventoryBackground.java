package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaType;
import engine.operations.gameobjectops.GameObjectOperation;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class SetInventoryBackground implements Action{

	GameObjectOperation target, template;
	public SetInventoryBackground(@VoogaAnnotation(name = "Target Object", type = VoogaType.GAMEOBJECT) GameObjectOperation target, 
			@VoogaAnnotation(name = "Object to be used as template", type = VoogaType.GAMEOBJECT) GameObjectOperation template) {
		// TODO Auto-generated constructor stub
		this.target=target;
		this.template = template;
	}
	@Override
	public void execute(GameObject asking, GameObjectEnvironment world) {
		// TODO Auto-generated method stub
		target.evaluate(asking, world).getInventory().setPane(template.evaluate(asking, world).getBounds());
		
		
	}
	

}
