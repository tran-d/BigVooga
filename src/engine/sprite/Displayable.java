package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 * @param <T>
 */
public interface Displayable extends Comparable {
	public void visit(GameDisplay display);
}
