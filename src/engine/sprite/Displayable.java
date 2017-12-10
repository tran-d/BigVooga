package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public interface Displayable extends Comparable {
	public void visit(GameDisplay display);
	public int getDrawingPriority();
	public void setPosition(double x, double y);
	public double getX();
	public double getY();
	public void setSize(double width, double height);
	public double getWidth();
	public double getHeight();
	public void setHeading(double heading);
	public double getHeading();
}
