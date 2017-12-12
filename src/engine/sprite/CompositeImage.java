package engine.sprite;

import java.util.List;

import engine.VoogaException;
import engine.utilities.collisions.BoundingPolygon;
import gui.player.GameDisplay;
import javafx.geometry.Point2D;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class CompositeImage implements Displayable {

	private Displayable displayable1;
	private List<? extends DisplayableText> text;

	public CompositeImage(Displayable bottom, List<? extends DisplayableText> text) {
		displayable1 = bottom;
		this.text = text;
		if (displayable1 == null)
			throw new VoogaException("CompositeFail");
	}

	@Override
	public void visit(GameDisplay display) {
		displayable1.visit(display);
		for (DisplayableText t : text) {
			t.setHeading(displayable1.getHeading());
			Point2D newLocation = BoundingPolygon
					.rotateByAngle(new Point2D(t.getRelativeX() * displayable1.getWidth() / 2,
							t.getRelativeY() * displayable1.getHeight() / 2), t.getHeading())
					.add(new Point2D(displayable1.getX(), displayable1.getY()));
			t.setPosition(newLocation.getX(), newLocation.getY());
			t.setSize(t.getRelativeWidth() * displayable1.getWidth(), t.getRelativeHeight() * displayable1.getHeight());
			t.visit(display);
		}
	}

	public int getDrawingPriority() {
		return displayable1.getDrawingPriority();
	}

	@Override
	public void setPosition(double x, double y) {
		displayable1.setPosition(x, y);
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
	}

	@Override
	public double getHeading() {
		return displayable1.getHeading();
	}

}
