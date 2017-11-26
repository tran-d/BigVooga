package authoring.drawing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import engine.VoogaException;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class ImageCanvas extends BorderPane {
	private static final int MIN_HEIGHT = 400;
	private static final int MIN_WIDTH = 400;
	private static final Color DEFAULT_COLOR = Color.BLACK;
	private static final int DEFAULT_STROKE_INDEX = 7;

	private List<Line> lines = new ArrayList<>();
	private ColorPicker colorPicker = new ColorPicker(DEFAULT_COLOR);
	private ComboBox<Integer> strokeSelector;
	private Integer[] strokes = { 3,4,5,6,7,8,9,10,11,12,14,16,18,20,22,24,26,30,36,48,64 };
	private ComboBox<DrawingTool> toolSelector;
	private DrawingTool[] knownTools = { new CurveDrawer(this), new LineDrawer(this), new EraserTool(this) };
	private DrawingTool currentTool;
	private Supplier<File> fileLocation;
	
	public ImageCanvas(Supplier<File> fileLocation) {
		setMinWidth(MIN_WIDTH);
		setMinHeight(MIN_HEIGHT);
		setMaxWidth(MIN_WIDTH);
		setMaxHeight(MIN_HEIGHT);
		setCenter(new Pane());
		setTop(new FlowPane());
		addToTop(colorPicker);
		addToolSelector();
		addStrokeSelector();
		addSaveButton();
		this.fileLocation = fileLocation;
	}

	private void addSaveButton() {
		Button b = new Button("Save");
		b.setOnAction(e -> save(fileLocation.get()));
		addToTop(b);
	}

	private void addToolSelector() {
		toolSelector = new ComboBox<>();
		toolSelector.getItems().addAll(knownTools);
		toolSelector.setOnAction(e -> setTool(toolSelector.getValue()));
		toolSelector.getSelectionModel().selectFirst();
		setTool(toolSelector.getValue());
		addToTop(toolSelector);
	}
	
	private void addStrokeSelector() {
		strokeSelector = new ComboBox<>();
		strokeSelector.getItems().addAll(strokes);
		strokeSelector.getSelectionModel().select(DEFAULT_STROKE_INDEX);
		addToTop(strokeSelector);
	}
	
	public int getStrokeWidth() {
		return strokeSelector.getValue();
	}

	private void addToTop(Node n) {
		((Pane) getTop()).getChildren().add(n);
	}

	private void setTool(DrawingTool tool) {
		if (currentTool != null)
			currentTool.drop();
		currentTool = tool;
		if (tool != null)
			tool.use();
	}

	public void add(Node n) {
		((Pane) getCenter()).getChildren().add(n);
	}

	public void remove(Node n) {
		((Pane) getCenter()).getChildren().remove(n);
	}

	public void addLine(Line line) {
		lines.add(line);
		add(line);
	}
	
	public List<Line> getLines(){
		return lines;
	}

	public void addLine(Point2D loc1, Point2D loc2) {
		addLine(getLine(loc1, loc2));
	}

	public Line getLine(Point2D loc1, Point2D loc2) {
		Line line = new Line(loc1.getX(), loc1.getY(), loc2.getX(), loc2.getY());
		line.setStroke(colorPicker.getValue());
		line.setTranslateX(getCenter().getLayoutX());
		line.setTranslateY(-getCenter().getLayoutY());
		line.setStrokeWidth(getStrokeWidth());
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		return line;
	}

	//http://code.makery.ch/blog/javafx-2-snapshot-as-png-image/
	public void save(File location) {
		if(location==null)
			return;
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		params.setViewport(centerView());
		WritableImage image = getCenter().snapshot(params, null);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", location);
		} catch (IOException e) {
			throw new VoogaException("IllegalFile", location.getAbsolutePath());
		}
	}

	private Rectangle2D centerView() {
		return new Rectangle2D(0-getCenter().getLayoutX(),((Pane) getTop()).getHeight(), getWidth(), getHeight()-((Pane) getTop()).getHeight());
	}
}
