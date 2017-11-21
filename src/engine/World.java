package engine;
import java.util.List;

public interface World extends Iterable<GameObject>{
	
	public void addGameObject(GameObject obj);
	public void removeGameObject(GameObject obj);
	public List<GameObject> getWithTag(String tag);
	
	//The World should have its own name to make things easily distinguishable.
	public boolean isNamed(String tag);
	
	public void step();
	
	public void addGlobalVars(VariableContainer gv);
	
	public InputManager getInputManager();
}