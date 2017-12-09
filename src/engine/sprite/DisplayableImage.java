package engine.sprite;

public interface DisplayableImage extends Displayable {
	public double getX();
	public double getY();
	public double getWidth();
	public double getHeight();
	public double getHeading();
	public String getFileName();
	public int getDrawingPriority();
	
	public void setPosition(double x, double y);
	public void setSize(double width, double height);
	public void setHeading(double heading);
}
