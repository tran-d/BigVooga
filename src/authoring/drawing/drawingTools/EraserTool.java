package authoring.drawing.drawingTools;

import authoring.drawing.ImageCanvas;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class EraserTool extends SmoothDrawer {
	
	private Rectangle square;

	public EraserTool(String name, ImageCanvas canvas) {
		super(name, canvas);
	}
	
	private void handleSquare(Point2D newCenter) {
		canvas.getChildren().remove(square);
		double stroke = canvas.getStroke();
		square = new Rectangle(newCenter.getX()-stroke/2, newCenter.getY()-stroke/2, stroke, stroke);
		square.setFill(Color.WHITE);
		square.setStroke(Color.BLACK);
		canvas.getChildren().add(square);
	}

	@Override
	protected void draw(Point2D lastLoc, Point2D point) {
		canvas.eraseLine(lastLoc, point);
		handleSquare(point);
	}
	
	@Override
	public void use() {
		super.use();
		canvas.setOnMouseReleased(e-> canvas.getChildren().remove(square));
	}

	@Override
	public void drop() {
		super.drop();
		canvas.setOnMouseReleased(null);
	}
}
