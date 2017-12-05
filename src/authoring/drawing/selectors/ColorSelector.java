package authoring.drawing.selectors;

import java.util.function.Consumer;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ColorSelector extends ColorPicker {
	private static final Color DEFAULT_COLOR = Color.BLACK;
	
	public ColorSelector(Consumer<Color> listener) {
		this(listener, DEFAULT_COLOR);
	}
	
	public ColorSelector(Consumer<Color> listener, Color startingColor) {
		super(startingColor);
		setOnAction(e -> listener.accept(getValue()));
		listener.accept(getValue());
	}
}
