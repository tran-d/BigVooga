package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

import engine.operations.VoogaType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class ExistingItemsChoiceBox {

	private ChoiceBox<String> cb;

	public ExistingItemsChoiceBox(VoogaType type) {
		List<String> list = this.makeObservableList(type);
		if (list.size() > 0)
			cb = this.makeChoiceBox(list);
	}

	private List<String> makeObservableList(VoogaType type) {
		List<String> list = new ArrayList<>();

		if (type == VoogaType.OBJECTNAME) {
			list.add("item 1");
			list.add("item 2");
		}
		else {
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

	public ChoiceBox<String> getChoiceBox() {
		return cb;
	}

	public String getSelected() {
		return "";
	}
}
