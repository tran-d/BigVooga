package authoring.drawing;

import java.util.ResourceBundle;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class DrawingTool {
	protected ImageCanvas canvas;
	private ResourceBundle names = ResourceBundle.getBundle("authoring.drawing.drawingTools");

	public DrawingTool(ImageCanvas canvas) {
		this.canvas = canvas;
	}
	
	public abstract void use();
	public abstract void drop();
	
	protected Point2D point(MouseEvent e) {
		return new Point2D(e.getX(), e.getY());
	}
	
	public String toString() {
		return names.getString(getClass().getSimpleName());
	}
}
