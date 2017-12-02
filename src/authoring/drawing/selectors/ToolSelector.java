package authoring.drawing.selectors;

import java.util.List;
import java.util.ResourceBundle;

import authoring.drawing.DrawingTool;
import authoring.drawing.ImageCanvas;
import authoring.drawing.ToolFactory;
import javafx.scene.control.ChoiceBox;

public class ToolSelector extends ChoiceBox<DrawingTool> {

	private ImageCanvas canvas;

	public ToolSelector(ImageCanvas canvas, ResourceBundle bundle) {
		this.canvas = canvas;
		setTools(ToolFactory.getTools(canvas, bundle));
		setOnAction(e -> canvas.setTool(getValue()));
	}
	
	public void setTools(List<DrawingTool> tools) {
		getItems().clear();
		getItems().addAll(tools);
		getSelectionModel().selectFirst();
		canvas.setTool(getValue());
	}
	
}
