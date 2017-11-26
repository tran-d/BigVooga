package authoring.drawing;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class LineDrawer extends DrawingTool{
	private static final double PHANTOM_OPACITY = .4;
	private Point2D lastLoc;
	private Line phantom;
	
	public LineDrawer(ImageCanvas canvas) {
		super(canvas);
	}
	
	private void mousePressed(MouseEvent e) {
		lastLoc = point(e);
	}
	
	private void mouseReleased(MouseEvent e) {
		canvas.addLine(lastLoc, point(e));
	}

	private void mouseDragged(MouseEvent e) {
		canvas.remove(phantom);
		phantom = canvas.getLine(lastLoc, point(e));
		phantom.setOpacity(PHANTOM_OPACITY);
		canvas.addLine(phantom);
	}
	
	@Override
	public void use() {
		canvas.setOnMousePressed(this::mousePressed);
		canvas.setOnMouseReleased(this::mouseReleased);
		canvas.setOnMouseDragged(this::mouseDragged);
	}

	@Override
	public void drop() {
		canvas.setOnMousePressed(null);
		canvas.setOnMouseReleased(null);
		canvas.setOnMouseDragged(null);
	}
}
