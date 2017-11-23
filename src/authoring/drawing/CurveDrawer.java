package authoring.drawing;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class CurveDrawer implements DrawingTool {
	private static final double DELAY = 10;
	private ImageCanvas canvas;
	private Timeline loop;
	private Point2D lastLoc;
	private Point2D mouseLoc;
	
	public CurveDrawer(ImageCanvas canvas) {
		this.canvas = canvas;
		canvas.setOnMousePressed(this::mousePressed);
		canvas.setOnMouseReleased(this::mouseReleased);
		canvas.setOnMouseDragged(this::mouseMoved);
		loop = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(DELAY), e -> step());
		loop.setCycleCount(Timeline.INDEFINITE);
		loop.getKeyFrames().add(frame);
	}
	
	private void mousePressed(MouseEvent e) {
		mouseLoc = point(e);
		lastLoc = mouseLoc;
		loop.play();
	}
	
	private Point2D point(MouseEvent e) {
		return new Point2D(e.getX(), e.getY());
	}

	private void mouseReleased(MouseEvent e) {
		loop.pause();
	}	
	
	private void step() {
		canvas.addLine(lastLoc, mouseLoc);
		lastLoc = mouseLoc;
	}
	
	private void mouseMoved(MouseEvent e) {
		mouseLoc = new Point2D(e.getX(), e.getY());
	}
}
