package engine.sprite;

import engine.VoogaException;
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
		if(displayable1 == null || displayable2 == null)
			throw new VoogaException("CompositeFail");
	}

	@Override
	public void visit(GameDisplay display) {
			displayable1.visit(display);
			displayable2.visit(display);
	}
		
	public int getDrawingPriority() {
		return displayable1.getDrawingPriority();
	}

	@Override
	public void setPosition(double x, double y) {
		displayable1.setPosition(x, y);
		displayable2.setPosition(x, y);
	}

	@Override
	public double getX() {
		return displayable1.getX();
	}

	@Override
	public double getY() {
		return displayable1.getY();
	}

	@Override
	public void setSize(double width, double height) {
		displayable1.setSize(width, height);
		displayable2.setSize(width, height);
	}

	@Override
	public double getWidth() {
		return displayable1.getWidth();
	}

	@Override
	public double getHeight() {
		return displayable1.getHeight();
	}

	@Override
	public void setHeading(double heading) {
		displayable1.setHeading(heading);
		displayable2.setHeading(heading);
	}

	@Override
	public double getHeading() {
		return displayable1.getHeading();
	}

}
