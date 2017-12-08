package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class CompositeImage implements Displayable {

	private Displayable displayable1;
	private Displayable displayable2;

	public CompositeImage(Displayable bottom, Displayable top) {
		displayable1 = bottom;
		displayable2 = top;
	}

	@Override
	public void visit(GameDisplay display) {
		if (displayable1 != null)
			displayable1.visit(display);
		if (displayable2 != null)
			displayable2.visit(display);
	}

	@Override
	public int getDrawingPriority() {
		int first = Integer.MAX_VALUE;
		int second = Integer.MAX_VALUE;
		if(displayable1 != null)
			first = displayable1.getDrawingPriority();
		if(displayable2 != null)
			second = Integer.MAX_VALUE;
		return Math.min(first, second);
	}

}
