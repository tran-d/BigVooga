package engine;

import java.util.List;
import java.util.Set;

import engine.sprite.Displayable;

public interface Element {
	public Displayable getDisplayable();
	public void step(int priorityNumber, Layer w, List<Runnable> runnables);
	public int getUniqueID();
	public void setUniqueID(int id);
	public Set<Integer> getPriorities();
	public List<String> getTags();
	public String getName();
}
