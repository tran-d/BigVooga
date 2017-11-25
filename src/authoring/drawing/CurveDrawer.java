package authoring.drawing;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class CurveDrawer extends DrawingTool {
	public CurveDrawer(ImageCanvas canvas) {
		super(canvas);
	}

	private Point2D lastLoc;
	
	private void mousePressed(MouseEvent e) {
		lastLoc = point(e);
		canvas.addLine(lastLoc, lastLoc);
	}
	
	private void mouseDragged(MouseEvent e) {
		canvas.addLine(lastLoc, point(e));
		lastLoc = point(e);
	}

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
