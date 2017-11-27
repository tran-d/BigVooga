package engine;

import java.util.ArrayList;
import java.util.List;

import engine.utilities.collisions.BoundingGeometry;
import engine.utilities.collisions.BoundingSet;
import engine.utilities.collisions.RelativeBoundingGeometry;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class BoundedImage extends BoundingSet implements DisplayableImage{

	private List<RelativeBoundingGeometry> relativeBounds;
	private String fileName;
	private double xCenter;
	private double yCenter;
	private double xSize;
	private double ySize;
	private double heading;

	public BoundedImage(String fileName) {
		this.fileName = fileName;
		relativeBounds = new ArrayList<>();
		relativeBounds.add(RelativeBoundingGeometry.DEFAULT);
	}

	public BoundedImage(String fileName, List<RelativeBoundingGeometry> bounds) {
		this.fileName = fileName;
		relativeBounds = bounds;
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
}
