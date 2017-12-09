package engine;

import engine.sprite.Displayable;

public interface Element {
	public Displayable getDisplayable();
	void step(GameObjectEnvironment w);
}
