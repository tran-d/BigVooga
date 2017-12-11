package engine;

import engine.sprite.Displayable;

public interface Element {
	public Displayable getDisplayable();
	public void step(GameObjectEnvironment w);
	public double getX();
	public double getY();
	public String getName();	//TODO: Delete after testing
}
