package authoring.drawing;

import javafx.geometry.Point2D;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class EraserTool extends SmoothDrawer {
	
	public EraserTool(ImageCanvas canvas) {
		super(canvas);
	}
	
	private void handleCircle(Point2D newCenter) {
		//canvas.eraseLine(new Point2D(circle.getCenterX(), circle.getCenterY()), newCenter);
	}

	private void eliminateCircle() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void draw(Point2D lastLoc, Point2D point) {
		canvas.eraseLine(lastLoc, point);
		handleCircle(point);
	}
	
	@Override
	public void use() {
		super.use();
		canvas.setOnMouseReleased(e-> eliminateCircle());
	}

	@Override
	public void drop() {
		super.drop();
		canvas.setOnMouseReleased(null);
	}
}
