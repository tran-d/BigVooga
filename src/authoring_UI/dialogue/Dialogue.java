package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

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
	private List<String> textList;

	public Dialogue(String name, String font, int fontSize, Color fontColor, List<TextArea> taList) {
		this.name = name;
		this.fontSize = fontSize;
		this.font = font;

		this.createTextList(taList);
	}

	/*************************** PUBLIC METHODS **********************************/

	public String getName() {
		return name;
	}

	public int getFontSize() {
		return fontSize;
	}

	public String getFontType() {
		return font;
	}

	public List<String> getTextList() {
		return textList;
	}
	
	public String toString() {
		return "Name: " + name + " | Font: " + font + " | Font Size: " + fontSize;
	}

	/*************************** PRIVATE METHODS **********************************/

	private void createTextList(List<TextArea> taList) {
		textList = new ArrayList<String>();

		for (TextArea ta : taList) {
			textList.add(ta.getText());
		}
	}

}
