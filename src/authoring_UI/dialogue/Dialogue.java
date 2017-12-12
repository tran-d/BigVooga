package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Class holding dialogue data.
 * 
 * @author DavidTran
 *
 */
public class Dialogue {

	private String name;
	private String fontType;
	private Color fontColor;
	private List<Pane> dialogueSequence;

	public Dialogue(String name, String fontType, Color fontColor, List<Pane> dialogueSequence) {
		this.name = name;
		this.fontType = fontType;
		this.fontColor = fontColor;
		this.dialogueSequence = dialogueSequence;
	}

	/*************************** PUBLIC METHODS **********************************/

	public String getName() {
		return name;
	}
	
	public List<Pane> getDialogueSequence() {
		return dialogueSequence;
	}

//	public int getFontSize() {
//		return fontSize;
//	}
//
	public String getFontType() {
		return fontType;
	}
	
	public Color getFontColor() {
		return fontColor;
	}
////
//	public List<String> getTextList() {
//		return textList;
//	}
//	
//	public String toString() {
//		return "Name: " + name + " | Font: " + font + " | Font Size: " + fontSize;
//	}

	/*************************** PRIVATE METHODS **********************************/

//	private void createTextList(List<TextArea> taList) {
//		textList = new ArrayList<String>();
//
//		for (TextArea ta : taList) {
//			textList.add(ta.getText());
//		}
//	}

}
