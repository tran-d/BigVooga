package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Class that displays a list of dialogues.
 * 
 * @author DavidTran
 *
 */
public class DialogueListView extends ListView<String> {

	private static double HEIGHT = 20;
//	private List<DialogueListCell> dList = new ArrayList<>();
	private List<String> dList = new ArrayList<>();

	public DialogueListView(List<Dialogue> list) {

		for (Dialogue d : list) {
			dList.add(d.getName());
		}
		ObservableList<String> items = FXCollections.observableArrayList (
		    dList);
		
		this.setItems(items);
//		this.setTooltip(getTooltip());
		
		this.setHeight(HEIGHT);
		
	}
	
	private String createListCellText(Dialogue d) {
		return "Name: " + d.getName() + " | Font: " + d.getFont() + " | Font Size: " + d.getFontSize();
	}
}
