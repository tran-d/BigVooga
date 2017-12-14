package authoring_UI.displayable;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Displayable {
	private String name;
	private String fontType;
	private Color fontColor;
	private List<Pane> displayableSequence;
	
	protected Displayable(String name, String fontType, Color fontColor, List<Pane> dialogueSequence) {
		this.name = name;
		this.fontType = fontType;
		this.fontColor = fontColor;
		this.displayableSequence = dialogueSequence;
	}
	
	protected String getName() {
		return name;
	}
	
	protected List<Pane> getDisplayableSequence() {
		return displayableSequence;
	}

	protected String getFontType() {
		return fontType;
	}
	
	protected Color getFontColor() {
		return fontColor;
	}
	
}
