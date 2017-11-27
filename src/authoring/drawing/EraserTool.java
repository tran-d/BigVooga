package authoring.drawing;

import java.util.Iterator;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class EraserTool extends DrawingTool {

	private Circle circle;
	
	public EraserTool(ImageCanvas canvas) {
		super(canvas);
	}

	private void mouseDragged(MouseEvent e) {
		handleCircle(e.getX(), e.getY());
		Iterator<Line> it = canvas.getLines().iterator();
		while(it.hasNext()) {
			Line l = it.next();
			if(Shape.intersect(l, circle).getBoundsInLocal().getWidth()>=0){
				canvas.remove(l);
				it.remove();
			}
		}
	}
	
	private void handleCircle(double newX, double newY) {
		canvas.remove(circle);
		circle = new Circle(newX, newY, canvas.getStrokeWidth()/2);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		canvas.translate(circle);
		canvas.add(circle);
	}
	
	@Override
	public void use() {
		canvas.setOnMouseDragged(this::mouseDragged);
		canvas.setOnMouseReleased(e->canvas.remove(circle));
	}
	
	@Override
	public void drop() {
		canvas.setOnMouseDragged(null);
		canvas.setOnMouseReleased(null);
	}
}
