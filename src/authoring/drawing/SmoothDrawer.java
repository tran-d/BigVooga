package authoring.drawing;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public abstract class SmoothDrawer extends DrawingTool {

	private Point2D lastLoc;

	public SmoothDrawer(ImageCanvas canvas) {
		super(canvas);
	}

	private void mousePressed(MouseEvent e) {
		lastLoc = point(e);
		draw(lastLoc, lastLoc);
	}

	private void mouseDragged(MouseEvent e) {
		draw(lastLoc, point(e));
		lastLoc = point(e);
	}

	protected abstract void draw(Point2D lastLoc, Point2D point);

	@Override
	public void use() {
		canvas.setOnMousePressed(this::mousePressed);
		canvas.setOnMouseDragged(this::mouseDragged);
	}

	@Override
	public void drop() {
		canvas.setOnMousePressed(null);
		canvas.setOnMouseDragged(null);
	}
}