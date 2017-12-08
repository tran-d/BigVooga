package engine;

import java.util.List;

import engine.sprite.Displayable;

public interface Element {
	public Displayable getDisplayable();
	public void step(int priorityNumber, Layer w, List<Runnable> runnables);
}
