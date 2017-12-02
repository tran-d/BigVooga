package authoring.drawing;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class DrawingTool {
	protected ImageCanvas canvas;
	private String name;
	private Image icon;

	public DrawingTool(String name, ImageCanvas canvas) {
		this.name = name;
		this.canvas = canvas;
	}
	
	public abstract void use();
	public abstract void drop();
	
	protected Point2D point(MouseEvent e) {
		return new Point2D(e.getX(), e.getY());
	}
	
	public String toString() {
		return name;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}
	public Image getIcon() {
		return icon;
	}
}
