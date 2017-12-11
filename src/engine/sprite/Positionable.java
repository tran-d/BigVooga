package engine.sprite;

public interface Positionable {

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