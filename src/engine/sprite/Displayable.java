package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 * @param <T>
 */
public interface Displayable<T> extends Comparable<T>{

	public void visit(GameDisplay display);
}
