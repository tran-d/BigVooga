package authoring.drawing;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import authoring.drawing.selectors.ColorSelector;
import authoring.drawing.selectors.StrokeSelector;
import authoring.drawing.selectors.ToolSelector;
import engine.VoogaException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class ImageCanvasPane extends BorderPane {
	private ImageCanvas imageCanvas;
	private Consumer<Image> saveTo;

	public ImageCanvasPane(ResourceBundle toolsAndNames, double width, double height, Consumer<Image> saveTo) {
		this(toolsAndNames, width, height);
		if (saveTo == null)
			saveTo = i -> {};
		this.saveTo = saveTo;
	}

	public ImageCanvasPane(ResourceBundle toolsAndNames, double width, double height, Supplier<File> fileChooser) {
		this(toolsAndNames, width, height);
		final Supplier<File> chooser;
		if (fileChooser == null)
			chooser = () -> null;
		else
			chooser = fileChooser;
		saveTo = i -> saveTo(i, chooser.get());
	}

	private ImageCanvasPane(ResourceBundle toolsAndNames, double width, double height) {
		imageCanvas = new ImageCanvas(width, height);
		setCenter(imageCanvas);
		setTop(new FlowPane());
		addToTop(new ColorSelector(c->imageCanvas.setColor(c)));
		addToTop(new ToolSelector(imageCanvas, toolsAndNames));
		addToTop(new StrokeSelector(s -> imageCanvas.setStroke(s)));
		addSaveButton();
	}

	private void addSaveButton() {
		Button b = new Button("Save");
		b.setOnAction(e -> saveTo.accept(getImage()));
		addToTop(b);
	}

	private void addToTop(Node n) {
		((Pane)getTop()).getChildren().add(n);
	}

	private Image getImage() {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image image = imageCanvas.snapshot(params,
				new WritableImage((int) imageCanvas.getWidth(), (int) imageCanvas.getHeight()));
		return image;
	}

	private void saveTo(Image image, File location) {
		if (location == null || image == null)
			return;
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", location);
		} catch (IOException e) {
			throw new VoogaException("IllegalFile", location.getAbsolutePath());
		}
	}
}
