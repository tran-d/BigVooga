package authoring.drawing;
import java.util.ArrayList;
import java.util.List;

import com.sun.prism.paint.Color;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class ImageCanvas extends Pane{
	private DrawingTool currentTool = new CurveDrawer(this);
	private List<Line> lines = new ArrayList<>();
	private Color workingColor = Color.BLACK; //TODO
	
	public ImageCanvas() {
		setMinWidth(300);
		setMinHeight(300);
	}
	
	public void addLine(Point2D loc1, Point2D loc2) {
		Line line = new Line(loc1.getX(), loc1.getY(), loc2.getX(), loc2.getY());
		lines.add(line);
		getChildren().add(line);
	}
	
	private void save(String path) {
		WritableImage image = this.snapshot(new SnapshotParameters(), null);
		//TODO, write to file
	}
}
