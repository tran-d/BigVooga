package engine.testing;

import java.util.ResourceBundle;

import authoring.drawing.ImageCanvasPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExampleDrawing extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		FlowPane flow = new FlowPane();

		// Here is the part we provide. Note the lambda could be replaced with a different
		// image consumer, such as storing to a particular directory, or by a file supplier, in which
		// case we handle the saving of the image.
		ImageCanvasPane drawer = new ImageCanvasPane(ResourceBundle.getBundle("authoring.drawing.drawingTools.drawingTools"),400, 400, i -> addImageTo(flow, i));

		pane.setCenter(drawer);
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(flow);
		pane.setRight(scroll);
		stage.show();
	}

	private void addImageTo(Pane p, Image i) {
		ImageView image = new ImageView(i);
		image.setFitHeight(100);
		image.setFitWidth(100);
		p.getChildren().add(image);
	}

}
