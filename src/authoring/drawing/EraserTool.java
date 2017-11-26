package authoring.drawing;

import java.util.Iterator;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class EraserTool extends DrawingTool {

	public EraserTool(ImageCanvas canvas) {
		super(canvas);
	}

	private void mouseDragged(MouseEvent e) {
		Iterator<Line> it = canvas.getLines().iterator();
		while(it.hasNext()) {
			Line l = it.next();
			if(Shape.intersect(l, new Circle(e.getX(), e.getY(), canvas.getStrokeWidth())).getBoundsInLocal().getWidth()>=0){
				canvas.remove(l);
				it.remove();
			}
		}
	}
	
	@Override
	public void use() {
		canvas.setOnMouseDragged(this::mouseDragged);
	}
	
	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}
}
