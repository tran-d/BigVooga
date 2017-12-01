package authoring.drawing;

import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 */
public class CurveDrawer extends SmoothDrawer {

	public CurveDrawer(ImageCanvas canvas) {
		super(canvas);
	}

	@Override
	protected void draw(Point2D lastLoc, Point2D point) {
		canvas.drawLine(lastLoc, point);
	}
}
