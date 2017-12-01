package authoring.drawing;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import engine.VoogaException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class ImageCanvasPane extends BorderPane {
	private static final Color DEFAULT_COLOR = Color.BLACK;
	private static final int DEFAULT_STROKE_INDEX = 7;

	private ColorPicker colorPicker;
	private ComboBox<Integer> strokeSelector;
	private Integer[] strokes = { 3,4,5,6,7,8,9,10,11,12,14,16,18,20,22,24,26,30,36,48,64 };
	private ComboBox<DrawingTool> toolSelector;
	private DrawingTool[] knownTools;
	
	private FlowPane top;
	private ImageCanvas imageCanvas;
	private double width;
	private double height;
	
	public ImageCanvasPane(double width, double height) {
		this.width = width;
		this.height = height;
		
		imageCanvas = new ImageCanvas(width, height);
		knownTools = new DrawingTool[]{ new CurveDrawer(imageCanvas), new EraserTool(imageCanvas), new LineDrawer(imageCanvas) };
		setCenter(imageCanvas);
		
		top = new FlowPane();
		setTop(top);
		
		addColorPicker();
		addToolSelector();
		addStrokeSelector();
		addSaveButton();
	}

	private void addColorPicker() {
		colorPicker = new ColorPicker(DEFAULT_COLOR);
		colorPicker.setOnAction(e->imageCanvas.setColor(colorPicker.getValue()));
		imageCanvas.setColor(colorPicker.getValue());
		addToTop(colorPicker);
	}

	private void addSaveButton() {
		Button b = new Button("Save");
		b.setOnAction(e -> {});//TODO
		addToTop(b);
	}

	private void addToolSelector() {
		toolSelector = new ComboBox<>();
		toolSelector.getItems().addAll(knownTools);
		toolSelector.setOnAction(e -> imageCanvas.setTool(toolSelector.getValue()));
		toolSelector.getSelectionModel().selectFirst();
		imageCanvas.setTool(toolSelector.getValue());
		addToTop(toolSelector);
	}
	
	private void addStrokeSelector() {
		strokeSelector = new ComboBox<>();
		strokeSelector.getItems().addAll(strokes);
		strokeSelector.getSelectionModel().select(DEFAULT_STROKE_INDEX);
		strokeSelector.setOnAction(e -> imageCanvas.setStroke(strokeSelector.getValue()));
		imageCanvas.setStroke(strokeSelector.getValue());
		addToTop(strokeSelector);
	}

	private void addToTop(Node n) {
		top.getChildren().add(n);
	}

	//http://code.makery.ch/blog/javafx-2-snapshot-as-png-image/
	public Image getImage() {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image image = imageCanvas.snapshot(params, new WritableImage((int)width, (int)height));
		return image;
	}
	
	public void saveTo(Image image, File location) {
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", location);
		} catch (IOException e) {
			throw new VoogaException("IllegalFile", location.getAbsolutePath());
		}
	}
}
