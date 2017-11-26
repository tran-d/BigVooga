package engine;
/**
* Action -- A general representation of any change in the game state
*/
public interface Action{
	
	public void execute(GameObject asking, World world);
	
}
