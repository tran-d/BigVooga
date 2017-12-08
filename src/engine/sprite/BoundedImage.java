package engine.sprite;

import java.util.ArrayList;
import java.util.List;

import engine.utilities.collisions.BoundingGeometry;
import engine.utilities.collisions.BoundingSet;
import engine.utilities.collisions.RelativeBoundingGeometry;
import gui.player.GameDisplay;

/**
 * Represents an Image with relative bounds. 
 * 
 * @author Ian Eldridge-Allegra
 * 
 */
public class BoundedImage extends BoundingSet implements DisplayableImage{

	private final int DEFAULT_DEPTH = 10;
	
	private List<RelativeBoundingGeometry> relativeBounds;
	private String fileName;
	private double xCenter;
	private double yCenter;
	private double xSize;
	private double ySize;
	private double heading;
	private int depth;

	public BoundedImage(String fileName) {
		this.fileName = fileName;
		relativeBounds = new ArrayList<>();
		relativeBounds.add(RelativeBoundingGeometry.DEFAULT);
		depth = DEFAULT_DEPTH;
	}

	public BoundedImage(String fileName, List<RelativeBoundingGeometry> bounds) {
		this.fileName = fileName;
		relativeBounds = bounds;
		depth = DEFAULT_DEPTH;
	}
	
	protected List<BoundingGeometry> getGeometry() {
		List<BoundingGeometry> geometry = new ArrayList<>(relativeBounds.size());
		for (RelativeBoundingGeometry rg : relativeBounds) {
			geometry.add(rg.getBoundingGeometry(xCenter, yCenter, xSize, ySize, heading));
		}
		return geometry;
	}

	public void setPosition(double x, double y) {
		xCenter = x;
		yCenter = y;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public void setSize(double width, double height) {
		xSize = width;
		ySize = height;
	}

	@Override
	public double getX() {
		return xCenter;
	}

	@Override
	public double getY() {
		return yCenter;
	}

	@Override
	public double getWidth() {
		return xSize;
	}

	@Override
	public double getHeight() {
		return ySize;
	}

	@Override
	public double getHeading() {
		return heading;
	}

	@Override
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Sets drawing priority of sprite. Lower numbers should be drawn first.
	 * @param priority
	 */
	public void setDrawPriority(int priority)
	{
		this.depth = priority;
	}
	
	@Override
	public int getDrawingPriority() {
		return depth;
	}

	@Override
	public int compareTo(DisplayableImage other) {
		return depth - other.getDrawingPriority();
	}
	
	public BoundedImage clone() {
		BoundedImage i =  new BoundedImage(fileName, relativeBounds);
		i.setPosition(xCenter, yCenter);
		i.setHeading(heading);
		i.setDrawPriority(depth);
		i.setSize(xSize, ySize);
		return i;
	}

	@Override
	public void visit(GameDisplay display) {
		// TODO Auto-generated method stub
		display.updateImages(this);
	}
}
