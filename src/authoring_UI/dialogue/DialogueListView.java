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

//	private List<DialogueListCell> dList = new ArrayList<>();
	private List<String> dList = new ArrayList<>();

	public DialogueListView(List<Dialogue> list) {

		for (Dialogue d : list) {
			dList.add(d.getName());
		}
		ObservableList<String> items = FXCollections.observableArrayList (
		    dList);
		
		this.setItems(items);
	}
}
