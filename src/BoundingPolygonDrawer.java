import java.util.ArrayList;
import java.util.List;

import engine.utilities.collisions.RelativeBoundingGeometry;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class BoundingPolygonDrawer extends Pane{

	private List<RelativeBoundingGeometry> geometries = new ArrayList<>();
	private List<Point2D> vertices = new ArrayList<>();
	private Line phantomLine;
	
	public BoundingPolygonDrawer(Image image) {
		getChildren().add(new ImageView(image));
		setup();
	}
	
	private void setup() {
		setOnMouseClicked(this::mouseClicked);
		setOnMouseMoved(this::mouseMoved);
	}
	
	private void mouseClicked(MouseEvent event) {
		if(event.getButton().equals(MouseButton.SECONDARY))
			rightClick(event);
		else
			leftClick(event);
		
	}
	
	private void leftClick(MouseEvent event) {
		if(!vertices.isEmpty()) {
			Line l = new Line(lastClickedX(), lastClickedY(), event.getX(), event.getY());
			getChildren().add(l);
		}
		vertices.add(new Point2D(event.getX(), event.getY()));
		phantomLine = new Line(0,0,0,0);
	}
	
	private Point2D lastPoint() {
		return vertices.get(vertices.size()-1);
	}
	
	private double lastClickedX() {
		return lastPoint().getX();
	}
	
	private double lastClickedY() {
		return lastPoint().getY();
	}
	
	private double originalX() {
		return vertices.get(0).getX();
	}
	
	private double originalY() {
		return vertices.get(0).getY();
	}

	private void rightClick(MouseEvent event) {
		Line l = new Line(lastClickedX(), lastClickedY(), originalX(), originalY());
		getChildren().add(l);
		
		getChildren().remove(phantomLine);
		phantomLine = null;
	}

	private void mouseMoved(MouseEvent event) {
		if(phantomLine == null)
			return;
		getChildren().remove(phantomLine);
		phantomLine = new Line(lastClickedX(), lastClickedY(), event.getX(), event.getY());
		phantomLine.setOpacity(.5);
		getChildren().add(phantomLine);
	}
}
