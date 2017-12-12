package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;
import engine.operations.VoogaType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCombination.Modifier;

public class ExistingItemsChoiceBox {

	private ChoiceBox<String> cb;

	public ExistingItemsChoiceBox(VoogaType type) {
		List<String> list = this.makeObservableList(type);
		if (list.size() > 0)
			cb = this.makeChoiceBox(list);
	}

	private List<String> makeObservableList(VoogaType type) {
		List<String> list = new ArrayList<>();

		if (type == VoogaType.ANIMATIONNAME) {

		} else if (type == VoogaType.BOOLEANNAME) {

		} else if (type == VoogaType.DOUBLENAME) {

		} else if (type == VoogaType.DIALOGNAME) {

		} else if (type == VoogaType.KEY) {

		} else if (type == VoogaType.OBJECTNAME) {
			list.add("item 1");
			list.add("item 2");
		} else if (type == VoogaType.STRINGNAME) {

		} else if (type == VoogaType.TAG) {

		} else if (type == VoogaType.WORLDNAME) {

		} else {
			list.add("item 3");
			list.add("item 4");
		}
		System.out.println("making an existingItemsList");
		return list;
	}

	private ChoiceBox<String> makeChoiceBox(List<String> list) {
		ObservableList<String> obList = FXCollections.observableArrayList(list);
		ChoiceBox<String> cb = new ChoiceBox<>(obList);
		return cb;
	}
	
	private List<String> makeKeyList() {
		return null;	// TODO what is this supposed to return?
	}	

	public ChoiceBox<String> getChoiceBox() {
		return cb;
	}

	public String getSelected() {
		return "";
	}
}
