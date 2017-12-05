package authoring_UI.dialogue;

import java.util.List;

import javafx.scene.control.TextArea;

/**
 * Class holding dialogue data.
 * 
 * @author DavidTran
 *
 */
public class Dialogue {

	private String name;
	private int fontSize;
	private String font;
	private List<TextArea> taList;

	public Dialogue(String name, int fontSize, String font, List<TextArea> taList) {
		this.name = name;
		this.fontSize = fontSize;
		this.font = font;
		this.taList = taList;
	}

}
